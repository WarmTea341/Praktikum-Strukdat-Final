/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure;
// 1. Objek Data: Sesi User
class UserSession {
    String token;    // Key (Misal: "TOKEN-A")
    String username; // Value (Misal: "Andi")

    public UserSession(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String toString() {
        return "{Token: " + token + " | User: " + username + "}";
    }
}

// 2. Class Manajemen: Hash Table
class AuthServer {
    private UserSession[] table;
    private int size;

    public AuthServer(int size) {
        this.size = size;
        table = new UserSession[size];
    }

    // Fungsi Hash Sederhana (Modulo)
    // Mengambil karakter terakhir token sebagai index
    // Contoh: "TOKEN-2" -> 2
    private int hashFunc(String token) {
        char lastChar = token.charAt(token.length() - 1);
        if(Character.isDigit(lastChar)) {
            return Character.getNumericValue(lastChar) % size;
        }
        return 0; // Default
    }

    // Insert (Login) dengan Linear Probing
    public void login(String token, String name) {
        int index = hashFunc(token);
        int originalIndex = index;

        System.out.print("Login '" + name + "' (Hash " + index + "): ");

        // LOGIKA LINEAR PROBING:
        // Selama slot terisi, geser ke kanan
        while (table[index] != null) {
            System.out.print("Slot " + index + " Penuh -> Geser.. ");
            index++;
            index %= size; // Putar balik ke 0 jika mentok ujung
            
            if(index == originalIndex) {
                System.out.println("FULL! Gagal Masuk.");
                return;
            }
        }
        
        table[index] = new UserSession(token, name);
        System.out.println("Masuk Slot [" + index + "]");
    }

    // Find (Autentikasi)
    public UserSession authenticate(String keyToken) {
        int index = hashFunc(keyToken);
        int originalIndex = index;

        while (table[index] != null) {
            if (table[index].token.equals(keyToken)) {
                return table[index]; // Ketemu
            }
            index++;
            index %= size;
            
            if (index == originalIndex) return null; // Balik ke awal, data gak ada
        }
        return null;
    }

    public void displayTable() {
        System.out.println("\nStatus Server Storage:");
        for (int i = 0; i < size; i++) {
            if (table[i] != null) 
                System.out.println(" [" + i + "] " + table[i].toString());
            else 
                System.out.println(" [" + i + "] -- Kosong --");
        }
    }
public static void main(String[] args) {
    int kapasitas = 5;
    AuthServer server = new AuthServer(kapasitas);
    ExecutionTimer timer = new ExecutionTimer();

    System.out.println("--- 1. Simulasi Login & Collision ---");
    
    // Skenario Normal
    server.login("USR-2", "Andi");  // Hash 2
    server.login("USR-4", "Citra"); // Hash 4
    
    // Skenario Collision (Tabrakan)
    // Budi punya hash 2, tapi slot 2 sudah diisi Andi.
    server.login("USR-12", "Budi"); 

    // Skenario Collision Beruntun
    // Dedi punya hash 2. Slot 2 isi (Andi), Slot 3 isi (Budi), harus geser ke 4?
    // Slot 4 isi (Citra). Geser ke 0.
    server.login("USR-22", "Dedi"); 

    server.displayTable();

    System.out.println("\n--- 2. Benchmark Autentikasi ---");
    // Mencari Budi (yang posisinya tergeser)
    String targetToken = "USR-12";
    
    timer.start();
    UserSession sess = server.authenticate(targetToken);
    timer.stop("Search User (Probing)");
    
    if (sess != null) 
        System.out.println("   -> Login Sukses: " + sess.username + " ditemukan di slot yang tergeser.");
}
}
