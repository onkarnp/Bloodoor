package com.example.bloodoor.models;

import java.io.Serializable;

public class bloodBankHelperClass implements Serializable {

    String name, handlerName, mobileNo, phoneNo, email, address, pinCode;

    public bloodBankHelperClass() {
    }

    public bloodBankHelperClass(String name, String handlerName, String mobileNo, String phoneNo, String email, String address, String pinCode) {
        this.name = name;
        this.handlerName = handlerName;
        this.mobileNo = mobileNo;
        this.phoneNo = phoneNo;
        this.email = email;
        this.address = address;
        this.pinCode = pinCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getbbPinCode() {
        return pinCode;
    }

    public void setbbPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
