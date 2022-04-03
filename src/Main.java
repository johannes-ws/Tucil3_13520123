import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println(" __ _____       _____  _    _ _____________      ______    _____  ____  _ __      ________ _____       ");
        System.out.println("/_ | ____|     |  __ \\| |  | |___  /___  / |    |  ____|  / ____|/ __ \\| |\\ \\    / /  ____|  __ \\ ");
        System.out.println(" | | |__ ______| |__) | |  | |  / /   / /| |    | |__    | (___ | |  | | | \\ \\  / /| |__  | |__) |   ");
        System.out.println(" | |___ \\______|  ___/| |  | | / /   / / | |    |  __|    \\___ \\| |  | | |  \\ \\/ / |  __| |  _  / ");
        System.out.println(" | |___) |     | |    | |__| |/ /__ / /__| |____| |____   ____) | |__| | |___\\  /  | |____| | \\ \\   ");
        System.out.println(" |_|____/      |_|     \\____//_____/_____|______|______| |_____/ \\____/|______\\/   |______|_|  \\_\\");

		FifteenPuzzle matrix = new FifteenPuzzle();
        int row = 0;
        int col = 0;

        try {
            // input file
            System.out.print("\nMasukkan nama file: ");
            Scanner scanFile = new Scanner(System.in);
            String file = scanFile.nextLine();
            scanFile.close();

            // baca file
            File myFile = new File("test/" + file);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextInt()) {
                int data = myReader.nextInt();
                matrix.setElement(row, col, data);
                col++;
                if (col == 4) {
                    row += 1;
                    col = 0;
                }
            }
            myReader.close();

            // menampilkan matriks posisi awal
            System.out.println();
            matrix.showMatrix();
            
            // menampilkan nilai dari fungsi Kurang(i)
            System.out.println();
            matrix.showKurang();
            
            // menampilkan nilai dari sigma Kurang(i) + X
            System.out.printf("Nilai dari sigma Kurang(i) + X = %d%n%n", matrix.sigmaKurang());
            
            if (matrix.sigmaKurang() % 2 == 0) {
                // waktu dimulai
                long startTime = System.currentTimeMillis();
    
                FifteenPuzzle solution = new FifteenPuzzle("solution");
                List<String> solutionPath = new ArrayList<>();
                int totalNode = 0;

                // memasukkan matriks awal ke pq
                PriorityQueue<FifteenPuzzle> pq = new PriorityQueue<>();
                pq.add(matrix);

                FifteenPuzzle previous = new FifteenPuzzle();
                FifteenPuzzle now = new FifteenPuzzle();
                while (!pq.isEmpty()) {
                    previous = new FifteenPuzzle(now);
                    now = new FifteenPuzzle(pq.poll());

                    // menambahkan path untuk menuju matriks tersebut
                    now.addToPath(solutionPath, previous);

                    // jika sudah ketemu, looping akan berhenti
                    if (now.isSame(solution)) {
                        break;
                    }

                    // pembangkitan simpul untuk command UP
                    if (now.checkUP() && now.getPath().get(now.getPath().size()-1) != "DOWN") {
                        FifteenPuzzle up = new FifteenPuzzle(now);
                        up.UP();
                        up.getPath().add("UP");
                        pq.add(up);
                        totalNode += 1;
                    }

                    // pembangkitan simpul untuk command RIGHT
                    if (now.checkRIGHT() && now.getPath().get(now.getPath().size()-1) != "LEFT") {
                        FifteenPuzzle right = new FifteenPuzzle(now);
                        right.RIGHT();
                        right.getPath().add("RIGHT");
                        pq.add(right);
                        totalNode += 1;
                    }

                    // pembangkitan simpul untuk command DOWN
                    if (now.checkDOWN() && now.getPath().get(now.getPath().size()-1) != "UP") {
                        FifteenPuzzle down = new FifteenPuzzle(now);
                        down.DOWN();
                        down.getPath().add("DOWN");
                        pq.add(down);
                        totalNode += 1;
                    }

                    // pembangkitan simpul untuk command LEFT
                    if (now.checkLEFT() && now.getPath().get(now.getPath().size()-1) != "RIGHT") {
                        FifteenPuzzle left = new FifteenPuzzle(now);
                        left.LEFT();
                        left.getPath().add("LEFT");
                        pq.add(left);
                        totalNode += 1;
                    }
                }
                
                // waktu berhenti
                long stopTime = System.currentTimeMillis();
    
                // menampilkan urutan matriks dari posisi awal ke posisi akhir
                int count = 0;
                System.out.println("Posisi awal:");
                matrix.showMatrix();
                System.out.println();
                for (String command : solutionPath) {
                    count++;
                    System.out.printf("Langkah ke-%d: %s%n", count, command);
                    matrix.todo(command);
                    matrix.showMatrix();
                    System.out.println();
                }

                // menghitung dan menampilkan waktu eksekusi program
                long elapsedTime = stopTime - startTime;
                System.out.printf("Waktu eksekusi program = %d ms%n", elapsedTime);

                // menampilkan jumlah simpul yang dibangkitkan
                System.out.printf("Jumlah simpul yang dibangkitkan = %d%n", totalNode);

            } else {
                System.out.println("Puzzle ini tidak dapat diselesaikan!");
            }    

        } catch (FileNotFoundException FileNotFound) {
            System.out.println("\nFile tidak ditemukan!");

        } catch (OutOfMemoryError OutOfMemory) {
            System.out.println("Nampaknya anda belum beruntung, silakan coba lagi :)");
        }
    }
}