package com.zopsmart.assignment2;
import java.util.*;

public class  MatrixController {
    public int [][]input(){
        Scanner scn=new Scanner(System.in);
        System.out.println("Enter number of rows ");
        int n =scn.nextInt();
        System.out.println("Enter number of columns ");
        int m =scn.nextInt();

        int [][] arr =new int[n][m];
        System.out.println("Enter the values for matrix ");
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                arr[i][j]= scn.nextInt();
            }
        }
        return arr;
    }

}