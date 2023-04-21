package com.zopsmart.Assignment7.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * PostgresConnection class to connect to db
 */
public class PostgresConnection {

    /**
     * connection function to connect to db
     */
    public Connection connection() throws SQLException {
        String url = "jdbc:postgresql://localhost:2006/zopsmart";
        String username = "Varun";
        String password = "varun";


        // Obtain a connection
        Connection con = DriverManager.getConnection(
                url, username, password);

        con.close();
        return con;
    }

}
