/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package datastructure;
public class ExecutionTimer {
    private long startTime;
    private long endTime;

    // Tekan tombol 'Start' pada stopwatch
    public void start() {
        this.startTime = System.nanoTime();
    }

    // Tekan tombol 'Stop' dan tampilkan hasilnya
    public void stop(String taskName) {
        this.endTime = System.nanoTime();
        long duration = this.endTime - this.startTime;
        
        // Konversi ke milidetik agar lebih mudah dibaca manusia
        double durationMs = duration / 1_000_000.0;

        System.out.println("--------------------------------------------------");
        System.out.println(">> [BENCHMARK] Task: " + taskName);
        System.out.println(">> Time Elapsed : " + duration + " ns (" + durationMs + " ms)");
        System.out.println("--------------------------------------------------");
    }
}
