import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class BnB extends Object implements Comparable<BnB> {
    private int[][] matrix1;
    private int row;
    private int col;

    public BnB() {
        this.row = 4;
        this.col = 4;
        this.matrix1 = new int[this.row][this.col];
    }

    public BnB(BnB bnb) {
        this.row = 4;
        this.col = 4;
        this.matrix1 = new int[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                this.matrix1[i][j] = bnb.matrix1[i][j];
            }
        }
    }

    public BnB(String solution) {
        int a = 0;
        this.row = 4;
        this.col = 4;
        this.matrix1 = new int[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                a++;
                this.matrix1[i][j] = a;
            }
        }
    }

    public int compareTo(BnB o) {
        if (this.COST() > o.COST()) {
            return 1;
        } else if (this.COST() == o.COST()) {
            return 0;
        } else {
            return -1;
        }
    }

    public void show() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                System.out.print(this.matrix1[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void showKURANG() {
        int a = 0;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                a++;
                System.out.printf("kurang(%d) = %d%n", a, this.KURANG(a));
            }
        }
    }

    public boolean isSame(BnB bnb) {
        boolean found = true;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (this.matrix1[i][j] != bnb.matrix1[i][j]) {
                    found = false;
                }
                if (!found) {
                    break;
                }
            }
            if (!found) {
                break;
            }
        }
        return found;
    }

    public void set(int x, int y, int a) {
        this.matrix1[x][y] = a;
    }

    public int KURANG(int a) {
        int min = 0;
        for (int i = 1; i < a; i++) {
            if (this.POSISI(a, i)) {
                min++;
            }
        }
        return min;
    }

    public boolean POSISI(int a, int b) {
        int now = 0;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (this.matrix1[i][j] == a) {
                    now = a;
                } else if (this.matrix1[i][j] == b) {
                    now = b;
                }
            }
        }
        if (now == b) {
            return true;
        } else {
            return false;
        }
    }

    public int TOTALKURANG() {
        int min = 0;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                min += this.KURANG(this.matrix1[i][j]);
            }
        }
        if (this.KOSONG()) {
            min++;
        }
        return min;
    }

    public boolean KOSONG() {
        boolean found = false;
        for (int i = 0; i < this.row; i++) {
            if (i % 2 == 0) {
                if (this.matrix1[i][1] == 16 || this.matrix1[i][3] == 16) {
                    found = true;
                }
            } else {
                if (this.matrix1[i][0] == 16 || this.matrix1[i][2] == 16) {
                    found = true;
                }
            }
        }
        return found;
    }

    public int COST() {
        int min = 0;
        int count = 1;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (this.matrix1[i][j] != count) {
                    min++;
                }
                count++;
                if (count == 16) {
                    break;
                }
            }
        }
        return min + 1;
    }

    public boolean checkUP() {
        boolean found = true;
        for (int i = 0; i < this.row; i++) {
            if (this.matrix1[0][i] == 16) {
                found = false;
            }
        }
        return found;
    }

    public boolean isUP(BnB bnb) {
        bnb.UP();
        return this.isSame(bnb);
    }

    public void UP() {
        boolean found = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.matrix1[i][j] == 16) {
                    this.matrix1[i][j] = this.matrix1[i-1][j];
                    this.matrix1[i-1][j] = 16;
                    found = true;
                }
                if (found) {
                    break;
                }
            }
            if (found) {
                break;
            }
        }
    }

    public boolean checkDOWN() {
        boolean found = true;
        for (int i = 0; i < this.row; i++) {
            if (this.matrix1[3][i] == 16) {
                found = false;
            }
        }
        return found;
    }

    public boolean isDOWN(BnB bnb) {
        bnb.DOWN();
        return this.isSame(bnb);
    }

    public void DOWN() {
        boolean found = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.matrix1[i][j] == 16) {
                    this.matrix1[i][j] = this.matrix1[i+1][j];
                    this.matrix1[i+1][j] = 16;
                    found = true;
                }
                if (found) {
                    break;
                }
            }
            if (found) {
                break;
            }
        }
    }

    public boolean checkLEFT() {
        boolean found = true;
        for (int i = 0; i < this.row; i++) {
            if (this.matrix1[i][0] == 16) {
                found = false;
            }
        }
        return found;
    }

    public boolean isLEFT(BnB bnb) {
        bnb.LEFT();
        return this.isSame(bnb);
    }

    public void LEFT() {
        boolean found = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.matrix1[i][j] == 16) {
                    this.matrix1[i][j] = this.matrix1[i][j-1];
                    this.matrix1[i][j-1] = 16;
                    found = true;
                }
                if (found) {
                    break;
                }
            }
            if (found) {
                break;
            }
        }
    }

    public boolean checkRIGHT() {
        boolean found = true;
        for (int i = 0; i < this.row; i++) {
            if (this.matrix1[i][3] == 16) {
                found = false;
            }
        }
        return found;
    }

    public boolean isRIGHT(BnB bnb) {
        bnb.RIGHT();
        return this.isSame(bnb);
    }

    public void RIGHT() {
        boolean found = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.matrix1[i][j] == 16) {
                    this.matrix1[i][j] = this.matrix1[i][j+1];
                    this.matrix1[i][j+1] = 16;
                    found = true;
                }
                if (found) {
                    break;
                }
            }
            if (found) {
                break;
            }
        }
    }

    public void addToPath(List<Character> l, BnB bnb) {
        if (isUP(bnb)) {
            l.add('u');
        } else if (isDOWN(bnb)) {
            l.add('d');
        } else if (isLEFT(bnb)) {
            l.add('l');
        } else if (isRIGHT(bnb)) {
            l.add('r');
        }
    }

    public static void main(String[] args) {
		BnB matrix1 = new BnB();
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
                matrix1.set(x, y, data);
                y++;
                if (y == 4) {
                    x++;
                    y = 0;
                }
            }
            myReader.close();
            long startTime = System.currentTimeMillis();

            matrix1.show();
            matrix1.showKURANG();
            System.out.println(matrix1.TOTALKURANG());
            if (matrix1.TOTALKURANG() % 2 == 0) {
                System.out.println("bisa diselesaikan");
                BnB solve = new BnB("solve");
                int bangkit = 0;
                List<Character> l = new ArrayList<>();
                PriorityQueue<BnB> pq = new PriorityQueue<>();
                pq.add(matrix1);
                BnB now = new BnB();
                BnB cek = new BnB();
                while (!pq.isEmpty()) {
                    now = new BnB(cek);
                    cek = new BnB(pq.poll());
                    cek.addToPath(l, now);
                    if (cek.isSame(solve)) {
                        System.out.println(" ketemu");
                        break;
                    }
                    if (cek.checkUP()) {
                        BnB up = new BnB(cek);
                        up.UP();
                        pq.add(up);
                        bangkit++;
                        System.out.print("u");
                    }
                    if (cek.checkRIGHT()) {
                        BnB right = new BnB(cek);
                        right.RIGHT();
                        pq.add(right);
                        bangkit++;
                        System.out.print("r");
                    }
                    if (cek.checkDOWN()) {
                        BnB down = new BnB(cek);
                        down.DOWN();
                        pq.add(down);
                        bangkit++;
                        System.out.print("d");
                    }
                    if (cek.checkLEFT()) {
                        BnB left = new BnB(cek);
                        left.LEFT();
                        pq.add(left);
                        bangkit++;
                        System.out.print("l");
                    }
                }
                System.out.println(bangkit);
                for (Character lol : l) {
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