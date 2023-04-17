package com.zopsmart.assignment2;

import java.util.*;

/**
 * MatrixController class for taking matrixInput from user
 */
public class MatrixController {

    /**
     * matrixInput function for taking input for a matrix
     */
    public int[][] matrixInput() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter number of rows ");
        int rows = scn.nextInt();
        System.out.println("Enter number of columns ");
        int columns = scn.nextInt();

        int[][] arr = new int[rows][columns];
        System.out.println("Enter the values for matrix ");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                arr[i][j] = scn.nextInt();
            }
        }
        return arr;
    }

    /**
     * matricesOperations function to select matrix operation to be performed
     */
    public void matricesOperations() {
        Scanner scn = new Scanner(System.in);
        MatrixService matrixService = new MatrixService();

        int choice;
        do {
            System.out.println("1 For Addition");
            System.out.println("2 For Substraction");
            System.out.println("3 For Multiplicationsx");
            System.out.println("4 For Scalar Multiplication ");
            System.out.println("5 For Transpose");
            System.out.println("6 for exit");

            choice = scn.nextInt();
            switch (choice) {
                case 1 -> {
                    int[][] matrixM1 = matrixInput();
                    int[][] matrixM2 = matrixInput();
                    int[][] resultantMatrix = matrixService.addMatrix(matrixM1, matrixM2);
                    matrixService.printMatrix(resultantMatrix);

                }
                case 2 -> {
                    int[][] matrixM1 = matrixInput();
                    int[][] matrixM2 = matrixInput();
                    int[][] resultantMatrix = matrixService.subtractMatrix(matrixM1, matrixM2);
                    matrixService.printMatrix(resultantMatrix);
                }
                case 3 -> {
                    int[][] matrixM1 = matrixInput();
                    int[][] matrixM2 = matrixInput();
                    int[][] resultantMatrix = matrixService.matrixMultiplication(matrixM1, matrixM2);
                    matrixService.printMatrix(resultantMatrix);
                }
                case 4 -> {
                    int[][] matrixM1 = matrixInput();
                    System.out.println("Enter Scalar Multiplier: ");
                    int k = scn.nextInt();
                    int[][] resultantMatrix = matrixService.scalarMultiplication(matrixM1, k);
                    matrixService.printMatrix(resultantMatrix);
                }
                case 5 -> {
                    int[][] matrixM1 = matrixInput();
                    int[][] resultantMatrix = matrixService.matrixTranspose(matrixM1);
                    matrixService.printMatrix(resultantMatrix);
                }
                default -> {
                    System.out.println("Invalid Option");
                    return;
                }

            }
        } while (choice != 6);
    }
}
