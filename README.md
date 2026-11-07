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
- Tab **Template** berisi Poster Sinematik, Bingkai Caption, Bingo Karakter, dan Linimasa Peristiwa. Pengaturan teksnya hanya muncul saat template dipilih. Saat template aktif, Gaya Tata Letak di tab Layout dikunci supaya tidak bentrok.
- Kalau menambah class Tailwind baru di `www/index.html`, tidak perlu apa-apa: workflow menjalankan `npm run build:css` otomatis. Untuk tes lokal: `npm install && npm run build:css`.

## Menu utama (beranda)
Saat aplikasi dibuka muncul 3 kartu: **Kolase** (kolase biasa tanpa template), **Template** (pilih Poster Sinematik / Bingkai Caption / Bingo Karakter / Linimasa), dan **Kanvas Kosong** (kanvas teks). Tombol rumah di pojok kiri atas editor (atau tombol Kembali Android) membawa kembali ke menu; proyek yang sedang dikerjakan bisa dilanjutkan lewat tombol "Lanjutkan proyek". Memulai proyek baru mengembalikan semua pengaturan ke bawaan.

## Plugin simpan-ke-Galeri
Kode native ada di `native/`, dipasang otomatis oleh `scripts/setup-native.js` lewat hook `capacitor:sync:before` di `package.json` (jadi tidak bergantung pada isi workflow). Yang HARUS ikut di-upload ke GitHub: folder `native/`, folder `scripts/`, dan `package.json`.

## Template Bingo Karakter
- Grid bingo persegi **3x3, 4x4, atau 5x5** (pilih lewat tombol Ukuran Grid di tab Template). Foto mengisi kotak berurutan dari kiri atas; kotak yang belum terisi dibiarkan kosong, foto berlebih belum ditampilkan (ada info jumlahnya).
- Judul bisa diedit (default "Favorite Character Bingo"), plus rata teks, font, ukuran, tebal garis, dan warna kertas/judul/garis. Nama karakter bisa ditulis lewat keterangan foto (ketuk foto).
- Ukuran kanvas mengikuti Rasio Kanvas (bawaan 4:5); pilih Auto agar tinggi kanvas pas dengan grid.
- Kode: `bingoLayout()`, `renderBingoOverlaysDOM()`, `exportBingoOverlays()` di `www/index.html`.

## Template Linimasa Peristiwa
- Garis waktu dengan garis-garis (tick) berselang-seling atas dan bawah. Satu foto = satu garis, urutannya mengikuti daftar foto di tab Foto.
- **Jumlah garis**: otomatis mengikuti jumlah foto (2 sampai 12), atau matikan "Otomatis" dan atur sendiri lewat slider. Garis yang belum ada fotonya tetap tampil kosong.
- **Teks peristiwa** diisi di kolom caption pada daftar foto. "Nama (1998)" otomatis dipecah jadi 2 baris; atau pakai tanda `|`, misalnya `Tsukihime | 2000`. Teks panjang dibungkus otomatis.
- Judul, label ujung ("Long time ago" / "Today"), font, ukuran judul/label/teks/foto, tebal garis, dan warna semuanya bisa diubah. Ganti foto lewat panel edit foto (ketuk foto).
- Rasio bawaan 5:4 (opsi baru di Rasio Kanvas). Kode: `timelineLayout()`, `renderTimelineOverlaysDOM()`, `exportTimelineOverlays()` di `www/index.html`.

## Template Tier List
- Baris tier berwarna dengan kolom label di kiri (gaya tier list maker). Foto mengisi baris dari atas ke bawah sesuai urutan di tab Foto.
- Di tab Template: tiap baris punya **warna**, **nama** (bisa diedit bebas), dan **jumlah foto**. Ada tombol ▲▼ untuk memindah urutan baris, ✕ untuk menghapus, dan ＋ Tambah baris (maksimal 12).
- Untuk memindahkan foto ke tier lain: tukar posisinya (tahan lalu geser) atau ubah jumlah foto per baris.
- Bisa diatur: font, ukuran teks label, lebar kolom label, jarak antar foto, tebal garis pemisah, warna latar/teks/garis. Bawaan rasio Auto agar tinggi kanvas pas dengan jumlah baris.
- Kode: `tierLayout()`, `renderTierOverlaysDOM()`, `exportTierOverlays()`, `renderTierEditor()` di `www/index.html`.

## Gaya Caption per Foto
- Di tab **Foto**, tiap baris punya tombol **Aa** di sebelah kolom "Teks / caption (opsional)". Ketuk untuk membuka panel gaya di bawah baris itu: font (12 pilihan), ukuran (40–400%, 100% = ukuran bawaan baru yang lebih besar), tebal, miring, bayangan, multi-baris (bungkus otomatis sampai 4 baris), posisi (atas/tengah/bawah), rata (kiri/tengah/kanan), gaya latar (gradasi/blok/pil/tanpa), warna teks, warna latar, dan kepekatan latar.
- **Terapkan ke semua foto** menyalin gaya foto itu ke seluruh foto; **Reset gaya** mengembalikan ke bawaan.
- Preview dan ekspor memakai fungsi geometri yang sama (`capGeom()`). Caption juga tampil di template Tier List; di Linimasa caption tetap dipakai sebagai teks peristiwa (tombol Aa disembunyikan).

## Zoom & Geser Foto (perbaikan)
- Satu rumus penempatan (`photoPlacement()`) dipakai preview dan ekspor. Foto selalu menutupi kotaknya; slider Geser ↔/↕ bergerak dari tepi ke tepi bagian foto yang berlebih, jadi tidak ada lagi celah kosong atau foto yang mengecil.
- Zoom sekarang sampai 5× untuk close up. Geser ↔ hanya terasa jika foto lebih lebar dari kotak (atau setelah zoom dinaikkan); begitu juga ↕ untuk foto yang lebih tinggi.

## Template Judul + Baris Poster dan Meme Reaksi
- **Judul + Baris Poster** (`titleLayout`): judul besar (boleh beberapa baris, font/ukuran/rata/warna bisa diatur) lalu foto tersusun per baris. Bisa atur foto per baris (1–6), bentuk foto (lebar÷tinggi), jarak, dan warna latar.
- **Meme Reaksi** (`memeLayout`): dua bagian atas-bawah dipisah garis. Tiap bagian: balon teks (pikiran / bicara / hanya teks) di atas foto reaksi di kiri, dan kumpulan 1–6 foto di kanan. Urutan foto: reaksi atas, foto bagian atas, reaksi bawah, foto bagian bawah.
- Balon, garis, dan teks digambar lewat satu daftar operasi (`xMeta.ops`) yang dipakai SVG untuk preview dan canvas untuk ekspor.

## Template Grafik Garis Peristiwa dan Bagan Segitiga
- **Grafik Garis Peristiwa** (`chartLayout`): tiap baris teks = satu titik, format `tinggi (0–100) | label`. Foto dipasang di atas titik yang dipilih (kolom "Foto di titik ke-", mis. `3` atau `3, 7`), diisi urut dari kiri ke kanan. Ada teks sumbu bawah, tebal garis, tinggi grafik, ukuran foto, warna.
- **Bagan Segitiga** (`trinityLayout`): 8 foto berlabel (3 atas, ibu di tengah, kiri, kanan, kiri bawah, kanan bawah), segitiga garis dengan teks tengah, judul dan 1–12 foto kecil di baris bawah. Posisi tetap (acuan lebar 1704), semua teks bisa diubah.
- Keduanya memakai daftar operasi gambar `xMeta.ops` yang sama dengan Meme Reaksi (SVG untuk preview, canvas untuk ekspor); `polyline` ditambahkan untuk garis grafik.

## Meme dipisah dua + foto bawaan
- Template **Meme: He is Literally Me** (`meme`) dan **Meme: Omg He's Literally Me** (`meme2`) sekarang terpisah. Satu `memeLayout(seq, spec, 'A'|'B')`, dua varian.
- Foto reaksi tertanam di `index.html` (`<script id="builtin-assets">`, id `bi-apple`, `bi-giga`, `bi-soy`, `bi-chad`): sudah dipotong pas slot template, watermark Gigachad dibuang, Chad dibalik supaya menghadap ke foto. Tidak muncul di daftar foto, tidak bisa digeser/ditukar. Pencariannya lewat `photoOf(id)`.
- Foto di tab Foto hanya untuk foto karakter di sisi kanan (bagian atas dulu, lalu bagian bawah).
- Untuk mengganti foto bawaan: ubah data URI di `builtin-assets` (potong ke rasio 0.75 untuk A, ~0.93 untuk B).

## Perbaikan menu awal
- Sheet "Pilih Template" di menu awal sekarang dibatasi tingginya (88% layar) dan bisa digulir, dengan tombol Batal yang tetap terlihat di bawah. Sebelumnya daftar yang makin panjang terpotong di bawah layar dan tidak bisa digulir.

## UX HP v2 (layar setelah masuk menu)
- Setelah memilih Kolase/Template, galeri tidak lagi terbuka otomatis. Muncul **layar mulai**: lencana jenis proyek, judul (nama template yang dipilih), deskripsi singkat, tombol besar "Pilih Foto dari Galeri", dan dua tombol sekunder (Foto contoh, Canvas teks). Fungsi `updateStartScreen()`. Untuk mengembalikan galeri otomatis, ganti `updateStartScreen()` di `startProject()` dengan `fileInput.click()`.
- Selama belum ada foto, panel pengaturan dan bar aksi disembunyikan (`body.is-empty`) supaya layar fokus; keduanya muncul begitu foto pertama masuk.
- Grip panel bawah bisa **ditarik** (3 posisi: preview kecil / sedang / besar, tarik ke bawah penuh = lipat), dengan transisi halus. Ketuk grip tetap melipat/membuka.
- Tab bawah: angka "1. 2. 3." dihapus, penanda tab aktif lebih jelas, konten tab muncul dengan animasi singkat dan selalu mulai dari atas. Tombol punya umpan balik tekan.
- Memilih kartu di tab Template otomatis menggulir ke pengaturan template itu.
