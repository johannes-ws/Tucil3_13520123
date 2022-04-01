import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BnB {
    private int[][] matrix1;

    public BnB() {
        this.matrix1 = new int[4][4];
    }

    public static void show(int[][] matrix1, int k) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(matrix1[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int KURANG(int[][] matrix1, int a) {
        int min = 0;
        for (int i = 1; i < a; i++) {
            if (POSISI(matrix1, a, i)) {
                min++;
            }
        }
        return min;
    }

    public static boolean POSISI(int[][] matrix1, int a, int b) {
        int now = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix1[i][j] == a) {
                    now = a;
                } else if (matrix1[i][j] == b) {
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

    public static int TOTALKURANG(int[][] matrix1) {
        int min = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                min += KURANG(matrix1, matrix1[i][j]);
            }
        }
        if (KOSONG(matrix1)) {
            min++;
        }
        return min;
    }

    public static boolean KOSONG(int[][] matrix1) {
        boolean found = false;
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                if (matrix1[i][1] == 16 || matrix1[i][3] == 16) {
                    found = true;
                }
            } else {
                if (matrix1[i][0] == 16 || matrix1[i][2] == 16) {
                    found = true;
                }
            }
        }
        return found;
    }

    public static int COST(int[][] matrix1) {
        int min = 0;
        int count = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix1[i][j] != count) {
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

    public static boolean checkUP(int[][] matrix1) {
        boolean found = true;
        for (int i = 0; i < 4; i++) {
            if (matrix1[0][i] == 16) {
                found = false;
            }
        }
        return found;
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

    public static boolean checkDOWN(int[][] matrix1) {
        boolean found = true;
        for (int i = 0; i < 4; i++) {
            if (matrix1[3][i] == 16) {
                found = false;
            }
        }
        return found;
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

    public static boolean checkLEFT(int[][] matrix1) {
        boolean found = true;
        for (int i = 0; i < 4; i++) {
            if (matrix1[i][0] == 16) {
                found = false;
            }
        }
        return found;
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

    public static boolean checkRIGHT(int[][] matrix1) {
        boolean found = true;
        for (int i = 0; i < 4; i++) {
            if (matrix1[i][3] == 16) {
                found = false;
            }
        }
        return found;
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

    public static void main(String[] args) {
		int[][] matrix1 = new int[4][4];
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
                matrix1[x][y] = data;
                y++;
                if (y == 4) {
                    x++;
                    y = 0;
                }
            }
            myReader.close();

            show(matrix1, 1);
            System.out.println(TOTALKURANG(matrix1));
            System.out.println(COST(matrix1));
            System.out.println(checkUP(matrix1));
            System.out.println(checkRIGHT(matrix1));
            System.out.println(checkDOWN(matrix1));
            System.out.println(checkLEFT(matrix1));
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }


    }
}