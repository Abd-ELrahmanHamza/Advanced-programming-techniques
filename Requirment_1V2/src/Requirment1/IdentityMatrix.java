package Requirment1;

import java.lang.reflect.Array;
import java.util.Arrays;

public class IdentityMatrix extends Matrix {

    public IdentityMatrix(int m, int n) {
        super(m, n);

        // Assert that matrix is square
        try {
            if (m != n)
                throw new MatrixExceptions("Identity matrix should be square matrix");
        } catch (MatrixExceptions mat) {
            System.out.println(mat.getMessage());
            System.exit(100);
        }
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                this.numbers[i][j] = (i == j ? 1 : 0);
            }
        }
    }

    @Override
    public boolean SetNumbers(Number[] matrix1D) {
        // deep copy the array
        Number[][] tempMatrix = new Number[this.m][];
        for (int i = 0; i < this.numbers.length; i++) {
            tempMatrix[i] = Arrays.copyOf(this.numbers[i], this.numbers[i].length);
        }

        boolean state = super.SetNumbers(matrix1D);
        if (!state) return false;
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.numbers[i][j].intValue() != (i == j ? 1 : 0)) {
                    this.numbers = tempMatrix;
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void Transpose() {
        return; //Beshoy20
    }
}
