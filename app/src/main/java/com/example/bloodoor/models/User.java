package com.example.bloodoor.models;

import java.io.Serializable;

public class User implements Serializable {

    private String fullName, homeAddress, mobileNo, email, date, bloodgrp, pin_code, gender;

    public User() {

    }

    public User(String email, String mobileno) {
        this.email = email;
        this.mobileNo = mobileno;
    }

    public User(String date, String gender, String mobileNo) {

        this.date = date;
        this.gender = gender;
        this.mobileNo = mobileNo;
    }

    public User(String fullName, String homeAddress, String mobileNo, String email, String pin_code, String date, String bloodgrp, String gender) {
        this.fullName = fullName;
        this.homeAddress = homeAddress;
        this.mobileNo = mobileNo;
        this.email = email;
        this.pin_code = pin_code;
        this.date = date;
        this.bloodgrp = bloodgrp;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getBloodgrp() {
        return bloodgrp;
    }

    public String getPinCode() {
        return pin_code;
    }

    public String getGender() {
        return gender;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBloodgrp(String bloodgrp) {
        this.bloodgrp = bloodgrp;
    }

    public void setPinCode(String pin_code) {
        this.pin_code = pin_code;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}

