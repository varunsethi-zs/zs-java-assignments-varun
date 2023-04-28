package com.zopsmart.Assignment7.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * PostgresConnection class to connect to db
 */
public class StudentConnection {


    private static final String url = "jdbc:postgresql://localhost:2006/student";
    private static final String username = "Varun";
    private static final String password = "varun";

    /**
     * getconnection function to connect to db
     */
    public Connection getconnection() throws SQLException {

        Connection con = DriverManager.getConnection(
                url, username, password);

        return con;
    }

}
