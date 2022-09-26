package com.example.house_cleaning_app.model;

public class FloorType {

    private String type;
    private String price;

    public FloorType() {
    }

    public FloorType(String type, String price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
