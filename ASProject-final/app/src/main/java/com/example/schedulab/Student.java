package com.example.schedulab;

public class Student {
    private String fullName, age, studentId, email;

    public Student(){};

    public Student(String fullName, String age, String studentId, String email) {
        this.fullName = fullName;
        this.age = age;
        this.studentId = studentId;
        this.email = email;
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
}
