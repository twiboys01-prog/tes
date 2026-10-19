package com.collagestudio.app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.OutputStream;

/** Menyimpan gambar (base64) langsung ke Galeri lewat MediaStore (Android 10+ tanpa izin tambahan). */
@CapacitorPlugin(name = "GallerySaver")
public class GallerySaverPlugin extends Plugin {

    @PluginMethod
    public void saveImage(final PluginCall call) {
        final String data = call.getString("data");
        final String fileName = call.getString("fileName", "collage-" + System.currentTimeMillis() + ".png");
        final String mime = call.getString("mimeType", "image/png");
        final String album = call.getString("album", "Collage Studio");

        if (data == null || data.isEmpty()) { call.reject("Data gambar kosong"); return; }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            call.reject("Android < 10: pakai Share"); // JS akan fallback ke lembar Share
            return;
        }

        // Jalankan di thread terpisah supaya UI tidak macet untuk gambar besar
        getBridge().execute(() -> {
            Uri uri = null;
            ContentResolver resolver = getContext().getContentResolver();
            try {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                values.put(MediaStore.MediaColumns.MIME_TYPE, mime);
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + album);
                values.put(MediaStore.MediaColumns.IS_PENDING, 1);

                uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (uri == null) throw new Exception("Gagal membuat entri MediaStore");

                byte[] bytes = Base64.decode(data, Base64.DEFAULT);
                try (OutputStream out = resolver.openOutputStream(uri)) {
                    if (out == null) throw new Exception("Gagal membuka output stream");
                    out.write(bytes);
                    out.flush();
                }

                ContentValues done = new ContentValues();
                done.put(MediaStore.MediaColumns.IS_PENDING, 0);
                resolver.update(uri, done, null, null);

                JSObject ret = new JSObject();
                ret.put("uri", uri.toString());
                call.resolve(ret);
            } catch (Throwable t) {
                if (uri != null) {
                    try { resolver.delete(uri, null, null); } catch (Throwable ignored) {}
                }
                call.reject("Gagal simpan ke galeri: " + t.getMessage());
            }
        });
    }
}
