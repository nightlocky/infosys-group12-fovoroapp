package com.example.myapplication;

//hold all variables
public class Review {
    private String description, title, img, ratedStoreName,key; //key for later updating rate score
    private int noOfLike;
    private boolean hasLiked;


    public Review() {
    }

    public int getNoOfLike() {
        return noOfLike;
    }

    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }

    public boolean isHasLiked() {
        return hasLiked;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Review(String description, String title, String img, String ratedStoreName, int noOfLike) {
        this.description = description;
        this.title = title;
        this.img = img;
        this.noOfLike = noOfLike;
        this.ratedStoreName = ratedStoreName;
        this.hasLiked = false;
    }


    public String getRatedStoreName() {
        return ratedStoreName;
    }

    public String getDescription() {
        return description;
    }

    public void setNoOfLike(int noOfLike) {
        this.noOfLike = noOfLike;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }



}