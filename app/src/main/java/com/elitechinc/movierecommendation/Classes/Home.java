package com.elitechinc.movierecommendation.Classes;

public class Home {
    String name, desc, uid, url, ratings, category,imgurl;

    public Home(String name, String desc, String uid, String url, String ratings, String category, String imgurl) {
        this.name = name;
        this.desc = desc;
        this.uid = uid;
        this.url = url;
        this.ratings = ratings;
        this.category = category;
        this.imgurl = imgurl;
    }

    public Home() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
