package datastructure;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// 1. Objek Data: Rumah (Vertex)
class Rumah {
    public char label; // Nama Rumah: A, B, C...
    public boolean dikunjungi;

    public Rumah(char lab) {
        label = lab;
        dikunjungi = false;
    }
}

// 2. Class Manajemen: Graph
class DroneMap {
    private final int MAX = 20;
    private Rumah[] daftarRumah;
    private int[][] jalan; // Matriks hubungan (1=Ada Jalan, 0=Buntu)
    private int jumlahRumah;
    
    // Alat bantu memori
    private Stack<Integer> tumpukanDFS;
    private Queue<Integer> antrianBFS;

    public DroneMap() {
        daftarRumah = new Rumah[MAX];
        jalan = new int[MAX][MAX];
        jumlahRumah = 0;
        tumpukanDFS = new Stack<>();
        antrianBFS = new LinkedList<>();
    }

    public void tambahRumah(char lab) {
        daftarRumah[jumlahRumah++] = new Rumah(lab);
    }

    public void tambahJalan(int start, int end) {
        jalan[start][end] = 1; // Jalan satu arah
    }

    // --- ALGORITMA 1: DFS (Cari jalan sembarang yang penting sampai) ---
    public void cariJalanDFS(int start, int end) {
        resetFlags();
        System.out.print("Rute DFS (Si Nekat): ");
        
        daftarRumah[start].dikunjungi = true;
        tumpukanDFS.push(start);
        System.out.print(daftarRumah[start].label + " ");

        while(!tumpukanDFS.isEmpty()) {
            int current = tumpukanDFS.peek();
            if (current == end) break; // Sampai tujuan!

            int tetangga = getTetanggaBelumDikunjungi(current);
            if(tetangga == -1) {
                tumpukanDFS.pop(); // Jalan buntu, mundur
            } else {
                daftarRumah[tetangga].dikunjungi = true;
                tumpukanDFS.push(tetangga);
                System.out.print("-> " + daftarRumah[tetangga].label + " ");
            }
        }
        System.out.println();
    }

    // --- ALGORITMA 2: BFS (Cari jalan dengan lompatan tersedikit) ---
    public int hitungLompatanBFS(int start, int end) {
        resetFlags();
        int[] jumlahLompatan = new int[MAX]; // Simpan jarak
        
        daftarRumah[start].dikunjungi = true;
        antrianBFS.add(start);
        jumlahLompatan[start] = 0;

        while(!antrianBFS.isEmpty()) {
            int current = antrianBFS.remove();
            if (current == end) return jumlahLompatan[current]; // Sampai!

            int tetangga;
            // Cek semua tetangga terdekat (Satu RT)
            while((tetangga = getTetanggaBelumDikunjungi(current)) != -1) {
                daftarRumah[tetangga].dikunjungi = true;
                jumlahLompatan[tetangga] = jumlahLompatan[current] + 1; // Tambah 1 hop
                antrianBFS.add(tetangga);
            }
        }
        return -1; // Tidak ada jalan
    }

    private int getTetanggaBelumDikunjungi(int v) {
        for(int j=0; j<jumlahRumah; j++)
            if(jalan[v][j]==1 && !daftarRumah[j].dikunjungi) return j;
        return -1;
    }

    private void resetFlags() {
        for(int i=0; i<jumlahRumah; i++) daftarRumah[i].dikunjungi = false;
        tumpukanDFS.clear();
        antrianBFS.clear();
    }
public static void main(String[] args) {
    DroneMap komplek = new DroneMap();
    ExecutionTimer timer = new ExecutionTimer();

    // 1. Buat Peta 5 Rumah (A, B, C, D, E)
    komplek.tambahRumah('A'); // 0
    komplek.tambahRumah('B'); // 1
    komplek.tambahRumah('C'); // 2
    komplek.tambahRumah('D'); // 3
    komplek.tambahRumah('E'); // 4

    // 2. Buat Jalan
    komplek.tambahJalan(0, 1); // A -> B (Jalur Atas)
    komplek.tambahJalan(1, 4); // B -> E
    
    komplek.tambahJalan(0, 2); // A -> C (Jalur Bawah)
    komplek.tambahJalan(2, 3); // C -> D
    komplek.tambahJalan(3, 4); // D -> E

    System.out.println("--- 1. Simulasi Drone Terbang (A ke E) ---");
    
    // Test DFS
    // DFS biasanya akan "terperosok" ke jalur yang pertama dia temukan (misal bawah)
    // meskipun jalur itu lebih jauh.
    timer.start();
    komplek.cariJalanDFS(0, 4);
    timer.stop("DFS Route Finding");

    // Test BFS
    // BFS dijamin menemukan jumlah lompatan paling sedikit (Jalur Atas: A-B-E)
    timer.start();
    int hops = komplek.hitungLompatanBFS(0, 4);
    timer.stop("BFS Shortest Hop");
    
    System.out.println(">> Jarak Terpendek (BFS): " + hops + " Lompatan/Transit.");
}
}