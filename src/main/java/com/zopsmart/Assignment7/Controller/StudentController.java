package com.zopsmart.Assignment7.Controller;

import com.zopsmart.Assignment7.Service.StudentService;
import com.zopsmart.assignment5.Controller.LogController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * PostgresController class to take input from user to implement particular service operations
 */
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(LogController.class.getName());

    /**
     * useDatabase function to take input from user to implement particular service operations
     */
    public void useDatabase() {

        StudentService studentService = new StudentService();
        try {
            studentService.createTable();
            logger.info("Table created and updated successfully");
        } catch (Exception e) {
            logger.error("Error in creating and updating table :" + e);
        }
        try {
            studentService.createRecords();
            logger.info("Loaded Data for Students in the table");
        } catch (Exception e) {
            logger.error("Error in inserting records in table :" + e);
        }
        try {
            studentService.extractDataInFile();
            logger.info("Extracted data successfully");
        } catch (Exception e) {
            logger.error("Error in extracting data" + e);
        }
        try {
            studentService.fileCompression();
            logger.info("File compressed successfully");
        } catch (Exception e) {
            logger.error("Error in compressing file:" + e);
        }
    }
}

