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

    // default constructor
    public FifteenPuzzle() {
        this.row = 4;
        this.col = 4;
        this.matrix = new int[this.row][this.col];
        this.path = new ArrayList<>();
        this.path.add("START");
    }    
    
    // solution constructor
    public FifteenPuzzle(String solution) {
        int number = 0;
        this.row = 4;
        this.col = 4;
        this.matrix = new int[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                number++;
                this.matrix[i][j] = number;
            }
        }
        this.path = new ArrayList<>();
    }

    // copy constructor
    public FifteenPuzzle(FifteenPuzzle fifteen_puzzle) {
        this.row = 4;
        this.col = 4;
        this.matrix = new int[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                this.matrix[i][j] = fifteen_puzzle.matrix[i][j];
            }    
        }    
        this.path = new ArrayList<>();
        for (String string : fifteen_puzzle.path) {
            this.path.add(string);
        }    
    }    

    // menentukan PriorityQueue berdasarkan nilai cost suatu matriks
    public int compareTo(FifteenPuzzle fifteen_puzzle) {
        if (this.cost() > fifteen_puzzle.cost()) {
            return 1;
        } else if (this.cost() == fifteen_puzzle.cost()) {
            return 0;
        } else {
            return -1;
        }
    }

    // menampilkan suatu matriks ke layar
    public void showMatrix() {
        System.out.println("+----+----+----+----+");
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (this.matrix[i][j] >= 1 && this.matrix[i][j] <= 9) {
                    System.out.printf("|  %d ", this.matrix[i][j]);
                } else if (this.matrix[i][j] >= 10 && this.matrix[i][j] <= 15) {
                    System.out.printf("| %d ", this.matrix[i][j]);
                } else {
                    System.out.print("|    ");
                }
            }
            System.out.println("|");
            System.out.println("+----+----+----+----+");
        }
    }

    // menampilkan nilai dari fungsi Kurang(i)
    public void showKurang() {
        System.out.println("+----+-----------+");
        System.out.println("|  i | Kurang(i) |");
        System.out.println("+----+-----------+");
        int number = 0;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                number++;
                if (number >= 1 && number <= 9 && this.kurang(number) >= 0 && this.kurang(number) <= 9) {
                    System.out.printf("|  %d |     %d     |%n", number, this.kurang(number));
                } else if (number >= 1 && number <= 9 && this.kurang(number) >= 10) {
                    System.out.printf("|  %d |    %d     |%n", number, this.kurang(number));
                } else if (number >= 10 && this.kurang(number) >= 0 && this.kurang(number) <= 9) {
                    System.out.printf("| %d |     %d     |%n", number, this.kurang(number));
                } else {
                    System.out.printf("| %d |    %d     |%n", number, this.kurang(number));
                }
            }
        }
        System.out.println("+----+-----------+");
    }

    // mengecek kesamaan antara dua buah matriks
    public boolean isSame(FifteenPuzzle fifteen_puzzle) {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (this.matrix[i][j] != fifteen_puzzle.matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // mengubah nilai elemen suatu matriks
    public void setElement(int row, int col, int number) {
        this.matrix[row][col] = number;
    }

    // menghitung banyaknya number > i dan posisi(number) < posisi(i)
    public int kurang(int number) {
        int count = 0;
        for (int i = 1; i < number; i++) {
            if (this.posisi(number, i)) {
                count++;
            }
        }
        return count;
    }

    // menentukan posisi dua buah angka, true jika posisi(big) < posisi(small)
    public boolean posisi(int big, int small) {
        int decision = 0;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (this.matrix[i][j] == big) {
                    decision = big;
                } else if (this.matrix[i][j] == small) {
                    decision = small;
                }
            }
        }
        if (decision == small) {
            return true;
        } else {
            return false;
        }
    }

    // menghitung nilai sigma Kurang(i) + X
    public int sigmaKurang() {
        int sigma = 0;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                sigma += this.kurang(this.matrix[i][j]);
            }
        }
        if (this.isKosong()) {
            sigma += 1;
        }
        return sigma;
    }

    // menentukan apakah posisi kosong berada pada arsiran
    public boolean isKosong() {
        for (int i = 0; i < this.row; i++) {
            if (i % 2 == 0) {
                if (this.matrix[i][1] == 16 || this.matrix[i][3] == 16) {
                    return true;
                }
            } else {
                if (this.matrix[i][0] == 16 || this.matrix[i][2] == 16) {
                    return true;
                }
            }
        }
        return false;
    }

    // menghitung nilai cost suatu matriks
    public int cost() {
        int number = 0;
        int f = 1;
        int g = 0;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                number++;
                if (number == 16) {
                    break;
                }
                if (this.matrix[i][j] != number) {
                    g += 1;
                }
            }
        }
        int c = f + g;
        return c;
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