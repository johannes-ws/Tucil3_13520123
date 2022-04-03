import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class FifteenPuzzle implements Comparable<FifteenPuzzle> {
    private int row;
    private int col;
    private int[][] matrix;
    private List<String> path;

    public FifteenPuzzle() {
        this.row = 4;
        this.col = 4;
        this.matrix = new int[this.row][this.col];
        this.path = new ArrayList<>();
        this.path.add("START");
    }

    public FifteenPuzzle(FifteenPuzzle bnb) {
        this.row = 4;
        this.col = 4;
        this.matrix = new int[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                this.matrix[i][j] = bnb.matrix[i][j];
            }
        }
        this.path = new ArrayList<>();
        for (String s : bnb.path) {
            this.path.add(s);
        }
    }

    public FifteenPuzzle(String solution) {
        int a = 0;
        this.row = 4;
        this.col = 4;
        this.matrix = new int[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                a++;
                this.matrix[i][j] = a;
            }
        }
        this.path = new ArrayList<>();
    }

    public int compareTo(FifteenPuzzle o) {
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
                System.out.print(this.matrix[i][j] + " ");
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

    public boolean isSame(FifteenPuzzle bnb) {
        boolean found = true;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (this.matrix[i][j] != bnb.matrix[i][j]) {
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
        this.matrix[x][y] = a;
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
                if (this.matrix[i][j] == a) {
                    now = a;
                } else if (this.matrix[i][j] == b) {
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
                min += this.KURANG(this.matrix[i][j]);
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
                if (this.matrix[i][1] == 16 || this.matrix[i][3] == 16) {
                    found = true;
                }
            } else {
                if (this.matrix[i][0] == 16 || this.matrix[i][2] == 16) {
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
                if (this.matrix[i][j] != count) {
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
            if (this.matrix[0][i] == 16) {
                found = false;
            }
        }
        return found;
    }

    public boolean isUP(FifteenPuzzle bnb) {
        FifteenPuzzle tes = new FifteenPuzzle(bnb);
        tes.UP();
        return this.isSame(tes);
    }

    public void UP() {
        if (this.checkUP()) {
            boolean found = false;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (this.matrix[i][j] == 16) {
                        this.matrix[i][j] = this.matrix[i-1][j];
                        this.matrix[i-1][j] = 16;
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
    }

    public boolean checkDOWN() {
        boolean found = true;
        for (int i = 0; i < this.row; i++) {
            if (this.matrix[3][i] == 16) {
                found = false;
            }
        }
        return found;
    }

    public boolean isDOWN(FifteenPuzzle bnb) {
        FifteenPuzzle tes = new FifteenPuzzle(bnb);
        tes.DOWN();
        return this.isSame(tes);
    }

    public void DOWN() {
        if (this.checkDOWN()) {
            boolean found = false;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (this.matrix[i][j] == 16) {
                        this.matrix[i][j] = this.matrix[i+1][j];
                        this.matrix[i+1][j] = 16;
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
    }

    public boolean checkLEFT() {
        boolean found = true;
        for (int i = 0; i < this.row; i++) {
            if (this.matrix[i][0] == 16) {
                found = false;
            }
        }
        return found;
    }

    public boolean isLEFT(FifteenPuzzle bnb) {
        FifteenPuzzle tes = new FifteenPuzzle(bnb);
        tes.LEFT();
        return this.isSame(tes);
    }

    public void LEFT() {
        if (this.checkLEFT()) {
            boolean found = false;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (this.matrix[i][j] == 16) {
                        this.matrix[i][j] = this.matrix[i][j-1];
                        this.matrix[i][j-1] = 16;
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
    }

    public boolean checkRIGHT() {
        boolean found = true;
        for (int i = 0; i < this.row; i++) {
            if (this.matrix[i][3] == 16) {
                found = false;
            }
        }
        return found;
    }

    public boolean isRIGHT(FifteenPuzzle bnb) {
        FifteenPuzzle tes = new FifteenPuzzle(bnb);
        tes.RIGHT();
        return this.isSame(tes);
    }

    public void RIGHT() {
        if (this.checkRIGHT()) {
            boolean found = false;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (this.matrix[i][j] == 16) {
                        this.matrix[i][j] = this.matrix[i][j+1];
                        this.matrix[i][j+1] = 16;
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
    }

    public void addToPath(List<Character> l, FifteenPuzzle bnb) {
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
                FifteenPuzzle solve = new FifteenPuzzle("solve");
                int bangkit = 0;
                List<Character> l = new ArrayList<>();
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
                        System.out.println(cek.path);
                        break;
                    }
                    if (cek.checkUP() && cek.path.get(cek.path.size()-1) != "DOWN") {
                        FifteenPuzzle up = new FifteenPuzzle(cek);
                        up.path.add("UP");
                        up.UP();
                        pq.add(up);
                        bangkit++;
                    }
                    if (cek.checkRIGHT() && cek.path.get(cek.path.size()-1) != "LEFT") {
                        FifteenPuzzle right = new FifteenPuzzle(cek);
                        right.path.add("RIGHT");
                        right.RIGHT();
                        pq.add(right);
                        bangkit++;
                    }
                    if (cek.checkDOWN() && cek.path.get(cek.path.size()-1) != "UP") {
                        FifteenPuzzle down = new FifteenPuzzle(cek);
                        down.path.add("DOWN");
                        down.DOWN();
                        pq.add(down);
                        bangkit++;
                    }
                    if (cek.checkLEFT() && cek.path.get(cek.path.size()-1) != "RIGHT") {
                        FifteenPuzzle left = new FifteenPuzzle(cek);
                        left.path.add("LEFT");
                        left.LEFT();
                        pq.add(left);
                        bangkit++;
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