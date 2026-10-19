# Collage Studio (APK)

## Build tanpa Android Studio (GitHub Actions, gratis)
1. Buat repo baru di GitHub, unggah SEMUA isi folder ini (termasuk folder `.github`).
2. Buka tab **Actions** > **Build APK** > **Run workflow**. Tunggu ±5-8 menit.
3. Unduh artifact **CollageStudio-APK** (zip berisi `app-debug.apk`), ekstrak, kirim ke HP, install
   (izinkan "Install dari sumber tidak dikenal").

## Mengubah aplikasi
Edit `www/index.html`, commit, jalankan workflow lagi.
Tombol Unduh menyimpan langsung ke Galeri (album **Collage Studio**). Kalau gagal, otomatis jatuh ke lembar Share.

## Update aplikasi (TIDAK perlu bikin repo baru)
1. Ganti/edit `www/index.html` di repo yang sama (GitHub web: buka file > ikon pensil, atau Add file > Upload files), lalu Commit ke `main`.
2. Workflow jalan otomatis (push ke main) — tunggu selesai, unduh artifact APK terbaru.
3. Install di atas versi lama. File `native/debug.keystore` menjaga tanda tangan tetap sama, jadi update menimpa tanpa uninstall.
   (Hanya untuk update pertama dari APK lama: uninstall dulu, karena APK lama bertanda tangan acak.)

## Build lokal (opsional, perlu Android Studio + JDK 17)
npm install && npx cap add android && npx capacitor-assets generate --android && npx cap sync android && cd android && ./gradlew assembleDebug
