import java.util.ArrayList;
import java.util.List;

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

    // mendapatkan path untuk menuju suatu matriks
    public List<String> getPath() {
        return this.path;
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

    // mengecek apakah bisa melakukan UP
    public boolean checkUP() {
        for (int i = 0; i < this.col; i++) {
            if (this.matrix[0][i] == 16) {
                return false;
            }
        }
        return true;
    }

    // mengecek apakah this.matrix merupakan UP dari fifteen_puzzle.matrix
    public boolean isUP(FifteenPuzzle fifteen_puzzle) {
        FifteenPuzzle check = new FifteenPuzzle(fifteen_puzzle);
        check.UP();
        return this.isSame(check);
    }

    // melakukan UP pada this.matrix
    public void UP() {
        if (this.checkUP()) {
            boolean flag = false;
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.col; j++) {
                    if (this.matrix[i][j] == 16) {
                        this.matrix[i][j] = this.matrix[i-1][j];
                        this.matrix[i-1][j] = 16;
                        flag = true;
                    }
                    if (flag) {
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
    }

    // mengecek apakah bisa melakukan DOWN
    public boolean checkDOWN() {
        for (int i = 0; i < this.col; i++) {
            if (this.matrix[3][i] == 16) {
                return false;
            }
        }
        return true;
    }

    // mengecek apakah this.matrix merupakan DOWN dari fifteen_puzzle.matrix
    public boolean isDOWN(FifteenPuzzle fifteen_puzzle) {
        FifteenPuzzle check = new FifteenPuzzle(fifteen_puzzle);
        check.DOWN();
        return this.isSame(check);
    }

    // melakukan DOWN pada this.matrix
    public void DOWN() {
        if (this.checkDOWN()) {
            boolean flag = false;
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.col; j++) {
                    if (this.matrix[i][j] == 16) {
                        this.matrix[i][j] = this.matrix[i+1][j];
                        this.matrix[i+1][j] = 16;
                        flag = true;
                    }
                    if (flag) {
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
    }

    // mengecek apakah bisa melakukan LEFT
    public boolean checkLEFT() {
        for (int i = 0; i < this.row; i++) {
            if (this.matrix[i][0] == 16) {
                return false;
            }
        }
        return true;
    }

    // mengecek apakah this.matrix merupakan LEFT dari fifteen_puzzle.matrix
    public boolean isLEFT(FifteenPuzzle fifteen_puzzle) {
        FifteenPuzzle check = new FifteenPuzzle(fifteen_puzzle);
        check.LEFT();
        return this.isSame(check);
    }

    // melakukan LEFT pada this.matrix
    public void LEFT() {
        if (this.checkLEFT()) {
            boolean flag = false;
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.col; j++) {
                    if (this.matrix[i][j] == 16) {
                        this.matrix[i][j] = this.matrix[i][j-1];
                        this.matrix[i][j-1] = 16;
                        flag = true;
                    }
                    if (flag) {
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
    }

    // mengecek apakah bisa melakukan RIGHT
    public boolean checkRIGHT() {
        for (int i = 0; i < this.row; i++) {
            if (this.matrix[i][3] == 16) {
                return false;
            }
        }
        return true;
    }

    // mengecek apakah this.matrix merupakan RIGHT dari fifteen_puzzle.matrix
    public boolean isRIGHT(FifteenPuzzle fifteen_puzzle) {
        FifteenPuzzle check = new FifteenPuzzle(fifteen_puzzle);
        check.RIGHT();
        return this.isSame(check);
    }

    // melakukan RIGHT pada this.matrix
    public void RIGHT() {
        if (this.checkRIGHT()) {
            boolean flag = false;
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.col; j++) {
                    if (this.matrix[i][j] == 16) {
                        this.matrix[i][j] = this.matrix[i][j+1];
                        this.matrix[i][j+1] = 16;
                        flag = true;
                    }
                    if (flag) {
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
    }

    // menambahkan path untuk menuju suatu matriks
    public void addToPath(List<String> list, FifteenPuzzle fifteen_puzzle) {
        if (this.isUP(fifteen_puzzle)) {
            list.add("UP");
        } else if (this.isDOWN(fifteen_puzzle)) {
            list.add("DOWN");
        } else if (this.isLEFT(fifteen_puzzle)) {
            list.add("LEFT");
        } else if (this.isRIGHT(fifteen_puzzle)) {
            list.add("RIGHT");
        }
    }
}