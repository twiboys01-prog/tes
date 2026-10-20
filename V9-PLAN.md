# Rencana v8 -> v9 (Kolase gaya InCollage)

Status: [x] selesai, [ ] belum

- [x] Tombol **Simpan** di header atas (HP, mode Kolase) -> memicu Download PNG. Chip kandidat di header disembunyikan (sudah ada di grid Layout).
- [x] **Latar**: 6 swatch gradasi (format `g:#warna1,#warna2`), dirender di preview dan ekspor.
- [x] **Border**: chip preset cepat: Siku / Lengkung / Bulat (radius) + Tanpa celah / Lega (jarak & margin).
- [x] Uji: Kolase, Template, ekspor kanvas dengan gradasi, undo/redo.

## Sengaja belum dikerjakan (butuh ubah mesin render)
- Layout bentuk hati / lingkaran (butuh mask clip di preview DOM + ekspor canvas).
- Layout miring / polaroid (butuh rotasi per foto di placements, preview dan ekspor).
- Pilihan bentuk garis tepi putus-putus.
- Autosave proyek (IndexedDB).
