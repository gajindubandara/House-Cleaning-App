package com.example.house_cleaning_app.model;

public class Job {
    private int jobID;
    private String Location;
    private String date;
    private String noOfRooms;
    private String floorType;
    private String noOfBathrooms;
    private String bFloorType;
    private String price;
    private String user;
//    private byte[] imgR;
//    private byte[] imgBr;
    public String image;

    public Job() {
    }

    public Job(int jobID, String location, String date, String noOfRooms, String floorType, String noOfBathrooms, String bFloorType, String price, String user,String image) {
        this.jobID = jobID;
        Location = location;
        this.date = date;
        this.noOfRooms = noOfRooms;
        this.floorType = floorType;
        this.noOfBathrooms = noOfBathrooms;
        this.bFloorType = bFloorType;
        this.price = price;
        this.user = user;
//        this.imgR = imgR;
//        this.imgBr = imgBr;
        this.image = image;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
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

    public String getFloorType() {
        return floorType;
    }

    public void setFloorType(String floorType) {
        this.floorType = floorType;
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

//    public byte[] getImgR() {
//        return imgR;
//    }
//
//    public void setImgR(byte[] imgR) {
//        this.imgR = imgR;
//    }
//
//    public byte[] getImgBr() {
//        return imgBr;
//    }
//
//    public void setImgBr(byte[] imgBr) {
//        this.imgBr = imgBr;
//    }
}
