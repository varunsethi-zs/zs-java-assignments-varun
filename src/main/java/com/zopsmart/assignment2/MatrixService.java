package com.zopsmart.assignment2;

public class MatrixService {
    public int[][] addMatrix(int [][]A,int [][]B){
        int[][] res = new int[A.length][A[0].length];

        for (int i =0 ; i< A.length; i++){
            for (int j = 0; j< A[0].length; j++){
                res[i][j] = A[i][j] + B [i][j];
            }
        }
        return res;

    }
    public int[][] substractMatrix(int [][]A,int [][]B){
        int[][] res = new int[A.length][A[0].length];

        for (int i =0 ; i< A.length; i++){
            for (int j = 0; j< A[0].length; j++){
                res[i][j] = A[i][j] - B [i][j];
            }
        }
        return res;

    }
    public int[][] matrixMultiplication(int[][] A,int[][] B){
        int[][] res = new int[A.length][A[0].length];

        for(int i=0;i<A.length;i++) {
            for (int j = 0; j < A[0].length; j++) {
                res[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    res[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return res;
    }

    public int[][] scalarMultiplication(int[][] A, int k){
        int[][] res = new int[A.length][A[0].length];

        for (int i =0 ; i< A.length; i++){
            for (int j = 0; j< A[0].length; j++){
                res[i][j] = k * A[i][j];
            }
        }
        return res;
    }

    public int[][] matrixTranspose(int[][] A){
        int[][] transpose = new int[A.length][A[0].length];

        for(int i=0;i< A.length;i++){
            for(int j=0;j<A[0].length;j++){
                transpose[i][j]=A[j][i];
            }
        }
        return transpose;
    }


    public void printMatrix(int [][]A){
        int [][] result = new int[A.length][A[0].length];
        for(int i=0;i< A.length;i++){
            for(int j=0;j<A[0].length;j++){
               System.out.println(result[i][j] +" ");
            }
            System.out.println();
        }
    }
}