package com.araskaplan.closett;

import android.graphics.Bitmap;

public class Clothing {
    String name;
    String pattern;
    String color;
    String type;
    String price;
    String date_of_purchase;
    Bitmap img;
    int id;
    int drawerid;


    public Clothing(String name, String pattern, String color, String type, String price, String date_of_purchase) {
        this.name = name;
        this.pattern = pattern;
        this.color = color;
        this.type = type;
        this.price = price;
        this.date_of_purchase = date_of_purchase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrawerid() {
        return drawerid;
    }

    public void setDrawerid(int drawerid) {
        this.drawerid = drawerid;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getDate_of_purchase() {
        return date_of_purchase;
    }

    public void setDate_of_purchase(String date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }
}
