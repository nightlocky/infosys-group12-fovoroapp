package com.example.myapplication;

public class Review {
    private String description;
    private String title;
    private String img;

    private int likecount;
    public Review(){}

    public Review(String description, String title,String img, int likecount) {
        this.description = description;
        this.title=title;
        this.img = img;
        this.likecount = likecount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setImageUrl(String imageUrl) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getLikecount() {
        return likecount;
    }

    public void setLikecount(int likecount) {
        this.likecount = likecount;
    }
}
