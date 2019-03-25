package objects;

public class MAINBOARD {
    private String model;
    private String brand;
    private String platform;
    private String size;
    private int image;
    private int price;

    public MAINBOARD(){

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setSize(String size) { this.size = size; }

    public String getSize() { return size; }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
