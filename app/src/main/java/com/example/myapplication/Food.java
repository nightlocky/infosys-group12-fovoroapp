package com.example.myapplication;

public class Food {
    private String foodImageName; //imageUploadActivity 的两个key name
    private String img;

    public Food(String foodImageName, String img) {
        this.foodImageName = foodImageName;
        this.img = img;
    }
    public Food() {

    }

    public String getFoodImageName() {
        return foodImageName;
    }

    public String getImg() {
        return img;
    }
}
