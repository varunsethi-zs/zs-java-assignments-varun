package com.zopsmart.assignment2;

import java.io.*;
import java.util.Scanner;

public class MatrixMain {

    public static void main(String [] args) {
        MatrixController mc = new MatrixController();
        MatrixService ms = new MatrixService();
        Scanner scn = new Scanner(System.in);

                int [][]A = mc.input();
        int [][]B =mc.input();

int [][]result;
        System.out.println("1 For Addition");
        System.out.println("2 For Substraction");
        System.out.println("3 For Multiplicationsx");
        System.out.println("4 For Scalar Multiplication ");
        System.out.println("5 For Transpose");

        int choice =scn.nextInt();
        switch (choice){
            case 1 -> {
                result = ms.addMatrix(A, B);
                ms.printMatrix(result);
            }
                case 2 -> {
                    result = ms.substractMatrix(A, B);
                    ms.printMatrix(result);
                }
                case 3 -> {
                    result = ms.matrixMultiplication(A, B);
                    ms.printMatrix(result);
                }
                case 4 -> {
                    System.out.println("Enter Scalar Multiplier: ");
                    int k = scn.nextInt();
                    result = ms.scalarMultiplication(A, k);
                    ms.printMatrix(result);
                }
                case 5 -> {
                    result = ms.matrixTranspose(A);
                    ms.printMatrix(result);
                }
                default -> System.out.println("Invalid Choice");
        }
        }
    }