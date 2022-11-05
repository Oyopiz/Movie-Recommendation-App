package com.elitechinc.movierecommendation.Classes;

public class Reviews {
    String reviews,uid;

    public Reviews(String reviews, String uid) {
        this.reviews = reviews;
        this.uid = uid;
    }

    public Reviews() {
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
