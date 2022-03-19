package Requirment1;

public class mainClass {
    public static void main(String[] args) {
        String separator = "===============================================";
        testMatrix();
        System.out.println(separator+"\n"+separator+"\n"+separator);
        testIdentity();
    }

    public static void testMatrix() {
        String separator = "===============================================";
        Matrix mat1 = new Matrix(2, 3);

        System.out.println(separator);
        System.out.println("Testing SetNumbers: -");
        System.out.println("1) valid inputs: -");
        double[] initMat1 = {1, 2, 3, 4, 5, 6};
        System.out.println(mat1.SetNumbers(initMat1));
        mat1.print();

        System.out.println("1) invalid inputs: -");
        double[] initMat2 = {1, 2, 3, 4, 5};
        System.out.println(mat1.SetNumbers(initMat2));
        mat1.print();
        System.out.println(separator);

        System.out.println("Testing transpose: -");
        mat1.Transpose();
        mat1.print();
        System.out.println(separator);

        System.out.println("Testing Add: -");
        System.out.println("1) valid inputs: -");
        Matrix mat2 = new Matrix(3, 2);
        double[] initMat3 = {10, 10, 10, 20, 20, 20};
        mat2.SetNumbers(initMat3);
        mat1.Add(mat2).print();
        // Uncomment for testing
        // terminates the program
        // System.out.println("1) invalid inputs: -");
        // Matrix mat3 = new Matrix(2,3);
        // mat3.SetNumbers(initMat3);
        // mat1.Add(mat3).print();
        System.out.println(separator);
    }

    public static void testIdentity() {
        String separator = "===============================================";
        IdentityMatrix mat1 = new IdentityMatrix(3, 3);

        System.out.println(separator);
        System.out.println("Testing SetNumbers: -");
        System.out.println("1) valid inputs: -");
        double[] initMat1 = {1, 0, 0, 0, 1, 0, 0, 0, 1};
        System.out.println(mat1.SetNumbers(initMat1));
        mat1.print();

        System.out.println("1) invalid inputs: -");
        double[] initMat2 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(mat1.SetNumbers(initMat2));
        mat1.print();
        System.out.println(separator);


        System.out.println("Testing transpose: -");
        mat1.Transpose();
        mat1.print();
        System.out.println(separator);

        System.out.println("Testing Add: -");
        IdentityMatrix mat2 = new IdentityMatrix(3, 3);
        mat1.Add(mat2).print();
        System.out.println(separator);
    }
}
