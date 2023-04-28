package com.zopsmart.assignment5.Models;

import java.util.Date;


/**
 * Commit Class to Get details for all the commits
 */
public class Commit {
    public String author;
    public Date commitDate;
    public String message;

    /**
     * Commit Parameterized Constructor
     */

    public Commit(String author, Date date, String message) {
        this.author = author;
        this.commitDate = date;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public String getMessage() {
        return message;
    }
}
