package com.zopsmart.assignment9;

import com.zopsmart.assignment9.Controller.StudentController;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        StudentController studentController=new StudentController();
        studentController.testServiceController();
    }
}
