package com.zopsmart.Assignment7.Service;

import com.zopsmart.Assignment7.Connection.PostgresConnection;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.*;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

/**
 * PostgresService to implement Operations in Db
 */

public class PostgresService {
    PostgresConnection postgresConnection = new PostgresConnection();


    /**
     * createTable function to create the respective tables
     */
    public void createTable() throws SQLException {
        Connection connection = postgresConnection.connection();
        Statement createStatement = connection.createStatement();

        String query = "CREATE TABLE students (id SERIAL PRIMARY KEY, first_name VARCHAR(50), last_name VARCHAR(50), mobile VARCHAR(15))";
        createStatement.executeUpdate(query);
        query = "CREATE TABLE departments (id INT PRIMARY KEY, name VARCHAR(50))";
        createStatement.executeUpdate(query);
        query = "CREATE TABLE Student_Department" + " ("
                + "student_id INTEGER REFERENCES students" + "(id),"
                + "department_id INTEGER REFERENCES departments" + "(id),"
                + "PRIMARY KEY(student_id, department_id)"
                + ")";
        createStatement.executeUpdate(query);
        Logger.getLogger("Table Created Successfully");
        connection.close();
    }

    /**
     * createRecords function to enter records in tables
     */
    public void createRecords() throws SQLException {
        Connection connection = postgresConnection.connection();
        Statement enterEntries = connection.createStatement();
        for (int i = 1; i <= 1000000; i++) {
            int department_id = (int) (Math.random() * 3) + 1;
            int id;
            id = i;
            String first_name = "fName" + i;
            String last_name = "lName" + i;
            String mobile = "93-93-" + String.format("%06d", i % 10000);

            String insert = "INSERT INTO students (id, first_name, last_name, mobile) VALUES (" + id + ", '" + first_name + "', '" + last_name + "', '" + mobile + "')";
            enterEntries.executeUpdate(insert);
            insert = "INSERT INTO student_department (student_id, department_id) VALUES (" + i + ", " + department_id + ")";
            enterEntries.executeUpdate(insert);
        }
        connection.close();
    }

    /**
     * extractDataInFile function to extract the records in a file
     */

    public void extractDataInFile() throws SQLException {
        Connection connection = postgresConnection.connection();
        try {
            Statement statement = connection.createStatement();
            String select = "SELECT s.id, s.first_name, s.last_name, s.mobile, d.name AS department FROM students s JOIN student_department sd ON s.id = sd.student_id JOIN departments d ON sd.department_id = d.id";
            ResultSet resultSet = statement.executeQuery(select);
            FileWriter writer = new FileWriter("src/main/java/com/zopsmart/assignment5/resources/log.txt");
            while (resultSet.next()) {
                writer.write(resultSet.getInt("id") + ", " + resultSet.getString("first_name") + ", " + resultSet.getString("last_name") + ", " + resultSet.getString("mobile") + ", " + resultSet.getString("department") + "\n");
            }
            writer.close();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }
    }

    /**
     * fileCompression function to compress the generated file
     */

    public void fileCompression() {
        try {

            FileInputStream inputStream = new FileInputStream("src/main/java/com/zopsmart/assignment5/resources/log.txt");
            GZIPOutputStream outputStream = new GZIPOutputStream(new FileOutputStream("src/main/java/com/zopsmart/assignment5/resources/log.txt"));

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.finish();
            outputStream.close();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }
    }

    /**
     * explainQuery function to explain the respective Query
     */
    public void explainQuery() throws SQLException {
        Connection connection = postgresConnection.connection();
        Statement explainStatement = connection.createStatement();
        String sql = "EXPLAIN SELECT s.id, s.first_name, s.last_name, s.mobile, d.name AS department FROM students s JOIN student_department sd ON s.id = sd.student_id JOIN departments d ON sd.department_id = d.id";

        ResultSet resultSet = explainStatement.executeQuery(sql);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("id") + " " + resultSet.getString("select_type") + " " + resultSet.getString("table") + " " + resultSet.getString("type") + " " + resultSet.getString("key") + " " + resultSet.getString("rows") + " " + resultSet.getString("Extra"));
        }
        connection.close();

    }
}
