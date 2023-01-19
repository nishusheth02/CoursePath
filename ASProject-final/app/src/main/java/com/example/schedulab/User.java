package com.example.schedulab;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private String fullName, age, studentId, email, type;
    private ArrayList<String> CoursesTaken;

    public User(){}

    public User(String fullName, String age, String studentId, String email, ArrayList<String> CoursesTaken, String type) {
        this.fullName = fullName;
        this.age = age;
        this.studentId = studentId;
        this.email = email;
        this.CoursesTaken = CoursesTaken;
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getCoursesTaken() {
        return CoursesTaken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
