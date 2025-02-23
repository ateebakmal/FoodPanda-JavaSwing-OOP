package Classes;

import gui.helper.Utility;

import javax.swing.text.Keymap;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 202412281234567890L;
    private String orderNumber;
    private String userName;
    private double totalPrice;
    private HashMap<String, ArrayList<RestaurantItem>> restaurantAndOrdersHashmap;

    public Order(String userName, double totalPrice, Cart cart){
        restaurantAndOrdersHashmap = new HashMap<>();
        this.readOrderNumber();
        this.userName = userName;
        this.totalPrice = totalPrice;
        //Extract data from the cart
        this.extractDataFromCart(cart);
    }
    public HashMap<String, ArrayList<RestaurantItem>> getRestaurantAndOrdersHashmap() {
        return restaurantAndOrdersHashmap;
    }

    private void extractDataFromCart(Cart cart){
        for(CartItem item : cart.getItems()){
            String restaurantName = item.getRestaurantName();
            //Check if restaurant already exsts
            if(restaurantAndOrdersHashmap.containsKey(restaurantName)){
                restaurantAndOrdersHashmap.get(restaurantName).add(item.getItem());
            }
            else{
                //Create a new key for that item
                restaurantAndOrdersHashmap.put(restaurantName,new ArrayList<>());
                restaurantAndOrdersHashmap.get(restaurantName).add(item.getItem());
            }
        }
    }

    private void readOrderNumber(){
        String filename = "src/data/orderNumber.txt";
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            this.orderNumber = reader.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }

        //Now update the order number as well
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            int x = Integer.parseInt(orderNumber);
            x++;
            writer.write(String.valueOf(x));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public String getUserName() {
        return userName; // Return the username associated with this order
    }

    public double getTotalPrice() {
        return totalPrice; // Return the total price of this order
    }
    public String getOrderNumber() {
        return orderNumber;
    }

    public void display(){
        System.out.println("Order ID : " + this.orderNumber);
        System.out.println("User name : " + this.userName);
        System.out.println("Total Amount : " + this.totalPrice);

        for(Map.Entry<String, ArrayList<RestaurantItem>> entry : restaurantAndOrdersHashmap.entrySet()){
            System.out.println(entry.getKey());
            Utility.printArrayList(entry.getValue());
        }

    }


}
