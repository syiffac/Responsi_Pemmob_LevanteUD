# 📱 Responsi 1 - Pemrograman Mobile

**Aplikasi: Levante UD Football Club**

---

## 👤 Identitas

| Keterangan     | Data                |
| -------------- | ------------------- |
| **Nama**       | Aisyah Syifa Karima |
| **NIM**        | H1D023043           |
| **Shift Baru** | C                   |
| **Shift Asal** | C                   |

---

## 🏟️ Deskripsi Aplikasi

Aplikasi **Levante UD Football Club** menampilkan informasi mengenai klub sepak bola **Levante Unión Deportiva**, meliputi sejarah klub, data pelatih, dan daftar pemain.
Data pelatih serta pemain diambil secara langsung dari **Football-Data.org API** menggunakan **Retrofit**.
Warna kartu pemain menyesuaikan posisi:
🟡 *Goalkeeper*, 🔵 *Defender*, 🟢 *Midfielder*, 🔴 *Forward*.

Aplikasi ini dikembangkan menggunakan **Kotlin**, **Android Studio**, serta menerapkan **ViewBinding** dan arsitektur **MVVM (Model-View-ViewModel)**.

---

## 🔄 Penjelasan Alur Data

1. **Pemanggilan API (Retrofit)**
   Aplikasi mengirim permintaan ke endpoint
   `https://api.football-data.org/v4/teams/{88}`
   dengan menyertakan *header* `X-Auth-Token`.

2. **Repository**
   Repository berfungsi sebagai penghubung antara ViewModel dan Retrofit.
   Di sini permintaan API dieksekusi, dan hasilnya dikembalikan ke ViewModel dalam bentuk objek Kotlin.

3. **ViewModel**
   ViewModel menyimpan data dari Repository ke dalam **LiveData** agar bisa dipantau oleh UI.
   Proses ini berjalan di coroutine sehingga tidak menghambat tampilan.

4. **Observasi Data di View**
   Activity atau Fragment mengamati `LiveData` dari ViewModel.
   Saat data diterima, tampilan akan otomatis diperbarui melalui **ViewBinding**.

5. **Penyajian di Layar**

   * Informasi pelatih ditampilkan di halaman “Head Coach”.
   * Daftar pemain ditampilkan di RecyclerView dengan warna berbeda sesuai posisi.
   * Gambar klub dan pemain dimuat menggunakan **Glide** dari URL API.

**Flow Alur Data**

> API → Retrofit → Repository → ViewModel → LiveData → View (Activity/Fragment) → ViewBinding → Tampilan di layar

---

## 🎬 Video Demo



https://github.com/user-attachments/assets/0bfc8ea3-9196-4d5b-a90e-0a0e37cffb3e



---
