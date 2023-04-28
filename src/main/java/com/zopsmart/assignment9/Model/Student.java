package com.zopsmart.assignment9.Model;


/**
 * Student class to write student parameters
 */
public class Student {

    private String firstName;
    private String lastName;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Student( String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
