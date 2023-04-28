package com.zopsmart.Assignment7.Service;

import com.zopsmart.Assignment7.Connection.StudentConnection;
import com.zopsmart.Assignment7.Model.Students;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

/**
 * PostgresService to implement Operations in Db
 */

public class StudentService {
    StudentConnection studentConnection = new StudentConnection();
    public List<Students> studentsList = new ArrayList<>();
    private static final Logger logger = (Logger) LoggerFactory.getLogger(StudentService.class.getName().getClass());

    private static final String FILEPATH = "/home/raramuri/Java/zs-java-assignments-varun/src/main/java/com/zopsmart/Assignment7/resources/output.txt";

    /**
     * createTable function to create the respective tables
     */
    public void createTable() throws SQLException {
        Connection connection = studentConnection.getconnection();
        Statement createStatement = connection.createStatement();
        Logger.getLogger("In table creation");
        String query = "CREATE TABLE students (id SERIAL PRIMARY KEY, first_name VARCHAR(50), last_name VARCHAR(50), mobile VARCHAR(15))";
        createStatement.executeUpdate(query);
        query = "CREATE TABLE departments (id SERIAL PRIMARY KEY, name VARCHAR(50))";
        createStatement.executeUpdate(query);
        String addForeignKeyQuery = "ALTER TABLE Students ADD FOREIGN KEY (department_id) REFERENCES Departments (id)";
        createStatement.execute(addForeignKeyQuery);
        query = "Insert into departments (name) Values('" + "CS Department" + "')";
        createStatement.executeUpdate(query);
        query = "Insert into departments (name) Values('" + "ee Department" + "')";
        createStatement.executeUpdate(query);
        query = "Insert into departments (name) Values('" + "mech Department" + "')";
        createStatement.executeUpdate(query);

    }

    /**
     * createRecords function to enter records in tables
     */
    public void createRecords() {
        try {
            List<Students> students = createStudents();
            Connection connection = studentConnection.getconnection();
            String query = "INSERT INTO students (first_name, last_name, mobile_number,department_id) VALUES (?, ?, ?, ?)";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            int batchTotal = 0;
            for (Students student : students) {
                prepareStatement.setString(1, student.getFirstName());
                prepareStatement.setString(2, student.getLastName());
                prepareStatement.setString(3, student.getMobileNumber());
                prepareStatement.setInt(4, student.getDepartmentID());
                prepareStatement.addBatch();
                if (batchTotal++ == 10000) {
                    prepareStatement.executeBatch();
                    prepareStatement.clearBatch();
                    batchTotal = 0;
                }
                if (batchTotal > 0)
                    prepareStatement.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * extractDataInFile function to extract the records in a file
     */

    public void extractDataInFile() throws SQLException {

        try (Connection connection = studentConnection.getconnection();) {
            Statement statement = connection.createStatement();
            String select = "SELECT s.id, s.first_name, s.last_name, s.mobile, d.name AS department FROM students s JOIN student_department sd ON s.id = sd.student_id JOIN departments d ON sd.department_id = d.id";
            ResultSet resultSet = statement.executeQuery(select);
            try (FileWriter writer = new FileWriter(FILEPATH)) {
                while (resultSet.next()) {
                    writer.write(resultSet.getInt("id") + ", " + resultSet.getString("first_name") + ", " + resultSet.getString("last_name") + ", " + resultSet.getString("mobile") + ", " + resultSet.getString("department") + "\n");
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * fileCompression function to compress the generated file
     */

    public void fileCompression() {
        try( FileInputStream inputStream = new FileInputStream(FILEPATH);
             GZIPOutputStream outputStream = new GZIPOutputStream(new FileOutputStream(FILEPATH))) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }
    }

    /**
     * createStudents function to enter a million records
     */
    public List<Students> createStudents() {
        for (int i = 1; i <= 1000000; i++) {
            String firstName = "F" + i;
            String lastName = "L" + i;
            String mobileNumber = "94152" + String.format("%05d", i);
            int departmentID = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
            studentsList.add(new Students(i, firstName, lastName, mobileNumber, departmentID));
        }
        return studentsList;
    }
}
