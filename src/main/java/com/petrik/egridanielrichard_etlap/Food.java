package com.petrik.egridanielrichard_etlap;

public class Food {
    private int id;
    private String name;
    private String details;
    private int price;
    private String category;

    public Food(int id, String name, String details, int price, String category) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
