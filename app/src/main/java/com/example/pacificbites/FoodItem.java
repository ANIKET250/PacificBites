package com.example.pacificbites;

public class FoodItem {
    private String name;
    private int icon;
    private String price;

    public FoodItem(String name, int icon, String price) {
        this.name = name;
        this.icon = icon;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public String getPrice() {
        return price;
    }
}