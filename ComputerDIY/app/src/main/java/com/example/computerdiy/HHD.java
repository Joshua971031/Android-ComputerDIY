package com.example.computerdiy;

public class HHD {
    private String model;
    private  String brand;
    private String capacity;
    private int image;
    private  int price;

    public HHD(){

    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String frequency) {
        this.capacity = capacity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
