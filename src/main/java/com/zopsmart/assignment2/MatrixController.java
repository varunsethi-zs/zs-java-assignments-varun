package com.zopsmart.assignment2;
import java.util.*;

public class  MatrixController {
    public int [][]input(){
        Scanner scn=new Scanner(System.in);
        int n =scn.nextInt();
        int m =scn.nextInt();
        int [][] arr =new int[n][m];

        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                arr[i][j]= scn.nextInt();
            }
        }
        return arr;
    }

}