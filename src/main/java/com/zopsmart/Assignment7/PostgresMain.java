package com.zopsmart.Assignment7;

import com.zopsmart.Assignment7.Controller.PostgresController;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Main class to call Controller and Controller function
 */
public class PostgresMain {

    public static void main(String[] args) {
        try {
            PostgresController postgresController = new PostgresController();
            postgresController.useDatabase();
        } catch (SQLException sqlException) {
            Logger.getLogger(sqlException.getMessage());
        }
    }
}
