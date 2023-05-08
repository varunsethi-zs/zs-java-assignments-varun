package com.zopsmart.Assignment7.Model;

public class Students {

    public int id;
    public String firstName;
    public String lastName;
    public String mobileNumber;
    public int departmentID;

    public Students(int id, String firstName, String lastName, String mobileNumber, int departmentID) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.departmentID = departmentID;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public int getDepartmentID() {
        return departmentID;
    }
}
