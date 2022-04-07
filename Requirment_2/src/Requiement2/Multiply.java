package Requiement2;

import java.util.Formatter;
import java.util.Scanner;


public class Multiply implements Runnable {
    private int[][] A;
    private int[][] B;
    private int[][] C;

    public Multiply(int[][] a, int[][] b, int[][] c) {
        this.A = a;
        this.B = b;
        this.C = c;
        if (this.A.length != this.C.length || this.B[0].length != this.C[0].length) {
            throw new ArithmeticException("Dimensions mismatch");
        }
        if (A[0].length != B.length) {
            throw new ArithmeticException("Dimensions mismatch");
        }
    }

    @Override
    public void run() {
//        System.out.println("I am thread : " + Thread.currentThread().getName());
        if (Thread.currentThread().getName().equals("1")) {
            for (int ra = 0; ra < A.length; ra++) {
                for (int cb = 0; cb < B[0].length / 2; cb++) {
                    for (int rc = 0; rc < A[ra].length; rc++) {
                        C[ra][cb] += A[ra][rc] * B[rc][cb];
                    }
//                    Formatter formatter = new Formatter();
//                    formatter.format("Thread : %s calculated c[%d][%d] = %d", Thread.currentThread().getName(), ra, cb, C[ra][cb]);
//                    System.out.println(formatter);
                }
            }
            printArraySync();
        } else {
            for (int ra = 0; ra < A.length; ra++) {
                for (int cb = B[0].length / 2; cb < B[0].length; cb++) {
                    for (int rc = 0; rc < A[ra].length; rc++) {
                        C[ra][cb] += A[ra][rc] * B[rc][cb];
                    }
//                    Formatter formatter = new Formatter();
//                    formatter.format("Thread : %s calculated c[%d][%d] = %d", Thread.currentThread().getName(), ra, cb, C[ra][cb]);
//                    System.out.println(formatter);
                }
            }
            printArraySync();
        }
    }

    public synchronized void printArraySync() {
        if (Thread.currentThread().getName().equals("1")) {
            System.out.println("I am thread : " + Thread.currentThread().getName());
            for (int i = 0; i < C.length; i++) {
                for (int j = 0; j < C[0].length / 2; j++) {
                    System.out.print(C[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("I am thread : " + Thread.currentThread().getName());
            for (int i = 0; i < C.length; i++) {
                for (int j = C[0].length / 2; j < C[0].length; j++) {
                    System.out.print(C[i][j] + " ");
                }
                System.out.println();
            }
        }
        System.out.println("====================================");
    }

    public static void printArray(int[][] arr) {
        for (int[] ints : arr) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner cin = new Scanner(System.in);

        int row, column;
        System.out.println("Matrix a:-");
        System.out.print("Row = ");
        row = cin.nextInt();
        System.out.print("Column = ");
        column = cin.nextInt();
        System.out.println("Matrix A: ");
        int[][] a = new int[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                a[i][j] = cin.nextInt();
            }
        }
        System.out.println("====================================");
        System.out.println("Matrix b:-");
        System.out.print("Row = ");
        row = cin.nextInt();
        System.out.print("Column = ");
        column = cin.nextInt();
        System.out.println("Matrix B: ");
        int[][] b = new int[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                b[i][j] = cin.nextInt();
            }
        }
        System.out.println("====================================");

        int[][] c = new int[a.length][b[0].length];
        Multiply mul = new Multiply(a, b, c);
        Thread t1 = new Thread(mul);
        Thread t2 = new Thread(mul);
        t2.setName("2");
        t1.setName("1");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("Matrix C: ");
        printArray(c);
    }
}
