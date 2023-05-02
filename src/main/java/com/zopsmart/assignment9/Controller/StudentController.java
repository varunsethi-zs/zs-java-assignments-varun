package com.zopsmart.assignment9.Controller;

import com.zopsmart.assignment9.Dao.StudentDao;
import com.zopsmart.assignment9.Service.StudentService;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * StudentController class for calling service layer functions and taking user input
 */


public class StudentController {
    private final StudentDao studentDao = new StudentDao();
    Logger logger = Logger.getLogger(StudentController.class.getName());
    StudentService studentService = new StudentService(studentDao);

    /**
     * testServiceController function for calling service layer functions and taking user input
     */
    public void testServiceController() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter First Name");
        String firstName = scanner.nextLine();
        logger.info("Enter Last Name");
        String lastName = scanner.nextLine();
        studentService.createStudentTable();
        studentService.createStudent(firstName, lastName);
    }

}
