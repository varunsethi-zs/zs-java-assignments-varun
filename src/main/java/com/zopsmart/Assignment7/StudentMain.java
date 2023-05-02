package com.zopsmart.Assignment7;

import com.zopsmart.Assignment7.Controller.StudentController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Main class to call Controller and Controller function
 */
public class StudentMain {

    public static void main(String[] args) {
        StudentController studentController = new StudentController();
        studentController.useDatabase();
    }
}
