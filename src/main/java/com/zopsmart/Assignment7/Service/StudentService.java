package com.zopsmart.Assignment7.Service;

import com.zopsmart.Assignment7.Connection.StudentConnection;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

/**
 * PostgresService to implement Operations in Db
 */

public class StudentService {
    StudentConnection studentConnection = new StudentConnection();


    /**
     * createTable function to create the respective tables
     */
    public void createTable() {
        try {
            Connection connection = studentConnection.connection();
            Statement createStatement = connection.createStatement();
            Logger.getLogger("In table creation");
            String query = "CREATE TABLE students (id SERIAL PRIMARY KEY, first_name VARCHAR(50), last_name VARCHAR(50), mobile VARCHAR(15))";
            createStatement.executeUpdate(query);
            Logger.getLogger("Student Table Created Successfully");
            query = "CREATE TABLE departments (id SERIAL PRIMARY KEY, name VARCHAR(50))";
            createStatement.executeUpdate(query);
            Logger.getLogger("Departments Table Created Successfully");
            query = "CREATE TABLE Student_Department" + " ("
                    + "student_id INTEGER REFERENCES students" + "(id),"
                    + "department_id INTEGER REFERENCES departments" + "(id),"
                    + "PRIMARY KEY(student_id, department_id)"
                    + ")";
            createStatement.executeUpdate(query);
            Logger.getLogger("Table Created Successfully");
            query = "Insert into departments (name) Values('" + "CS Department" + "')";
            createStatement.executeUpdate(query);
            query = "Insert into departments (name) Values('" + "ee Department" + "')";
            createStatement.executeUpdate(query);
            query = "Insert into departments (name) Values('" + "mech Department" + "')";
            createStatement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * createRecords function to enter records in tables
     */
    public void createRecords() {
        try {
            Connection connection = studentConnection.connection();
            Statement enterEntries = connection.createStatement();
            for (int i = 1; i <= 1000000; i++) {
                int department_id = (int) (Math.random() * 3) + 1;
                int id;
                id = i;
                String first_name = "fName" + i;
                String last_name = "lName" + i;
                String mobile = "93-93-" + String.format("%06d", i % 10);

                String insert = "INSERT INTO students (id, first_name, last_name, mobile) VALUES (" + id + ", '" + first_name + "', '" + last_name + "', '" + mobile + "')";
                enterEntries.executeUpdate(insert);
                insert = "INSERT INTO student_department (student_id, department_id) VALUES (" + i + ", " + department_id + ")";
                enterEntries.executeUpdate(insert);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * extractDataInFile function to extract the records in a file
     */

    public void extractDataInFile() {

        try (Connection connection = studentConnection.connection();) {
            Statement statement = connection.createStatement();
            String select = "SELECT s.id, s.first_name, s.last_name, s.mobile, d.name AS department FROM students s JOIN student_department sd ON s.id = sd.student_id JOIN departments d ON sd.department_id = d.id";
            ResultSet resultSet = statement.executeQuery(select);
            FileWriter writer = new FileWriter("/home/raramuri/Java/zs-java-assignments-varun/src/main/java/com/zopsmart/Assignment7/resources/output.txt");
            while (resultSet.next()) {
                writer.write(resultSet.getInt("id") + ", " + resultSet.getString("first_name") + ", " + resultSet.getString("last_name") + ", " + resultSet.getString("mobile") + ", " + resultSet.getString("department") + "\n");
            }
            writer.close();
        } catch (IOException | SQLException e) {
//            Logger.getLogger(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * fileCompression function to compress the generated file
     */

    public void fileCompression() {
        try {

            FileInputStream inputStream = new FileInputStream("/home/raramuri/Java/zs-java-assignments-varun/src/main/java/com/zopsmart/Assignment7/resources/output.txt");
            GZIPOutputStream outputStream = new GZIPOutputStream(new FileOutputStream("/home/raramuri/Java/zs-java-assignments-varun/src/main/java/com/zopsmart/Assignment7/resources/output.txt"));

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
    public void explainQuery() {
        try (Connection connection = studentConnection.connection()) {
            Statement explainStatement = connection.createStatement();
            ResultSet resultSet = explainStatement.executeQuery("EXPLAIN SELECT * FROM Student_Department");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
