package edu.neu.madcourse.numad21fa_team21dayproject.cookbook;

import android.net.Uri;

import java.util.ArrayList;

public class User {
    private String userName;
    private String picPath;
    private int userLevel;
    private int userStr;
    private int userIntelligent;
    private int userAgility;
    private ArrayList<Habit> createdHabits;
    private ArrayList<Animal> receivedAnimals;

    public User(String userName) {
        this.userName = userName;
        this.picPath = "Default.jpg";
        this.userLevel = 1;
        this.userStr = 0;
        this.userIntelligent = 0;
        this.userAgility = 0;
        this.createdHabits = new ArrayList<Habit>();
        this.receivedAnimals = new ArrayList<Animal>();

        this.createdHabits.add(new Habit("Hello",1,5,1,"morning","day",
                "Have a good day",21, 20, 5, "none", 1));

        //this.createdHabits.add(new Habit("Sport",2,5,1,"morning","day",
        // "Sporttoday",21, 20, 5, "none", 1));

        this.receivedAnimals.add(new Animal("Panda",1,10000));
    }

    public User(){ }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Habit> getCreatedHabits() {
        return createdHabits;
    }

    public void setCreatedHabits(ArrayList<Habit> createdHabits) {
        this.createdHabits = createdHabits;
    }

    public ArrayList<Animal> getReceivedAnimals() {
        return receivedAnimals;
    }

    public void setReceivedAnimals(ArrayList<Animal> receivedAnimals) {
        this.receivedAnimals = receivedAnimals;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }


    public int getUserStr() {
        return userStr;
    }

    public void setUserStr(int userStr) {
        this.userStr = userStr;
    }

    public int getUserIntelligent() {
        return userIntelligent;
    }

    public void setUserIntelligent(int userIntelligent) {
        this.userIntelligent = userIntelligent;
    }

    public int getUserAgility() {
        return userAgility;
    }

    public void setUserAgility(int userAgility) {
        this.userAgility = userAgility;
    }
}
