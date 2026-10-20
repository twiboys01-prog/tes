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


## Tampilan HP & Template
- Di HP: preview kolase selalu di atas (tidak ikut scroll), panel kontrol di bawah dan scroll sendiri. Tombol ⤢ di pojok kanan atas preview memperbesar/memperkecil preview.
- Ketuk foto = buka panel edit (bottom sheet). **Tahan lalu geser** = tukar posisi foto (geser biasa tidak menukar apa-apa).
- Tab **Template** berisi Poster Sinematik dan Bingkai Caption. Pengaturan teksnya hanya muncul saat template dipilih. Saat template aktif, Gaya Tata Letak di tab Layout dikunci supaya tidak bentrok.
- Kalau menambah class Tailwind baru di `www/index.html`, tidak perlu apa-apa: workflow menjalankan `npm run build:css` otomatis. Untuk tes lokal: `npm install && npm run build:css`.

## Menu utama (beranda)
Saat aplikasi dibuka muncul 3 kartu: **Kolase** (kolase biasa tanpa template), **Template** (pilih Poster Sinematik / Bingkai Caption), dan **Kanvas Kosong** (kanvas teks). Tombol rumah di pojok kiri atas editor (atau tombol Kembali Android) membawa kembali ke menu; proyek yang sedang dikerjakan bisa dilanjutkan lewat tombol "Lanjutkan proyek". Memulai proyek baru mengembalikan semua pengaturan ke bawaan.

## Plugin simpan-ke-Galeri
Kode native ada di `native/`, dipasang otomatis oleh `scripts/setup-native.js` lewat hook `capacitor:sync:before` di `package.json` (jadi tidak bergantung pada isi workflow). Yang HARUS ikut di-upload ke GitHub: folder `native/`, folder `scripts/`, dan `package.json`.
