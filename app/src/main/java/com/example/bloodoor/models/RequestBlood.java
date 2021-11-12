package com.example.bloodoor.models;

public class RequestBlood {
    String bloodgrp;
    String patientName;
    String patientNumber;
    String patientEmail;
    String hospitalName;
    String pinCode;
    String requestReason;
    String requestStatus;

    public RequestBlood(){}

    public RequestBlood(String bloodgrp, String patientName, String patientNumber, String patientEmail, String hospitalName, String pinCode, String requestReason, String requestStatus) {
        this.bloodgrp = bloodgrp;
        this.patientName = patientName;
        this.patientNumber = patientNumber;
        this.patientEmail = patientEmail;
        this.hospitalName = hospitalName;
        this.pinCode = pinCode;
        this.requestReason = requestReason;
        this.requestStatus = requestStatus;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }


    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getBloodgrp() {
        return bloodgrp;
    }

    public void setBloodgrp(String bloodgrp) {
        this.bloodgrp = bloodgrp;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }

}

