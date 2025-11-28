/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure;
// 1. Class Node: Lagu
class Lagu {
    String judul;
    Lagu next; // Penunjuk ke depan
    Lagu prev; // Penunjuk ke belakang

    public Lagu(String judul) {
        this.judul = judul;
    }
    
    public String toString() { return "[" + judul + "]"; }
}

// 2. Class Manajemen: Playlist
class Playlist {
    private Lagu head;
    private Lagu tail;

    public Playlist() { head = null; tail = null; }

    public boolean isEmpty() { return head == null; }

    // Insert Last: Tambah lagu di akhir antrian
    public void addLagu(String judul) {
        Lagu baru = new Lagu(judul);
        if (isEmpty()) {
            head = baru;
            tail = baru;
        } else {
            tail.next = baru; // Ekor lama menunjuk lagu baru
            baru.prev = tail; // Lagu baru menunjuk ekor lama
            tail = baru;      // Update posisi ekor
        }
    }

    // FITUR UTAMA: Hapus lagu pertama (Head)
    public Lagu removeFirst() {
        if (isEmpty()) return null;
        
        Lagu temp = head; // Simpan lagu yang akan dihapus
        
        if (head == tail) { // Jika cuma ada 1 lagu
            head = null;
            tail = null;
        } else {
            head = head.next; // 1. Pindahkan stiker "Head" ke lagu kedua
            head.prev = null; // 2. Putus hubungan dengan lagu lama
        }
        return temp;
    }

    public void display() {
        System.out.print("Playlist: ");
        Lagu current = head;
        while(current != null) {
            System.out.print(current + " <-> "); // Visualisasi panah bolak-balik
            current = current.next;
        }
        System.out.println("END");
    }
public static void main(String[] args) {
    Playlist spotify = new Playlist();
    ExecutionTimer timer = new ExecutionTimer();

    System.out.println("--- 1. Menyiapkan Playlist ---");
    spotify.addLagu("Lagu A (Awal)");
    spotify.addLagu("Lagu B");
    spotify.addLagu("Lagu C");
    spotify.addLagu("Lagu D (Akhir)");
    
    spotify.display();

    System.out.println("\n--- 2. Hapus Lagu Pertama (Delete Head) ---");
    // Operasi ini O(1) karena hanya memindah pointer Head.
    // Tidak ada pergeseran elemen B, C, D.
    
    timer.start();
    Lagu hapus = spotify.removeFirst();
    timer.stop("Delete First Node");
    
    if (hapus != null) 
        System.out.println("   -> Berhasil menghapus: " + hapus);

    System.out.println("\n--- 3. Playlist Update ---");
    spotify.display();
}
}
