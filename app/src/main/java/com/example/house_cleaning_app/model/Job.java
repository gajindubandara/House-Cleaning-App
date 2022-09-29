package com.example.house_cleaning_app.model;

public class Job {
    private String jobID;
    private String Location;
    private String date;
    private String noOfRooms;
    private String rFloorType;
    private String noOfBathrooms;
    private String bFloorType;
    private String price;
    private String user;
    private String status;
    private String imageR;
    private String imageBr;
    private String contractor;
    private String RSqFt;
    private String BSqFt;



    public Job() {
    }

    public Job(String jobID, String location, String date, String noOfRooms, String floorType, String noOfBathrooms, String bFloorType, String price, String user, String status, String imageR, String imageBr, String contractor,String RSqFt,String BSqFt) {
        this.jobID = jobID;
        Location = location;
        this.date = date;
        this.noOfRooms = noOfRooms;
        this.rFloorType = floorType;
        this.noOfBathrooms = noOfBathrooms;
        this.bFloorType = bFloorType;
        this.price = price;
        this.user = user;
        this.status = status;
        this.imageR = imageR;
        this.imageBr = imageBr;
        this.contractor=contractor;
        this.BSqFt=BSqFt;
        this.RSqFt=RSqFt;

    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(String noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public String getrFloorType() {
        return rFloorType;
    }

    public void setrFloorType(String rFloorType) {
        this.rFloorType = rFloorType;
    }

    public String getNoOfBathrooms() {
        return noOfBathrooms;
    }

    public void setNoOfBathrooms(String noOfBathrooms) {
        this.noOfBathrooms = noOfBathrooms;
    }

    public String getbFloorType() {
        return bFloorType;
    }

    public void setbFloorType(String bFloorType) {
        this.bFloorType = bFloorType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageR() {
        return imageR;
    }

    public void setImageR(String imageR) {
        this.imageR = imageR;
    }

    public String getImageBr() {
        return imageBr;
    }

    public void setImageBr(String imageBr) {
        this.imageBr = imageBr;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getRSqFt() {
        return RSqFt;
    }

    public void setRSqFt(String RSqFt) {
        this.RSqFt = RSqFt;
    }

    public String getBSqFt() {
        return BSqFt;
    }

    public void setBSqFt(String BSqFt) {
        this.BSqFt = BSqFt;
    }
}