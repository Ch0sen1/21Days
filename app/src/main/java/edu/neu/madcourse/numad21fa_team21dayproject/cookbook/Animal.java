package edu.neu.madcourse.numad21fa_team21dayproject.cookbook;

public class Animal {
    public String aName;
    // change to int
    public int picPath;
    public int price;

    public Animal(String aName, int picPath, int price) {
        this.aName = aName;
        this.picPath = picPath;
        this.price = price;
    }

    public Animal(){}

    public String getaName() {
        return aName;
    }

    public int getPicPath() {
        return picPath;
    }

    public int getPrice() {
        return price;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public void setPicPath(int picPath) {
        this.picPath = picPath;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
