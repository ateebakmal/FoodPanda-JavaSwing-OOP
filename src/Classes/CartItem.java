package Classes;

public class CartItem {
    private RestaurantItem item;
    private String restaurantName;

    public CartItem(RestaurantItem item, String restaurantName){
        this.item = item;
        this.restaurantName = restaurantName;
    }

    //Getters

    public RestaurantItem getItem() {
        return item;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getName(){
        return item.getName();
    }

    public double getPrice(){
        return item.getPrice();
    }

    //setters

    public void setItem(RestaurantItem item) {
        this.item = item;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String toString(){
        return this.item.toString() + "\nRestaurant : " + this.getRestaurantName();
    }
}


