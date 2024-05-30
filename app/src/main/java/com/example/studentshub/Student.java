package com.example.studentshub;

public class Student {
    private String fullName;
    private String email;
    private String regNumber;
    private String gender;
    private String phoneNumber;

    public Student() {
        // Default constructor required for Firebase
    }

    public Student(String fullName, String email, String regNumber, String gender, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.regNumber = regNumber;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
