package Classes;

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> items;

    public Cart(){
        this.items = new ArrayList<>();
    }

    //getter

    public ArrayList<CartItem> getItems() {
        return items;
    }

    //Methods
    public void addItem(RestaurantItem item, String restaurantName){
        CartItem cartItem = new CartItem(item, restaurantName);
        this.items.add(cartItem);
    }

    public void removeItem(int index){
        this.items.remove(index);
    }

    public double getTotal(){
        double sum = 0;
        for(CartItem item : items){
            sum += item.getPrice();
        }

        return sum;
    }
}
