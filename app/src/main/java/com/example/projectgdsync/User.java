package com.example.projectgdsync;

public class User {
    private String ID;
    private String Name;
    private String Product;
    private String Qty;
    private String Price;
    private String Date;


    public User(String id, String name, String product, String qty, String price,String date){
        ID = id;
        Name = name;
        Product = product;
        Qty = qty;
        Price = price;
        Date = date;

    }

    public String getID()
    {
        return ID;
    }

    public void setID(String id)
    {
        ID = id;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
