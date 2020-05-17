package com.example.projectgdsync;

public class User {
    private String ID;
    private String Name;


    public User(String id, String name){
        ID = id;
        Name = name;

    }

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        ID = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

//    public String getFavFood() {
//        return FavFood;
//    }
//
//    public void setFavFood(String favFood) {
//        FavFood = favFood;
//    }
}
