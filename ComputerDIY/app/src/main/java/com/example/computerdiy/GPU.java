package com.example.computerdiy;

public class GPU {
    private String model;
    private  String brand;
    private String GRAM;
    private int image;
    private  int price;

    public GPU(){

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

    public String getGRAM() {
        return GRAM;
    }

    public void setGRAM(String GRAM) {
        this.GRAM = GRAM;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
