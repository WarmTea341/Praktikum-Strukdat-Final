package datastructure;
// 1. Objek Data: Produk E-Commerce

class Produk {

    String nama;
    int harga;

    public Produk(String nama, int harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public String toString() {
        return "[Item: " + nama + " | Rp " + harga + "]";
    }
}

// 2. Class Algoritma Sorting
class ShopSorter {

    private Produk[] etalase;
    private int nElemen;

    public ShopSorter(int max) {
        etalase = new Produk[max];
        nElemen = 0;
    }

    public void insert(String nama, int harga) {
        etalase[nElemen] = new Produk(nama, harga);
        nElemen++;
    }

    // Helper: Copy Array agar pengujian adil (Data acak yang sama untuk tiap algo)
    public Produk[] copyArray() {
        Produk[] temp = new Produk[nElemen];
        for (int i = 0; i < nElemen; i++) {
            temp[i] = etalase[i];
        }
        return temp;
    }

    // A. BUBBLE SORT
    public void bubbleSort(Produk[] arr) {
        int batas, i;
        for (batas = nElemen - 1; batas > 0; batas--) {
            for (i = 0; i < batas; i++) {
                // Bandingkan Harga
                if (arr[i].harga > arr[i + 1].harga) {
                    swap(arr, i, i + 1);
                }
            }
        }
    }

    // B. SELECTION SORT
    public void selectionSort(Produk[] arr) {
        int awal, i, min;
        for (awal = 0; awal < nElemen - 1; awal++) {
            min = awal;
            for (i = awal + 1; i < nElemen; i++) {
                if (arr[i].harga < arr[min].harga) { // Cari harga termurah
                    min = i;
                }
            }
            swap(arr, awal, min);
        }
    }

    // C. INSERTION SORT
    public void insertionSort(Produk[] arr) {
        int i, curIn;
        for (curIn = 1; curIn < nElemen; curIn++) {
            Produk temp = arr[curIn];
            i = curIn;
            // Geser elemen yang harganya lebih mahal ke kanan
            while (i > 0 && arr[i - 1].harga >= temp.harga) {
                arr[i] = arr[i - 1];
                i--;
            }
            arr[i] = temp;
        }
    }

    private void swap(Produk[] arr, int one, int two) {
        Produk temp = arr[one];
        arr[one] = arr[two];
        arr[two] = temp;
    }

    public static void main(String[] args) {
        int jumlahBarang = 1000;
        ShopSorter toko = new ShopSorter(jumlahBarang);
        ExecutionTimer timer = new ExecutionTimer();

        // 1. Generate 1000 Produk dengan Harga Acak
        System.out.println("--- 1. Generate " + jumlahBarang + " Data Produk Acak ---");
        for (int i = 0; i < jumlahBarang; i++) {
            // Harga acak antara 10.000 s.d 1.000.000
            int hargaAcak = 10000 + (int) (Math.random() * 990000);
            toko.insert("Barang-" + i, hargaAcak);
        }

        // Siapkan 3 Salinan Data yang Sama Persis (Agar Fair)
        Produk[] data1 = toko.copyArray();
        Produk[] data2 = toko.copyArray();
        Produk[] data3 = toko.copyArray();

        System.out.println("\n--- 2. Mulai Kompetisi Sorting ---");

        // Uji Bubble Sort
        timer.start();
        toko.bubbleSort(data1);
        timer.stop("Bubble Sort");

        // Uji Selection Sort
        timer.start();
        toko.selectionSort(data2);
        timer.stop("Selection Sort");

        // Uji Insertion Sort
        timer.start();
        toko.insertionSort(data3);
        timer.stop("Insertion Sort");

        // Bukti Data Terurut (Tampilkan 3 barang termurah dari hasil Insertion Sort)
        System.out.println("\n--- 3. Validasi Hasil (3 Termurah) ---");
        System.out.println("1. " + data3[0].toString());
        System.out.println("2. " + data3[1].toString());
        System.out.println("3. " + data3[2].toString());
    }
}
