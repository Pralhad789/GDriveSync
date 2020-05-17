package com.example.projectgdsync;

import java.util.Date;

public class MyData {
    int ID;
    String name;
    int price;
    Date date;
    int value;

    public MyData(int ID, String name, int price, Date date, int value) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.date = date;
        this.value = value;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
