import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
		FifteenPuzzle matrix1 = new FifteenPuzzle();
        int x = 0;
        int y = 0;
        try {
            System.out.print("Masukkan nama file: ");
            Scanner myFile = new Scanner(System.in);
            String file = myFile.nextLine();
            File myObj = new File("test/" + file);
            myFile.close();
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextInt()) {
                int data = myReader.nextInt();
                matrix1.setElement(x, y, data);
                y++;
                if (y == 4) {
                    x++;
                    y = 0;
                }
            }
            myReader.close();
            long startTime = System.currentTimeMillis();

            matrix1.showMatrix();
            matrix1.showKurang();
            System.out.println(matrix1.sigmaKurang());
            if (matrix1.sigmaKurang() % 2 == 0) {
                System.out.println("bisa diselesaikan");
                FifteenPuzzle solve = new FifteenPuzzle("solve");
                int bangkit = 0;
                List<String> l = new ArrayList<>();
                PriorityQueue<FifteenPuzzle> pq = new PriorityQueue<>();
                pq.add(matrix1);
                FifteenPuzzle now = new FifteenPuzzle();
                FifteenPuzzle cek = new FifteenPuzzle();
                while (!pq.isEmpty()) {
                    now = new FifteenPuzzle(cek);
                    cek = new FifteenPuzzle(pq.poll());
                    cek.addToPath(l, now);
                    if (cek.isSame(solve)) {
                        System.out.println(" ketemu");
                        System.out.println(cek.getPath());
                        break;
                    }
                    if (cek.checkUP() && cek.getPath().get(cek.getPath().size()-1) != "DOWN") {
                        FifteenPuzzle up = new FifteenPuzzle(cek);
                        up.getPath().add("UP");
                        up.UP();
                        pq.add(up);
                        bangkit++;
                    }
                    if (cek.checkRIGHT() && cek.getPath().get(cek.getPath().size()-1) != "LEFT") {
                        FifteenPuzzle right = new FifteenPuzzle(cek);
                        right.getPath().add("RIGHT");
                        right.RIGHT();
                        pq.add(right);
                        bangkit++;
                    }
                    if (cek.checkDOWN() && cek.getPath().get(cek.getPath().size()-1) != "UP") {
                        FifteenPuzzle down = new FifteenPuzzle(cek);
                        down.getPath().add("DOWN");
                        down.DOWN();
                        pq.add(down);
                        bangkit++;
                    }
                    if (cek.checkLEFT() && cek.getPath().get(cek.getPath().size()-1) != "RIGHT") {
                        FifteenPuzzle left = new FifteenPuzzle(cek);
                        left.getPath().add("LEFT");
                        left.LEFT();
                        pq.add(left);
                        bangkit++;
                    }
                }
                System.out.println(bangkit);
                for (String lol : l) {
                    System.out.print(lol);
                }
            } else {
                System.out.println("tidak bisa diselesaikan");
            }

            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println(elapsedTime);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }


    }
}