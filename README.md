# Tugas Kecil 3 IF2211 Strategi Algoritma
# Penyelesaian Persoalan 15-Puzzle dengan Algoritma _Branch and Bound_

| Nama | Johannes Winson Sukiatmodjo |
| ---- | --------------------------- |
| NIM  | 13520123                    |

## Deskripsi Singkat Program
Program ini merupakan sebuah aplikasi berbasis Command Line Interface (CLI) yang dapat menyelesaikan persoalan 15-Puzzle. Dengan menggunakan algoritma _Branch and Bound_, program ini akan meminimalkan pergeseran ubin dalam mencapai goal state dengan cara memilih simpul yang mempunyai nilai cost paling kecil. Program ini menerima input berupa nama file 15-Puzzle yang akan diselesaikan. Setelah itu, program akan menampilkan matriks posisi awal 15-Puzzle, nilai dari fungsi Kurang(i) untuk setiap ubin yang tidak kosong pada posisi awal, nilai dari sigma Kurang(i) + X, urutan matriks dari posisi awal ke posisi akhir jika persoalan dapat diselesaikan, waktu eksekusi program, dan jumlah simpul yang dibangkitkan di dalam pohon ruang status pencarian. Program ini dibuat dalam bahasa Java dan dengan paradigma pemrograman berorientasi objek.

## Requirement Program
1. Java 8 or greater
2. Visual Studio Code

## Cara Menggunakan Program
1. Clone repository ini
2. Buka Visual Studio Code
3. Buka folder repository ini
4. Buka file `Main.java`
5. Tekan tombol `Run` yang berada di bawah `public class Main`
6. Masukkan nama file 15-Puzzle yang ingin diselesaikan