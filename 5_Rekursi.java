/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure;

import java.util.ArrayList;
import java.util.List;

class FileNode {

    String name;
    long size; // KB
    boolean isFolder;
    List<FileNode> children;

    public FileNode(String name, long size, boolean isFolder) {
        this.name = name;
        this.size = size;
        this.isFolder = isFolder;
        this.children = new ArrayList<>();
    }

    public void addContent(FileNode node) {
        if (this.isFolder) {
            children.add(node);
        }
    }
}

// 2. Class Algoritma Rekursi
class DiskScanner {

    public long calculateTotalSize(FileNode node) {
        // --- BASE CASE (Kondisi Berhenti) ---
        // Jika ini File, kembalikan ukurannya. Jangan panggil fungsi lagi.
        if (!node.isFolder) {
            return node.size;
        }

        // --- RECURSIVE STEP (Panggil Diri Sendiri) ---
        // Jika ini Folder, panggil fungsi ini untuk SEMUA isinya
        long total = 0;
        for (FileNode child : node.children) {
            total += calculateTotalSize(child); // <--- REKURSI TERJADI DISINI
        }
        return total;
    }

    // Helper: Menampilkan Tree Struktur Folder
    public void printTree(FileNode node, String indent) {
        System.out.println(indent + "|-- " + node.name);
        if (node.isFolder) {
            for (FileNode child : node.children) {
                printTree(child, indent + "    ");
            }
        }
    }

    public static void main(String[] args) {
        DiskScanner scanner = new DiskScanner();
        ExecutionTimer timer = new ExecutionTimer();

        // 1. Setup Struktur Folder
        FileNode root = new FileNode("My Documents", 0, true);

        FileNode kuliah = new FileNode("Kuliah", 0, true);
        kuliah.addContent(new FileNode("Skripsi_Bab1.docx", 2500, false));
        kuliah.addContent(new FileNode("Skripsi_Bab2.docx", 3000, false));

        FileNode smt1 = new FileNode("Semester 1", 0, true);
        smt1.addContent(new FileNode("Algoritma.ppt", 5000, false));
        kuliah.addContent(smt1); // Folder dalam folder

        FileNode foto = new FileNode("Foto", 0, true);
        foto.addContent(new FileNode("Liburan.jpg", 4000, false));

        root.addContent(kuliah);
        root.addContent(foto);

        System.out.println("--- 1. Struktur File ---");
        scanner.printTree(root, "");

        // 2. Hitung Size
        System.out.println("\n--- 2. Scan Total Size ---");
        timer.start();
        long total = scanner.calculateTotalSize(root);
        timer.stop("Recursive Scan");

        System.out.println(">> Total Ukuran: " + total + " KB");
    }
}
