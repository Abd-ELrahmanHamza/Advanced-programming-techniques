package Requirment1;

import javax.rmi.ssl.SslRMIClientSocketFactory;

public class mainClass {
    public static void main(String[] args) {
        String separator = "===============================================";
        testMatrix();
        System.out.println(separator + "\n" + separator + "\n" + separator);
        testIdentity();
    }

    public static void testMatrix() {
        String separator = "===============================================";
        Matrix<Double> mat1 = new Matrix<Double>(2, 3);

        System.out.println(separator);
        System.out.println("Testing SetNumbers: -");
        System.out.println("1) valid inputs: -");
        Double[] initMat1 = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
        System.out.println(mat1.SetNumbers(initMat1));
        mat1.print();

        System.out.println("1) invalid inputs: -");
        Double[] initMat2 = {1.0, 2.0, 3.0, 4.0, 5.0};
        System.out.println(mat1.SetNumbers(initMat2));
        mat1.print();
        System.out.println(separator);

        System.out.println("Testing transpose: -");
        mat1.Transpose();
        mat1.print();
        System.out.println(separator);

        System.out.println("Testing Add: -");
        System.out.println("1) valid inputs: -");
        Matrix<Double> mat2 = new Matrix<Double>(3, 2);
        Double[] initMat3 = {10.0, 10.0, 10.0, 20.0, 20.0, 20.0};
        mat2.SetNumbers(initMat3);
        try {
            mat1.Add(mat2).print();
        } catch (MatrixExceptions me) {
            System.out.println(me.getMessage());
        }
        // comment for testing
        System.out.println("1) invalid inputs: -");
        Matrix<Double> mat3 = new Matrix<Double>(2, 3);
        mat3.SetNumbers(initMat3);
        try {
            mat1.Add(mat3).print();
        } catch (MatrixExceptions me) {
            System.out.println(me.getMessage());
        }
        System.out.println(separator);
    }

    public static void testIdentity() {
        String separator = "===============================================";
        IdentityMatrix mat1 = new IdentityMatrix(3, 3);

        System.out.println(separator);
        System.out.println("Testing SetNumbers: -");
        System.out.println("1) valid inputs: -");
        Double[] initMat1 = {1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0};
        System.out.println(mat1.SetNumbers(initMat1));
        mat1.print();

        System.out.println("1) invalid inputs: -");
        Double[] initMat2 = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        System.out.println(mat1.SetNumbers(initMat2));
        mat1.print();
        System.out.println(separator);


        System.out.println("Testing transpose: -");
        mat1.Transpose();
        mat1.print();
        System.out.println(separator);

        System.out.println("Testing Add: -");
        IdentityMatrix mat2 = new IdentityMatrix(3, 3);
        try {
            mat1.Add(mat2).print();
        } catch (MatrixExceptions me) {
            System.out.println(me.getMessage());
        }
        System.out.println(separator);
    }
}
