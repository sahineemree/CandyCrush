import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        // Oyun başında kullanılacak olan dosya yolu.
        String filePath = "C:\\Java Projelerim\\CandyCrush\\CandyCrush\\AlgoLab.txt";

        // Dosyadan okunan matrisi alır.
        char[][] candyMatris = matrisOku(filePath);

        // Eğer matris düzgün bir şekilde okunduysa devam et.
        if (candyMatris != null) {

            // Matrisi ekrana yazdır.
            printMatris(candyMatris);

            // Kullanıcıdan giriş al.
            Scanner scanner = new Scanner(System.in);

            // Oyun döngüsü.
            while (!oyunSonu(candyMatris)) {
                int row = kullaniciGirisi("Satır numarasını girin (1-10): ", scanner);
                int col = kullaniciGirisi("Sütun numarasını girin (1-10): ", scanner);

                // Girilen koordinatlar geçerliyse işlem yap.
                if (gecerliKoordinat(row, col)) {
                    komsular(candyMatris, row - 1, col - 1, candyMatris[row - 1][col - 1]);
                    printMatris(candyMatris);
                } else {
                    System.out.println("Koordinatlar geçersiz. Satır ve sütun numaraları 1 ile 10 arasında olmak zorundadır.");
                }
            }

            // Oyun başarıyla bittiğinde bitiş cümlesi.
            System.out.println("Tebrikler, oyun bitti!");

        } else {
            // Matris okunmazsa hata mesajı.
            System.out.println("Dosyadan matris okunamadı.");
        }
    }

    // Dosyadan matris okuma işlemini gerçekleştiren metot.
    static char[][] matrisOku(String dosyaYolu) {
        char[][] matris = new char[10][10];

        try (BufferedReader okuma = new BufferedReader(new FileReader(dosyaYolu))) {
            int row = 0;
            String line;

            // Dosyayı satır satır oku ve matrise yerleştir.
            while ((line = okuma.readLine()) != null && row < 10) {
                String[] values = line.split("\\s+");

                for (int col = 0; col < Math.min(values.length, 10); col++) {
                    matris[row][col] = values[col].charAt(0);
                }
                row++;
            }
        } catch (IOException e) {
            // Eğer dosya okuma hatası durumu olursa hata mesajını yazdır ve null döndür.
            System.out.println("Dosya okuma hatası: " + e.getMessage());
            return null;
        }

        return matris;
    }

    // Okunan matrisi ekrana yazdıran metot.
    static void printMatris(char[][] matris) {
        for (char[] row : matris) {
            for (char value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    // Verilen koordinatların geçerli olup olmadığını kontrol eden metot.
    static boolean gecerliKoordinat(int row, int col) {
        return row >= 1 && row <= 10 && col >= 1 && col <= 10;
    }

    // Girilen hücrenin geçerli olup olmadığını kontrol eden metot.
    static boolean gecerliHucre(char[][] matris, int row, int col) {
        return row >= 0 && row < 10 && col >= 0 && col < 10;
    }

    // Komşu hücreleri işaretleyen ve diğer komşu hücrelere de bakarak devam eden metot.
    static void komsular(char[][] matris, int row, int col, char hedefDeger) {
        if (gecerliHucre(matris, row, col) && matris[row][col] == hedefDeger) {
            matris[row][col] = 'X';

            komsular(matris, row - 1, col, hedefDeger); // Üst
            komsular(matris, row, col - 1, hedefDeger); // Sağ
            komsular(matris, row + 1, col, hedefDeger); // Alt
            komsular(matris, row, col + 1, hedefDeger); // Sol
        }
    }

    // Oyunun bitip bitmediğini kontrol eden metot.
    static boolean oyunSonu(char[][] matris) {
        for (char[] row : matris) {
            for (char value : row) {
                if (value != 'X') {
                    return false;
                }
            }
        }
        return true;
    }

    // Kullanıcıdan giriş alıp bir tam sayı döndüren metot.
    static int kullaniciGirisi(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextInt();
    }
}
