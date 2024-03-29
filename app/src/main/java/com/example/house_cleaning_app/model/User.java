package com.example.house_cleaning_app.model;

public class User {

    private String NIC;
    private String type;
    private String name;
    private String address;
    private String email;
    private String number;
    private String password;



    public User() {
    }

    public User(String NIC, String type, String name, String address, String email, String number, String password) {
        this.NIC = NIC;
        this.type = type;
        this.name = name;
        this.address = address;
        this.email = email;
        this.number = number;
        this.password = password;
    }

    public String getUserNIC() {
        return NIC;
    }

    public void setUserNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
