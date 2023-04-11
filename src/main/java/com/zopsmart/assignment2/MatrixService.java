package com.zopsmart.assignment2;

/**
 * MatrixService class for implementation of matrix operations
 */
public class MatrixService {

    /**
     * addMatrix function for addition of 2 matrices
     */
    public int[][] addMatrix(int[][] matrixM1, int[][] matrixM2) {
        int[][] res = new int[matrixM1.length][matrixM1[0].length];

        for (int i = 0; i < matrixM1.length; i++) {
            for (int j = 0; j < matrixM1[0].length; j++) {
                res[i][j] = matrixM1[i][j] + matrixM2[i][j];
            }
        }
        return res;

    }

    /**
     * subtractMatrix function for subtraction of 2 matrices
     */

    public int[][] subtractMatrix(int[][] matrixM1, int[][] matrixM2) {
        int[][] res = new int[matrixM1.length][matrixM1[0].length];

        for (int i = 0; i < matrixM1.length; i++) {
            for (int j = 0; j < matrixM1[0].length; j++) {
                res[i][j] = matrixM1[i][j] - matrixM2[i][j];
            }
        }
        return res;

    }

    /**
     * matrixMultiplication function for multiplication of 2 matrices
     */

    public int[][] matrixMultiplication(int[][] matrixM1, int[][] matrixM2) {
        int[][] res = new int[matrixM1.length][matrixM1[0].length];

        for (int i = 0; i < matrixM1.length; i++) {
            for (int j = 0; j < matrixM1[0].length; j++) {
                res[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    res[i][j] += matrixM1[i][k] * matrixM2[k][j];
                }
            }
        }
        return res;
    }

    /**
     * scalarMultiplication function for multiplication of a matrix with any scalar value
     */

    public int[][] scalarMultiplication(int[][] matrixM1, int k) {
        int[][] resultantMatrix = new int[matrixM1.length][matrixM1[0].length];

        for (int i = 0; i < matrixM1.length; i++) {
            for (int j = 0; j < matrixM1[0].length; j++) {
                resultantMatrix[i][j] = k * matrixM1[i][j];
            }
        }
        return resultantMatrix;
    }

    /**
     * matrixTranspose function for transpose of a matrix
     */

    public int[][] matrixTranspose(int[][] matrixM1) {
        int[][] transpose = new int[matrixM1.length][matrixM1[0].length];

        for (int i = 0; i < matrixM1.length; i++) {
            for (int j = 0; j < matrixM1[0].length; j++) {
                transpose[i][j] = matrixM1[j][i];
            }
        }
        return transpose;
    }

    /**
     * printMatrix function to display the resultant matrix
     */

    public static void printMatrix(int[][] resultantMatrix) {

        for (int i = 0; i < resultantMatrix.length; i++) {

            for (int j = 0; j < resultantMatrix[0].length; j++) {

                System.out.print(resultantMatrix[i][j] + " ");

            }

            System.out.println();

        }

    }
}