package datastructure;
// 1. Objek Data: Representasi Satu Mahasiswa
class Mahasiswa {
    long nim;
    String nama;
    int nilai;

    public Mahasiswa(long nim, String nama, int nilai) {
        this.nim = nim;
        this.nama = nama;
        this.nilai = nilai;
    }
    
    public String toString() {
        return "[NIM: " + nim + " | Nama: " + nama + "]";
    }
}

// 2. Class Manajemen Array
class DataKelas {
    private Mahasiswa[] daftarMhs;
    private int jumlahMhs;
    private int kapasitas;

    public DataKelas(int max) {
        this.kapasitas = max;
        daftarMhs = new Mahasiswa[kapasitas];
        jumlahMhs = 0;
    }

    // Insert Data (Sequential/Berurutan)
    public void insert(long nim, String nama, int nilai) {
        if (jumlahMhs == kapasitas) {
            System.out.println(">> [ERROR] Kelas Penuh! Mahasiswa " + nama + " ditolak.");
            return;
        }
        daftarMhs[jumlahMhs] = new Mahasiswa(nim, nama, nilai);
        jumlahMhs++;
    }

    // Linear Search: Cek satu per satu (Brute Force)
    public Mahasiswa findLinear(long cariNIM) {
        for (int i = 0; i < jumlahMhs; i++) {
            if (daftarMhs[i].nim == cariNIM) return daftarMhs[i];
        }
        return null;
    }

    // Binary Search: Divide and Conquer
    public Mahasiswa findBinary(long cariNIM) {
        int batasBawah = 0;
        int batasAtas = jumlahMhs - 1;

        while (true) {
            int tengah = (batasBawah + batasAtas) / 2;
            if (daftarMhs[tengah].nim == cariNIM) return daftarMhs[tengah]; // Ketemu
            else if (batasBawah > batasAtas) return null; // Tidak ketemu
            else {
                // Karena NIM urut, kita bisa eliminasi setengah data
                if (daftarMhs[tengah].nim < cariNIM)
                    batasBawah = tengah + 1; // Cari di kanan
                else
                    batasAtas = tengah - 1;  // Cari di kiri
            }
        }
    }
}

// 3. Main Program & Benchmark
public class Arrays {
    public static void main(String[] args) {
        int maxKursi = 100;
        DataKelas kelasTI = new DataKelas(maxKursi);
        ExecutionTimer timer = new ExecutionTimer();

        // A. Simulasi Isi Data (PENTING: Insert Berurutan agar Binary Search jalan)
        System.out.println("--- 1. Input 100 Mahasiswa (NIM 2000-2099) ---");
        for (int i = 0; i < maxKursi; i++) {
            kelasTI.insert(2000 + i, "Mhs-" + i, 80); 
        }

        // B. Simulasi Kapasitas Penuh (Overflow)
        System.out.println("\n--- 2. Test Input Data ke-101 ---");
        kelasTI.insert(9999, "Budi Telat", 90);

        // C. Benchmark Pencarian (Mencari data paling ujung: NIM 2099)
        long targetNIM = 2099;
        System.out.println("\n--- 3. Benchmark Pencarian NIM: " + targetNIM + " ---");

        timer.start();
        Mahasiswa h1 = kelasTI.findLinear(targetNIM);
        timer.stop("Linear Search (Cek 100 data)");
        if(h1 != null) System.out.println("   -> Hasil: " + h1.toString());

        timer.start();
        Mahasiswa h2 = kelasTI.findBinary(targetNIM);
        timer.stop("Binary Search (Cek log2 data)");
        if(h2 != null) System.out.println("   -> Hasil: " + h2.toString());
    }
}
