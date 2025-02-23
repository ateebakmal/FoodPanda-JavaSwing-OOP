package gui.adminpanel;

import Classes.Order;
import Classes.RestaurantItem;
import gui.helper.BinaryFileModification;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrdersHistoryFrame extends JFrame {
    private JTable ordersTable;
    private JButton detailsButton;

    public OrdersHistoryFrame() {
        setTitle("Orders History");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon("src/dummyimages/food-panda-logo.png").getImage());
        setLayout(new BorderLayout());

        // Create a welcome panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(178, 28, 92)); // Set background color
        JLabel welcomeLabel = new JLabel("Orders History");
        welcomeLabel.setFont(new Font("Google Sans", Font.PLAIN, 22)); // Set font size and style
        welcomeLabel.setForeground(Color.WHITE); // Set text color
        welcomePanel.add(welcomeLabel);

        // Add the welcome panel to the top of the frame
        add(welcomePanel, BorderLayout.NORTH);

        // Create table to display orders
        ordersTable = new JTable();
        ordersTable.setBackground(Color.WHITE);
        ordersTable.setForeground(Color.BLACK);
        ordersTable.setOpaque(true);
        ordersTable.setFont(new Font("Google Sans", Font.PLAIN, 16));

        loadOrderData();

        // Customize header
        customizeHeader();

        JScrollPane scrollPane = new JScrollPane(ordersTable);
        add(scrollPane, BorderLayout.CENTER);

        // Add details button
        detailsButton = new JButton("Show Order Details");
        detailsButton.setBackground(new Color(178, 28, 92));
        detailsButton.setForeground(Color.WHITE);
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOrderDetails();
            }
        });


        //See specific Order Button - By ateeb
        JButton seeSpecificOrderButton = new JButton("Search an order");
        seeSpecificOrderButton.setForeground(Color.WHITE);
        seeSpecificOrderButton.setBackground(new Color(178, 28, 92));

        seeSpecificOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(null, "Enter order ID: ");

                //Check if it is integer or not.
                try{
                   int Number = Integer.parseInt(input);
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Enter an Integer", "Invalid ID", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Order order = getOrderByNumber(input);
                if(order == null){
                    JOptionPane.showMessageDialog(null, "Order ID doesn't exist","Invalid ID", JOptionPane.ERROR_MESSAGE);
                    return;
                }else{
                    new OrderDetailsFrame(order);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(detailsButton);
        buttonPanel.add(seeSpecificOrderButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadOrderData() {
        String orderFile = "src/data/orders.bin";
        ArrayList<Order> orders = BinaryFileModification.readObjectsFromFile(orderFile, Order.class);

        // Set up table model with updated column names (removed Restaurant Name)
        DefaultTableModel model = new DefaultTableModel(new String[]{"Order Number", "Total Amount", "User Name"}, 0);

        for (Order order : orders) {
            // Add row to the model without restaurant name
            model.addRow(new Object[]{order.getOrderNumber(), order.getTotalPrice(), order.getUserName()});
        }

        ordersTable.setModel(model);
    }

    private void customizeHeader() {
        JTableHeader header = ordersTable.getTableHeader();

        // Set font size and style for headers
        header.setFont(new Font("Google Sans", Font.PLAIN, 18)); // Bigger font size for headers
        header.setForeground(Color.WHITE); // Header text color
        header.setBackground(Color.DARK_GRAY); // Header background color
    }

    private void showOrderDetails() {
        int selectedRow = ordersTable.getSelectedRow();

        if (selectedRow >= 0) {
            String orderNumber = (String) ordersTable.getValueAt(selectedRow, 0);
            String userName = (String) ordersTable.getValueAt(selectedRow, 2);

            // Retrieve the corresponding order object from storage or cache based on order number
            Order order = getOrderByNumber(orderNumber);

            if (order != null) {
                new OrderDetailsFrame(order); // Show order details in a new frame
            } else {
                JOptionPane.showMessageDialog(this, "Order not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please select an order.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private Order getOrderByNumber(String orderNumber) {
        String orderFile = "src/data/orders.bin";

        ArrayList<Order> orders = BinaryFileModification.readObjectsFromFile(orderFile, Order.class);

        for (Order order : orders) {
            if (order.getOrderNumber().equals(orderNumber)) {
                return order; // Return the matching order
            }
        }

        return null; // Return null if no matching order is found
    }
}
