package Classes;

import javax.swing.*;
import java.io.Serializable;

public class RestaurantItem implements Serializable {
    private static final long serialVersionUID = 1056871726704231746L;
    private String name;
    private double price;
    private  ImageIcon image;

    public RestaurantItem(String name, double price, ImageIcon image){
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public RestaurantItem(RestaurantItem item){
        this.name = item.name;
        this.price = item.price;
        this.image = item.image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ImageIcon getImage() {
        return image;
    }


    @Override
    public String toString(){
        return "Item Name : " + this.name + " , Item Price : " + this.price;
    }
}
