# Collage Studio (APK)

## Build tanpa Android Studio (GitHub Actions, gratis)
1. Buat repo baru di GitHub, unggah SEMUA isi folder ini (termasuk folder `.github`).
2. Buka tab **Actions** > **Build APK** > **Run workflow**. Tunggu ±5-8 menit.
3. Unduh artifact **CollageStudio-APK** (zip berisi `app-debug.apk`), ekstrak, kirim ke HP, install
   (izinkan "Install dari sumber tidak dikenal").

## Mengubah aplikasi
Edit `www/index.html`, commit, jalankan workflow lagi.
Simpan/bagikan hasil kolase memakai lembar Share Android (pilih Galeri/Files).

## Build lokal (opsional, perlu Android Studio + JDK 17)
npm install && npx cap add android && npx capacitor-assets generate --android && npx cap sync android && cd android && ./gradlew assembleDebug
