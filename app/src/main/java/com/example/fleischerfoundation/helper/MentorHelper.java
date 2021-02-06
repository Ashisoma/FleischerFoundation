package com.example.fleischerfoundation.helper;

public class MentorHelper {
    public String fullName;
    public String student;
    public String profession;
    public String email;
    public String password;
    public String form = "https://docs.google.com/forms/d/e/1FAIpQLSdF2gEGQQo40lQ6pHlvnOpRA5MSeWy6cILNb1pCf2rm7GVozQ/viewform";

    public MentorHelper() {
    }

    public MentorHelper(String fullName, String student, String profession, String email, String password) {
        this.fullName = fullName;
        this.student = student;
        this.profession = profession;
        this.email = email;
        this.password = password;
    }

    public String getForm() {
        return form;
    }
}
