package com.example.bloodoor.models;

public class Events {
    private String bankName;
    private String name;
    private String startDate;
    private String endData;
    private String description;
    private String status;
    private String duration;
    private String venue;
    private String pin;

    public Events() {
    }

    public Events(String bankName, String startDate, String endData, String description, String duration, String venue) {
        this.bankName = bankName;
        this.startDate = startDate;
        this.endData = endData;
        this.description = description;
        this.duration = duration;
        this.venue = venue;
    }

    public Events(String bankName, String name, String startDate, String endData, String description, String status, String duration, String venue, String pin) {
        this.bankName = bankName;
        this.name = name;
        this.startDate = startDate;
        this.endData = endData;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.venue = venue;
        this.pin = pin;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndData() {
        return endData;
    }

    public void setEndData(String endData) {
        this.endData = endData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getPin() { return pin; }

    public void setPin(String pin) { this.pin = pin; }
}
