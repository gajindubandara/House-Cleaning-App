package com.example.house_cleaning_app.model;

public class Review {
    private String creator;
    private String user;
    private String date;
    private String review;
    private String key;
    private String rating;

    public Review() {
    }

    public Review(String creator,String user, String date, String review,String key,String rating) {
        this.creator=creator;
        this.user = user;
        this.date = date;
        this.review = review;
        this.key=key;
        this.rating=rating;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
