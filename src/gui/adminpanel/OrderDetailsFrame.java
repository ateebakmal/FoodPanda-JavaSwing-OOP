package gui.adminpanel;

import Classes.Order;
import Classes.RestaurantItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class OrderDetailsFrame extends JFrame {

    public OrderDetailsFrame(Order order) {
        setIconImage(new ImageIcon("src/dummyimages/food-panda-logo.png").getImage());
        setTitle("Order Details");
        setSize(400, 300);

        JTextArea textArea = new JTextArea();

        textArea.append("Order Number: " + order.getOrderNumber() + "\n");
        textArea.append("User Name: " + order.getUserName() + "\n");

        for (Map.Entry<String, ArrayList<RestaurantItem>> entry : order.getRestaurantAndOrdersHashmap().entrySet()) {
            textArea.append("Restaurant Name: " + entry.getKey() + "\n");
            textArea.append("Ordered Items: \n");

            for (RestaurantItem item : entry.getValue()) {
                textArea.append("- " + item.getName() + "\n");
            }

            textArea.append("\n");
        }

        textArea.append("Total Amount: $" + order.getTotalPrice());

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setVisible(true);
    }
}
