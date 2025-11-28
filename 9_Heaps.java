/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure;
Java
// 1. Objek Data: Pengunjung Wahana
class Pengunjung {
    String nama;
    int prioritasTiket; // 100 = VIP, 10 = Regular

    public Pengunjung(String nama, int prio) {
        this.nama = nama;
        this.prioritasTiket = prio;
    }

    public String toString() {
        return "[" + nama + " | Tiket: " + prioritasTiket + "]";
    }
}

// 2. Class Manajemen: Max-Heap
class AntrianWahana {
    private Pengunjung[] heapArray;
    private int maxSize;
    private int currentSize;

    public AntrianWahana(int max) {
        maxSize = max;
        currentSize = 0;
        heapArray = new Pengunjung[maxSize];
    }

    public boolean isEmpty() { return currentSize == 0; }

    // INSERT (Masuk Antrian): O(log n)
    public void daftarAntrian(String nama, int prioritas) {
        if (currentSize == maxSize) return;
        
        Pengunjung baru = new Pengunjung(nama, prioritas);
        heapArray[currentSize] = baru; // 1. Taruh di kursi paling belakang
        trickleUp(currentSize++);      // 2. "Promosi" naik ke atas jika VIP
    }

    // REMOVE (Masuk Wahana): O(log n)
    public Pengunjung panggilMasuk() {
        if (isEmpty()) return null;
        
        Pengunjung root = heapArray[0]; // Ambil orang terdepan (Bos)
        heapArray[0] = heapArray[--currentSize]; // Pindahkan orang terakhir ke posisi Bos
        trickleDown(0); // "Turunkan" dia ke posisi yang pantas
        return root;
    }

    // Logika Promosi (Naik ke atas)
    private void trickleUp(int index) {
        int parent = (index - 1) / 2;
        Pengunjung bottom = heapArray[index];

        // Selama prioritas si "bottom" lebih tinggi dari Parent-nya -> Tukar!
        while (index > 0 && heapArray[parent].prioritasTiket < bottom.prioritasTiket) {
            heapArray[index] = heapArray[parent]; // Parent turun
            index = parent;
            parent = (parent - 1) / 2;
        }
        heapArray[index] = bottom;
    }

    // Logika Turun (Cari posisi yang pas)
    private void trickleDown(int index) {
        int largerChild;
        Pengunjung top = heapArray[index];

        while (index < currentSize / 2) { // Selama punya anak
            int left = 2 * index + 1;
            int right = left + 1;

            // Bandingkan anak kiri vs kanan, mana yang lebih prioritas?
            if (right < currentSize &&
                heapArray[left].prioritasTiket < heapArray[right].prioritasTiket) {
                largerChild = right;
            } else {
                largerChild = left;
            }

            if (top.prioritasTiket >= heapArray[largerChild].prioritasTiket) break;

            heapArray[index] = heapArray[largerChild];
            index = largerChild;
        }
        heapArray[index] = top;
    }

public static void main(String[] args) {
    AntrianWahana rollerCoaster = new AntrianWahana(10);
    ExecutionTimer timer = new ExecutionTimer();

    System.out.println("--- 1. Pengunjung Datang Mengantri ---");
    
    // Orang biasa datang duluan
    rollerCoaster.daftarAntrian("Andi (Regular)", 10);
    rollerCoaster.daftarAntrian("Budi (Regular)", 10);
    rollerCoaster.daftarAntrian("Citra (Regular)", 10);
    
    // Tiba-tiba Sultan datang belakangan
    System.out.println(">> VIP Datang: Sultan...");
    
    timer.start();
    rollerCoaster.daftarAntrian("Sultan (VIP)", 100); 
    timer.stop("Heap Insert (Trickle Up)");

    System.out.println("\n--- 2. Gerbang Wahana Dibuka (Remove Max) ---");
    
    // Siapa yang masuk duluan?
    timer.start();
    Pengunjung p1 = rollerCoaster.panggilMasuk();
    timer.stop("Heap Remove (Trickle Down)");
    
    System.out.println("   -> Masuk Wahana #1: " + p1.toString());
    System.out.println("   -> Masuk Wahana #2: " + rollerCoaster.panggilMasuk());
    System.out.println("   -> Masuk Wahana #3: " + rollerCoaster.panggilMasuk());
    System.out.println("   -> Masuk Wahana #4: " + rollerCoaster.panggilMasuk());
}
}