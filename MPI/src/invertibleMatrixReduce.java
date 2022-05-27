import mpi.MPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class invertibleMatrixReduce {
    private static int[] matrix;

    public static void main(String[] args) throws IOException {
        MPI.Init(args);
        int my_rank = MPI.COMM_WORLD.Rank();
        int root = 0;
        matrix = new int[9];
        int[] sendBuffer = new int[1];
        int[] receiveBuffer = new int[1];

        // TODO: Read the file by the root
        if (my_rank == root) {
            readFile();
            System.out.println("process " + my_rank + " matrix values after reading file " + Arrays.toString(matrix));
        } else {
            System.out.println("process " + my_rank + " matrix values before Bcast " + Arrays.toString(matrix));
        }
//            System.out.println("process " + my_rank + " matrix values before Bcast " + Arrays.toString(matrix));

        // TODO: broadcast the matrix
        MPI.COMM_WORLD.Bcast(matrix, 0, 9, MPI.INT, root);
        System.out.println("process " + my_rank + " matrix values after Bcast " + Arrays.toString(matrix));

        // TODO: calculate the values
        if (my_rank != root) {
            int[][] mat2d = new int[3][3];
            //                         0 1 2               [0][r-1]     [1][]       [2]         [2]         [1]
            //0*3+0 0*3+1 0*3+2      0 0 1 2          mat2d[0][0]*(mat2d[1][1]*mat2d[2][2]-mat2d[2][1]*mat2d[1][2])
            //1*3+0 1*3+1 1*3+2      1 3 4 5        - mat2d[0][1]*(mat2d[1][0]*mat2d[2][2]-mat2d[2][0]*mat2d[1][2])
            //2*3+0 2*3+1 2*3+2      2 6 7 8          mat2d[0][2]*(mat2d[1][0]*mat2d[2][1]-mat2d[2][0]*mat2d[1][1])
            // TODO: Convert the 1d array into 2d array
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    mat2d[i][j] = matrix[(i * 3) + j];
                }
            }
            System.out.println("process " + my_rank + " matrix values after converting 1d into 2d " + Arrays.deepToString(mat2d));
            sendBuffer[0] = 0;

            // TODO: calculate the values
            if (my_rank == 1) {
                sendBuffer[0] = mat2d[0][0] * (mat2d[1][1] * mat2d[2][2] - mat2d[2][1] * mat2d[1][2]);
            } else if (my_rank == 2) {
                sendBuffer[0] = -mat2d[0][1] * (mat2d[1][0] * mat2d[2][2] - mat2d[2][0] * mat2d[1][2]);
            } else {
                sendBuffer[0] = mat2d[0][2] * (mat2d[1][0] * mat2d[2][1] - mat2d[2][0] * mat2d[1][1]);
            }
            System.out.println("process " + my_rank + " my value is " + sendBuffer[0]);
        }
        // TODO: Gather the values
        MPI.COMM_WORLD.Reduce(sendBuffer, 0, receiveBuffer, 0, 1, MPI.INT, MPI.SUM, root);
        if (my_rank == root) {
            System.out.println("I am the root & I have received: " + Arrays.toString(receiveBuffer));

            // TODO: determine whether the matrix is singular or not
            int sum = receiveBuffer[0];
            if (sum == 0) {
                System.out.println("The determinant is " + sum + " so the matrix is singular");
            } else {
                System.out.println("The determinant is " + sum + " so the matrix is not singular");
            }
        }
        MPI.Finalize();
    }

    private static void readFile() throws IOException {
        Scanner scanner = new Scanner(new File("matrix.txt"));
        int i = 0;
        while (scanner.hasNextInt()) {
            matrix[i++] = scanner.nextInt();
        }
    }
}
