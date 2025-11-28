/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure;
// 1. Objek Data: Penduduk
class Penduduk {
    long nik;
    String nama;

    public Penduduk(long nik, String nama) {
        this.nik = nik;
        this.nama = nama;
    }

    public String toString() {
        return "[NIK: " + nik + " | " + nama + "]";
    }
}

// 2. Class Algoritma Advanced Sort
class CensusSorter {
    private Penduduk[] data;
    private int nElemen;

    public CensusSorter(int max) {
        data = new Penduduk[max];
        nElemen = 0;
    }

    public void insert(long nik, String nama) {
        data[nElemen] = new Penduduk(nik, nama);
        nElemen++;
    }

    public Penduduk[] copyArray() {
        Penduduk[] temp = new Penduduk[nElemen];
        System.arraycopy(data, 0, temp, 0, nElemen);
        return temp;
    }

    // --- A. SHELL SORT (Knuth Sequence) ---
    public void shellSort(Penduduk[] arr) {
        int inner, outer;
        Penduduk temp;
        int h = 1;
        
        // Cari gap awal (Knuth's Interval: h = 3*h + 1)
        while (h <= nElemen / 3) h = h * 3 + 1; 

        while (h > 0) {
            for (outer = h; outer < nElemen; outer++) {
                temp = arr[outer];
                inner = outer;
                
                // Insertion sort dengan lompatan h
                while (inner > h - 1 && arr[inner - h].nik >= temp.nik) {
                    arr[inner] = arr[inner - h];
                    inner -= h;
                }
                arr[inner] = temp;
            }
            h = (h - 1) / 3; // Kecilkan gap
        }
    }

    // --- B. QUICK SORT ---
    public void quickSort(Penduduk[] arr) {
        recQuickSort(arr, 0, nElemen - 1);
    }

    private void recQuickSort(Penduduk[] arr, int left, int right) {
        if (right - left <= 0) return; // Base case: sudah urut
        
        long pivot = arr[right].nik; // Pivot ambil paling kanan
        int partition = partitionIt(arr, left, right, pivot);
        
        recQuickSort(arr, left, partition - 1);  // Sort kiri
        recQuickSort(arr, partition + 1, right); // Sort kanan
    }

    private int partitionIt(Penduduk[] arr, int left, int right, long pivot) {
        int leftPtr = left - 1;
        int rightPtr = right;
        
        while (true) {
            // Cari yang lebih besar dari pivot (dari kiri)
            while (leftPtr < right && arr[++leftPtr].nik < pivot);
            
            // Cari yang lebih kecil dari pivot (dari kanan)
            while (rightPtr > left && arr[--rightPtr].nik > pivot);
            
            if (leftPtr >= rightPtr) break; // Pointer ketemu
            else swap(arr, leftPtr, rightPtr);
        }
        swap(arr, leftPtr, right); // Restore pivot
        return leftPtr;
    }

    private void swap(Penduduk[] arr, int one, int two) {
        Penduduk temp = arr[one];
        arr[one] = arr[two];
        arr[two] = temp;
    }
public static void main(String[] args) {
    int jumlahWarga = 10000; // Dataset Besar
    CensusSorter dukcapil = new CensusSorter(jumlahWarga);
    ExecutionTimer timer = new ExecutionTimer();

    System.out.println("--- 1. Generate " + jumlahWarga + " NIK Penduduk Acak ---");
    for (int i = 0; i < jumlahWarga; i++) {
        // NIK acak 16 digit (simulasi long)
        long nikAcak = 3500000000000000L + (long)(Math.random() * 999999999L);
        dukcapil.insert(nikAcak, "Warga-" + i);
    }

    // Copy array agar adil
    Penduduk[] dataShell = dukcapil.copyArray();
    Penduduk[] dataQuick = dukcapil.copyArray();

    System.out.println("\n--- 2. Benchmark Sorting (10.000 Data) ---");

    // Uji Shell Sort
    timer.start();
    dukcapil.shellSort(dataShell);
    timer.stop("Shell Sort");

    // Uji Quick Sort
    timer.start();
    dukcapil.quickSort(dataQuick);
    timer.stop("Quick Sort");

    System.out.println("\n--- 3. Validasi Hasil (3 NIK Terkecil) ---");
    System.out.println("1. " + dataQuick[0].toString());
    System.out.println("2. " + dataQuick[1].toString());
    System.out.println("3. " + dataQuick[2].toString());
}
}
