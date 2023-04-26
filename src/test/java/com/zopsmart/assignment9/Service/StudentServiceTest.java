package com.zopsmart.assignment9.Service;

import com.zopsmart.assignment9.Dao.StudentDao;
import com.zopsmart.assignment9.Exceptions.StudentExceptions;
import com.zopsmart.assignment9.Model.Student;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentDao studentDao;
    private StudentService studentService;

    @BeforeEach
    public void setup() {
        studentService = new StudentService(studentDao);

    }

    @Test
    public void testCreateStudent() {
        String firstName = "Varun";
        String lastName = "Sethi";
        Student expectedStudent = new Student(firstName, lastName);
        when(studentDao.createStudent(firstName, lastName)).thenReturn(expectedStudent);
        Student actualStudent = studentService.createStudent(firstName, lastName);
        verify(studentDao).createStudent(firstName, lastName);
        assertEquals(expectedStudent, actualStudent);
    }


    @Test
    public void testStudentSQLExceptionInsertStudent() {
        String firstName = "Varun";
        String lastName = "Sethi";
        doThrow(new StudentExceptions("SQL Exception")).when(studentDao).createStudent(firstName, lastName);
        Exception sqlException = assertThrows(StudentExceptions.class, () -> studentService.createStudent(firstName, lastName));
        String expectedMessage = "SQL Exception";
        String actualMessage = sqlException.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testStudentSQLExceptionGetId() {
        doThrow(new StudentExceptions("SQL Exception")).when(studentDao).getStudent(Mockito.anyInt());
        Exception sqlException = assertThrows(StudentExceptions.class, () -> studentService.getStudent(Mockito.anyInt()));
        String expectedMessage = "SQL Exception";
        String actualMessage = sqlException.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testStudentFirstNameIsEmpty() {
        String firstName = "";
        String lastName = "Sethi";
        Student expectedStudent = null;
        Student actualStudent = studentService.createStudent(firstName, lastName);
        assertEquals(null, actualStudent);
    }

    @Test
    public void testStudentLastNameIsEmpty() {
        String firstName = "Varun";
        String lastName = "";
        Student expectedStudent = null;
        Student actualStudent = studentService.createStudent(firstName, lastName);
        assertNull(actualStudent);
    }


    @Test
    public void testIdIsNegative() {
        int id = -2;
        Exception illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> studentService.getStudent(id));
        String expectedMessage = "Id is less than 0";
        String actualMessage = illegalArgumentException.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testGetStudent() throws SQLException {
        int id = 1;
        Student expectedStudent = new Student("Varun", "Sethi");
        when(studentDao.getStudent(id)).thenReturn(expectedStudent);
        Student actualStudent = studentService.getStudent(id);
        verify(studentDao).getStudent(id);
        assertEquals(expectedStudent, actualStudent);
    }

}
