# AccuratePeople

Aplikasi Android untuk mengelola daftar pengguna dengan dukungan offline, pencarian, filter, dan sinkronisasi data dari server.

---

## Cara Penggunaan Aplikasi

### Melihat Daftar User
Saat pertama kali dibuka, aplikasi otomatis mengambil data dari server dan menyimpannya ke database lokal. Daftar user langsung tampil dari penyimpanan lokal sehingga tetap bisa diakses meski tidak ada koneksi internet.

### Mencari User
Ketik nama atau nama kota di kolom pencarian di bagian atas. Hasil langsung difilter secara real-time tanpa perlu menekan tombol apapun.

### Filter berdasarkan Kota
Geser chip kota di bawah kolom pencarian, lalu tap kota yang diinginkan. Tap **Semua** untuk menampilkan seluruh user kembali.

### Mengurutkan Daftar
Tap ikon panah di sebelah kanan kolom pencarian untuk beralih antara urutan nama A–Z dan Z–A.

### Menyegarkan Data (Swipe to Refresh)
Tarik daftar ke bawah untuk memperbarui data langsung dari server. Indikator loading muncul selama proses berlangsung dan hilang otomatis saat selesai.

### Menambah User Baru
Tap tombol **+** di pojok kanan bawah. Form tambah user muncul dari bawah layar. Isi semua field yang ditandai **\*** lalu tap **Simpan**.

| Field | Keterangan |
|---|---|
| Nama | Minimal 2 karakter |
| Email | Harus format email yang valid |
| No. Telepon | 8–15 digit angka |
| Alamat | Opsional |
| Kota | Ketik untuk mencari, pilih dari dropdown |
| Gender | Pilih Perempuan atau Laki-laki |

Data dikirim ke server dan langsung muncul di daftar tanpa perlu refresh manual.

### Melihat Detail User
Tap salah satu kartu user untuk melihat detail lengkapnya.

- Di layar kecil (ponsel): detail muncul di halaman baru
- Di layar lebar (tablet / fold): detail tampil berdampingan dengan daftar

---

## Teknologi yang Digunakan

### Arsitektur
| Komponen | Teknologi |
|---|---|
| Arsitektur | Clean Architecture + Multi-Module |
| Pattern | MVVM (ViewModel + StateFlow + Compose) |
| Dependency Injection | Hilt 2.59 |

### UI
| Komponen | Teknologi |
|---|---|
| UI Framework | Jetpack Compose |
| Design System | Material 3 |
| Navigasi | Navigation Compose |
| Adaptive Layout | `ListDetailPaneScaffold` (Material3 Adaptive) |

### Data & Jaringan
| Komponen | Teknologi |
|---|---|
| HTTP Client | Retrofit 3 + OkHttp |
| JSON Parser | Moshi (code-gen via KSP) |
| Database Lokal | Room 2.8 |
| Background Sync | WorkManager 2.10 |

### Observabilitas
| Komponen | Teknologi |
|---|---|
| Analytics | Firebase Analytics |

### Build
| Komponen | Teknologi |
|---|---|
| Android Gradle Plugin | AGP 9.2 |
| Kotlin | 2.3 |
| KSP | 2.3.7 |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 37 |

### Struktur Modul
```
AccuratePeople/
├── app/                  # Entry point, wiring Hilt + WorkManager + Firebase
├── core/
│   ├── common/           # Result, DispatcherProvider
│   ├── network/          # Retrofit, OkHttpClient, Moshi
│   ├── database/         # Room entities, DAOs, AppDatabase
│   └── ui/               # Shared composables, tema, adaptive helpers
└── feature/
    └── users/            # Domain, data, presentation layer fitur user
```

---

## Kenapa Tampilan dan Interaksi Seperti Ini

### Offline-First
Data selalu dibaca dari Room (database lokal), bukan langsung dari API. Sinkronisasi dilakukan di background via WorkManager setiap 15 menit. Artinya daftar user tetap tampil meski sinyal buruk atau tidak ada internet sama sekali.

### Swipe to Refresh sebagai Sync Manual
Pengguna tidak perlu tahu soal WorkManager atau siklus sinkronisasi. Gestur tarik ke bawah adalah cara paling intuitif untuk "menyegarkan data" di aplikasi mobile — sama seperti aplikasi email atau media sosial yang sudah familiar. Saat di-trigger, sync langsung hit endpoint dan hasilnya masuk ke Room, lalu UI update otomatis lewat Flow.

### Search Real-Time dengan Debounce
Filter dilakukan lokal (tidak hit API) dengan debounce 300ms agar tidak memicu komputasi ulang setiap satu huruf diketik. Hasil muncul seketika tanpa loading, memberi kesan aplikasi terasa cepat dan responsif.

### City Autocomplete sebagai Dropdown
Dropdown kota di form tambah user difilter saat pengguna mengetik, bukan daftar statis. Ini mencegah kesalahan penulisan nama kota dan memastikan data yang disimpan konsisten dengan data master kota dari server.

### Per-Field Validation dengan Inline Error
Error validasi ditampilkan langsung di bawah field yang bermasalah, bukan satu pesan di bawah form. Pengguna langsung tahu field mana yang perlu diperbaiki tanpa harus menebak. Error hilang begitu pengguna mulai mengetik ulang di field tersebut.

### Keyboard Type per Field
Setiap field menggunakan jenis keyboard yang sesuai konteksnya — angka untuk nomor telepon, email untuk alamat email, kapital otomatis per kata untuk nama. Ini mengurangi friksi saat mengisi form dan meminimalkan kemungkinan input yang salah format.

### Adaptive Layout (Ponsel vs Tablet)
Di layar sempit, navigasi ke detail user menggunakan back-stack biasa. Di layar lebar, daftar dan detail ditampilkan berdampingan (`ListDetailPaneScaffold`) agar ruang layar dimanfaatkan secara optimal dan pengguna tidak bolak-balik layar.

### ModalBottomSheet untuk Form Tambah User
Form tambah user muncul dari bawah layar sebagai bottom sheet, bukan halaman baru. Ini membuat konteks daftar tetap terlihat di belakang, dan pengguna bisa membatalkan cukup dengan geser ke bawah — lebih cepat daripada menekan tombol back.

### FilterChip untuk Filter Kota
Filter kota menggunakan chip horizontal yang bisa di-scroll, bukan dropdown. Chip memperlihatkan semua opsi sekaligus (atau sebagian dengan scroll), sehingga pengguna bisa melihat pilihan yang tersedia tanpa harus membuka menu terlebih dahulu.
