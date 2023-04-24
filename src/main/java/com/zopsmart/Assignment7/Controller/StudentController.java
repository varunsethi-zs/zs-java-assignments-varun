package com.zopsmart.Assignment7.Controller;

import com.zopsmart.Assignment7.Service.StudentService;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;


/**
 * PostgresController class to take input from user to implement particular service operations
 */
public class StudentController {
    private static final Logger logger = Logger.getLogger(StudentController.class.getName());


    /**
     * useDatabase function to take input from user to implement particular service operations
     */
    public void useDatabase() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        StudentService studentService = new StudentService();
        studentService.createTable();
        studentService.createRecords();
        studentService.extractDataInFile();
        studentService.fileCompression();
        studentService.explainQuery();


    }
}
