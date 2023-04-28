package com.zopsmart.Assignment7.Controller;

import com.zopsmart.Assignment7.Service.StudentService;

import java.io.IOException;
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
    public void useDatabase() throws SQLException, IOException {
        StudentService studentService = new StudentService();
        studentService.createTable();
        logger.info("Table created and updated successfully");
        studentService.createRecords();
        logger.info("Loaded Data for Students in the table");
        studentService.extractDataInFile();
        studentService.fileCompression();
    }
}
