package com.zopsmart.Assignment7.Model;

public class Students {

    public int id;
    public String firstname;
    public String lastname;
    public String mobileNumber;
    public int departmentID;

    public Students(int id, String firstname, String lastname, String mobileNumber, int departmentID) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobileNumber = mobileNumber;
        this.departmentID = departmentID;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public int getDepartmentID() {
        return departmentID;
    }
}
