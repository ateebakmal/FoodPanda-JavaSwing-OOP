package Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable {

    private String name;
    private String password;
    private ArrayList<RestaurantItem> menu = new ArrayList<>();

    public Restaurant(String name, String password){
        this.name = name;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<RestaurantItem> getMenu() {
        return menu;
    }

    public void addItem(RestaurantItem item){
        this.menu.add(item);
    }

    public void removeItem(int index){
        this.menu.remove(index);
    }
    @Override
    public String toString(){
        System.out.println("Restaurant Name : " + this.name + " , Restaurant Password : " + this.password);

        //print the items
        System.out.println("Menu : ");
        for(RestaurantItem item : this.menu){
            System.out.println(item);
        }

        System.out.println("-------------");
        return "";
    }
}
