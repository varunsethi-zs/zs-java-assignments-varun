package com.zopsmart.assignment9.Dao;

import com.zopsmart.assignment9.Exceptions.StudentExceptions;
import com.zopsmart.assignment9.Model.Student;

import java.sql.*;


public class StudentDao {
    public Student student;
    public Connection connection;


    public static Connection connection() throws SQLException {
        String url = "jdbc:postgresql://localhost:2006/student";
        String username = "Varun";
        String password = "varun";


        return DriverManager.getConnection(
                url, username, password);
    }

    /**
     * createStudent function for creating student entry
     */

    public Student createStudent(String firstName, String lastName) {

        String query = "INSERT INTO student (first_name, last_name) VALUES (?, ?);";
        try  {
            connection = connection();
            PreparedStatement prepareStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
          prepareStatement.setString(1, firstName);
            prepareStatement.setString(2, lastName);
            int rowsInserted = prepareStatement.executeUpdate();
            if (rowsInserted == 0) {
                throw new StudentExceptions("Creating student failed, no rows inserted.");
            }
            try (ResultSet generatedKeys = prepareStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Student( firstName, lastName);
                } else {
                    throw new StudentExceptions("Creating student failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new StudentExceptions(e.getMessage());
        }
    }

    /**
     * getStudent Gets student object from id
     */
    public Student getStudent(int id) {
        try {
            connection = connection();
            String query = "Select * from student where id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                student = new Student( resultSet.getString(2), resultSet.getString(3));
            }
            System.out.println(student.getFirstName() + student.getLastName());
            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * createStudentTable for creating student table
     */
    public void createStudentTable() throws SQLException {
        connection = connection();
        String query = "CREATE TABLE student(id  SERIAL PRIMARY KEY,first_name TEXT NOT NULL, last_name TEXT NOT NULL);";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }
}
