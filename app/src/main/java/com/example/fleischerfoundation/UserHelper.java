package com.example.fleischerfoundation;

public class UserHelper {

    public String fullName;
    public String mentor;
    public String year;
    public String email;
    public String password;

    public UserHelper() {
    }

    public UserHelper(String fullName, String mentor, String year, String email, String password) {
        this.fullName = fullName;
        this.mentor = mentor;
        this.year = year;
        this.email = email;
        this.password = password;
    }
}
