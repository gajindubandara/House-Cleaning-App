package com.example.house_cleaning_app.model;

public class Job<status> {
    private String Location;
    private String date;
    private String noOfRooms;
    private String floorType;
    private String noOfBathrooms;
    private String bFloorType;
    private String price;
    private String user;
    private String status;
    private String imageR;
    private String imageBr;


    public Job() {
    }

    public Job( String location, String date, String noOfRooms, String floorType, String noOfBathrooms, String bFloorType, String price, String user,String status,String imageR,String imageBr) {
        Location = location;
        this.date = date;
        this.noOfRooms = noOfRooms;
        this.floorType = floorType;
        this.noOfBathrooms = noOfBathrooms;
        this.bFloorType = bFloorType;
        this.price = price;
        this.user = user;
        this.status = status;
        this.imageR = imageR;
        this.imageBr = imageBr;
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
}
