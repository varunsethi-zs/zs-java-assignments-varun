package com.zopsmart.assignment9.Service;

import com.zopsmart.assignment9.Dao.StudentDao;
import com.zopsmart.assignment9.Model.Student;

import java.sql.SQLException;

/**
 * StudentService Class to implement get and create and functions
 */
public class StudentService {
    private final StudentDao studentDao;


    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void createStudentTable() throws SQLException {
        studentDao.createStudentTable();
    }

    /**
     * Inserts student in the table.
     *
     * @return student object of Student class
     */
    public Student createStudent(String firstName, String lastName) {
        try {
            if (firstName.isEmpty() || lastName.isEmpty()) {
                throw new IllegalArgumentException("First name and last name cannot be empty");
            } else {
                return studentDao.createStudent(firstName, lastName);
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            return null;
        }
    }


    /**
     * getStudent  Gets student object when id is given as input.
     *
     * @return student object
     */
    public Student getStudent(int id) throws SQLException {
        if (id < 0) {
            throw new IllegalArgumentException("Id is less than 0");
        }
        return studentDao.getStudent(id);
    }
}
