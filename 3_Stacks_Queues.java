/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure;
// A. STACK: History Browser

class BrowserHistory {

    private String[] history;
    private int top;
    private int maxSize;

    public BrowserHistory(int size) {
        this.maxSize = size;
        history = new String[maxSize];
        top = -1; // Stack kosong
    }

    public void visit(String url) {
        if (top < maxSize - 1) {
            history[++top] = url; // Naikkan tumpukan, taruh data
        } else {
            System.out.println(">> History Penuh!");
        }
    }

    public String back() {
        if (top == -1) {
            return "Homepage";
        }
        return history[top--]; // Ambil data paling atas, lalu turunkan pointer
    }
}

// B. QUEUE: Printer (Circular)
class PrinterQueue {

    private String[] jobQueue;
    private int maxSize;
    private int front; // Penunjuk orang terdepan
    private int rear;  // Penunjuk orang terakhir
    private int nItems;

    public PrinterQueue(int size) {
        this.maxSize = size;
        jobQueue = new String[maxSize];
        front = 0;
        rear = -1;
        nItems = 0;
    }

    public void addJob(String docName) {
        if (nItems == maxSize) {
            System.out.println(">> [Full] Printer sibuk! " + docName + " ditolak.");
        } else {
            // LOGIKA CIRCULAR: Jika sudah di ujung array, putar balik ke index 0
            if (rear == maxSize - 1) {
                rear = -1;
            }
            jobQueue[++rear] = docName;
            nItems++;
        }
    }

    public String printJob() {
        if (nItems == 0) {
            return null;
        }

        String temp = jobQueue[front++];
        // LOGIKA CIRCULAR: Jika front sudah di ujung, putar balik ke 0
        if (front == maxSize) {
            front = 0;
        }
        nItems--;
        return temp;
    }

    public boolean isEmpty() {
        return (nItems == 0);
    }

    public static void main(String[] args) {
        ExecutionTimer timer = new ExecutionTimer();

        // --- TEST 1: STACK (Browser) ---
        System.out.println("=== 1. SIMULASI BROWSER (STACK) ===");
        BrowserHistory browser = new BrowserHistory(5);

        // User membuka 3 web berurutan
        browser.visit("www.google.com");
        browser.visit("www.youtube.com");
        browser.visit("www.instagram.com");

        // User tekan tombol Back
        System.out.println("Posisi saat ini: Instagram. User tekan Back...");

        timer.start();
        String prevPage = browser.back(); // Harusnya Instagram yang ke-pop
        timer.stop("Stack Pop");

        System.out.println("   -> Kembali ke: " + browser.back()); // Harusnya Youtube

        // --- TEST 2: QUEUE (Printer) ---
        System.out.println("\n=== 2. SIMULASI PRINTER (QUEUE) ===");
        PrinterQueue printer = new PrinterQueue(3); // Kapasitas cuma 3

        // Masuk 3 Dokumen
        printer.addJob("Skripsi.pdf");    // Masuk ke-1
        printer.addJob("Foto.jpg");       // Masuk ke-2
        printer.addJob("Tiket.pdf");      // Masuk ke-3

        // Test Circular: Masukkan dokumen ke-4 (Harusnya ditolak dulu)
        printer.addJob("Dokumen-Extra.docx");

        System.out.println("Printer mulai mencetak...");

        timer.start();
        String job1 = printer.printJob(); // Harusnya Skripsi
        timer.stop("Queue Dequeue");

        System.out.println("   -> Selesai cetak: " + job1);

        // Sekarang slot kosong 1 (bekas Skripsi), kita masukkan dokumen baru
        // Ini membuktikan logika Circular (memakai slot indeks 0 yang kosong)
        printer.addJob("Dokumen-Susulan.docx");
        System.out.println("   -> Berhasil input: Dokumen-Susulan.docx (Masuk ke slot 0)");
    }
}
