/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure;
// 1. Node: Merepresentasikan Data IP Address
class IPNode {
    long ipAddress;
    String threatLevel; // LOW, HIGH
    IPNode left;  // Kaki Kiri (Nilai lebih kecil)
    IPNode right; // Kaki Kanan (Nilai lebih besar)

    public IPNode(long ip, String threat) {
        this.ipAddress = ip;
        this.threatLevel = threat;
    }

    public String toString() {
        return "[IP: " + ipAddress + " | " + threatLevel + "]";
    }
}

// 2. Class Manajemen: Binary Search Tree
class FirewallBST {
    private IPNode root;

    public FirewallBST() { root = null; }

    // Insert: Menambah IP (O(log n))
    public void insert(long ip, String threat) {
        IPNode newNode = new IPNode(ip, threat);
        if (root == null) {
            root = newNode;
        } else {
            IPNode current = root;
            IPNode parent;
            while (true) {
                parent = current;
                // LOGIKA VISUALISASI KIRI/KANAN
                if (ip < current.ipAddress) { 
                    current = current.left;   // Belok Kiri
                    if (current == null) {
                        parent.left = newNode; // Tempel di kiri
                        return;
                    }
                } else { 
                    current = current.right;  // Belok Kanan
                    if (current == null) {
                        parent.right = newNode; // Tempel di kanan
                        return;
                    }
                }
            }
        }
    }

    // Search: Cek apakah IP ada di blacklist
    public IPNode checkIP(long keyIP) {
        IPNode current = root;
        while (current.ipAddress != keyIP) {
            if (keyIP < current.ipAddress) current = current.left;
            else current = current.right;
            
            if (current == null) return null; // Mentok, tidak ketemu
        }
        return current;
    }

    // Traversal In-Order: Untuk mencetak laporan urut
    public void displayInOrder(IPNode localRoot) {
        if (localRoot != null) {
            displayInOrder(localRoot.left);       // 1. Kunjungi Kiri dulu (Paling kecil)
            System.out.println(localRoot);        // 2. Cetak Diri Sendiri
            displayInOrder(localRoot.right);      // 3. Kunjungi Kanan
        }
    }
    
    public IPNode getRoot() { return root; }

public static void main(String[] args) {
    FirewallBST firewall = new FirewallBST();
    ExecutionTimer timer = new ExecutionTimer();

    System.out.println("--- 1. Input Data Acak ke Firewall ---");
    // Urutan Input: 50, 30, 70, 20, 40, 60, 80
    // Ini akan membentuk Perfect Balanced Tree
    firewall.insert(5000L, "ROOT"); 
    firewall.insert(3000L, "LEFT");
    firewall.insert(7000L, "RIGHT");
    firewall.insert(2000L, "LEFT-LEFT");
    firewall.insert(4000L, "LEFT-RIGHT");
    firewall.insert(6000L, "RIGHT-LEFT");
    firewall.insert(8000L, "RIGHT-RIGHT");

    System.out.println("Input Selesai. Struktur Tree terbentuk di memori.");

    System.out.println("\n--- 2. Tampilkan Data (In-Order Traversal) ---");
    // Harusnya tercetak urut dari 2000 s.d 8000
    firewall.displayInOrder(firewall.getRoot());

    System.out.println("\n--- 3. Benchmark Pencarian (Search) ---");
    long target = 2000L; // Posisi di paling bawah kiri (Leaf)
    
    timer.start();
    IPNode hasil = firewall.checkIP(target);
    timer.stop("BST Search (Deepest Node)");
    
    if(hasil != null) System.out.println("   -> Ditemukan: " + hasil);
}}
