package Requirment1;

public class Matrix implements Addable<Matrix> {
    protected double[][] numbers;
    protected int m, n;

    public Matrix(int m, int n) {
        // To be sure that m and n are valid numbers
        try {
            if (m < 0 || n < 0)
                throw new MatrixExceptions("Invalid dimensions");
        } catch (MatrixExceptions mat) {
            System.out.println(mat.getMessage());
            System.exit(101);
        }
        this.m = Math.max(m, 0);
        this.n = Math.max(n, 0);
        this.numbers = new double[this.m][this.n];
    }

    public boolean SetNumbers(double[] matrix1D) {
        // Error only on dimensions mismatch
        if (matrix1D.length != this.n * this.m) return false;

        int oneDIterator = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                this.numbers[i][j] = matrix1D[oneDIterator++];
            }
        }
        return true;
    }

    public void print() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(this.numbers[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void Transpose() {

        double[][] transpose = new double[this.n][this.m];
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                transpose[j][i] = this.numbers[i][j];
            }
        }
        this.numbers = transpose;

        // Swap rows and columns
        int tempSwap = this.n;
        this.n = this.m;
        this.m = tempSwap;
    }

    @Override
    public Matrix Add(Matrix secondElement) {
        // Assert that the two matrices are of same dimensions
        try {
            if (!(secondElement.n == this.n && secondElement.m == this.m))
                throw new MatrixExceptions("Matrices dimensions mismatch");
        } catch (MatrixExceptions m) {
            System.out.println(m.getMessage());
            System.exit(99);
        }
        Matrix sumMatrix = new Matrix(this.m, this.n);
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                sumMatrix.numbers[i][j] = secondElement.numbers[i][j] + this.numbers[i][j];
            }
        }
        return sumMatrix;
    }
}
