package com.zopsmart.Assignment7.Controller;

import com.zopsmart.Assignment7.Service.PostgresService;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;


/**
 * PostgresController class to take input from user to implement particular service operations
 */
public class PostgresController {
    private static final Logger logger = Logger.getLogger(PostgresController.class.getName());


    /**
     * useDatabase function to take input from user to implement particular service operations
     */
    public void useDatabase() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        PostgresService postgresService = new PostgresService();
        int choice;

       do{
        logger.info("\n Choose from below options");
        logger.info("1. Enter 1 to create Tables");
        logger.info("2. Enter 2 to enter records in Tables");
        logger.info("3. Enter 3 to extract data in output.txt file ");
        logger.info("4. Enter 4 to compress output.txt file");
        logger.info("5. Enter 5 for Query Explanation");
        logger.info("6. Enter 6 to exit");
        logger.info("Enter Choice");

        choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    postgresService.createTable();
                case 2:
                    postgresService.createRecords();
                case 3:
                    postgresService.extractDataInFile();
                case 4:
                    postgresService.fileCompression();
                case 5:
                    postgresService.explainQuery();
                case 6:
                    break;
                default:
                    logger.info("Invalid Choice");
                    break;
            }
        } while (choice != 6);
    }
}
