package gui.postlogin.user;

import Classes.Cart;
import Classes.CartItem;
import Classes.Order;
import Classes.User;
import gui.helper.BinaryFileModification;
import gui.helper.GuiMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class CheckOutHandler implements ActionListener {
    private User userThatLoggedIn; // To keep track of the user who ordered
    private Cart cart;
    private ArrayList<JCheckBox> checkBoxes;
    private Cart filteredCart; // To filter only the selected item

    private JFrame receiptFrame; // Declare the receiptFrame as a class-level variable

    private boolean addressInfoEntered  = false;

    public CheckOutHandler(Cart cart, ArrayList<JCheckBox> checkBoxes, User userThatLoggedIn) {
        this.userThatLoggedIn = userThatLoggedIn;
        this.cart = cart;
        this.checkBoxes = checkBoxes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Collect selected items from the checkboxes
        filteredCart = new Cart();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                CartItem item = cart.getItems().get(i); // Get the corresponding item in the cart
                filteredCart.addItem(item.getItem(),item.getName());
            }
        }



        // If no items are selected, show a message
        if (cart.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items selected for checkout.");
            return;
        }


        showAddressForm(); // Start by showing the address form


    }

    // Method to calculate the total of selected items
    private double calculateTotal(ArrayList<CartItem> selectedItems) {
        double total = 0;
        for (CartItem item : selectedItems) {
            total += item.getPrice();
        }
        return total;
    }
    // Address Form
    private void showAddressForm() {
        JFrame addressFrame = new JFrame("Enter Delivery Address");
        addressFrame.setSize(400, 180);
        addressFrame.setIconImage(new ImageIcon("src/dummyimages/food-panda-logo.png").getImage());
        addressFrame.setLayout(new GridLayout(3, 2));
        addressFrame.setLocationRelativeTo(null);
        addressFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Display the user's name
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameStringLabel = GuiMaker.makeJLabel("Name : ", 70, 30);
        JTextField userNameField = GuiMaker.makeJTextField(300, 30);
        userNameField.setEditable(false);
        userNameField.setText(userThatLoggedIn.getUsername());
        userNameField.setBackground(Color.WHITE);
        userNameField.setOpaque(true);

        namePanel.add(nameStringLabel);
        namePanel.add(userNameField);
        addressFrame.add(namePanel);

        // Address input field
        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel addressStringLabel = GuiMaker.makeJLabel("Address : ", 70, 30);
        JTextField addressField = GuiMaker.makeJTextField(300, 30);
        addressField.setToolTipText("Enter your delivery address");
        addressPanel.add(addressStringLabel);
        addressPanel.add(addressField);
        addressFrame.add(addressPanel);

        // Confirm button
        JButton confirmButton = GuiMaker.makeJButton("Confirm");
        confirmButton.setBackground(Color.WHITE);
        confirmButton.setForeground(new Color(220, 26, 91));
        confirmButton.addActionListener(e -> {
            String address = addressField.getText();
            if (address.isEmpty()) {
                JOptionPane.showMessageDialog(addressFrame, "Address cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            addressFrame.dispose();

            //Write orders in files to save it
            Order order = new Order(userThatLoggedIn.getUsername(), calculateTotal(cart.getItems()),filteredCart);

            // Write the order object to the file
            String fileName = "src/data/orders.bin";
            BinaryFileModification.writeObjectsToFile(order, fileName);
            showDriverSearchAndReceipt(address);
        });
        addressFrame.add(confirmButton);
        addressFrame.setVisible(true);
    }

    //Driver Search Simulation
    private void showDriverSearchAndReceipt(String address) {
        JFrame searchingFrame = new JFrame("Searching for Driver");
        searchingFrame.setIconImage(new ImageIcon("src/dummyimages/food-panda-logo.png").getImage());
        searchingFrame.setSize(300, 200);
        searchingFrame.setLocationRelativeTo(null);
        searchingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchingFrame.setLayout(new BorderLayout());

        JLabel searchingLabel = new JLabel("Searching for Closest Driver...", JLabel.CENTER);
        searchingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        searchingFrame.add(searchingLabel, BorderLayout.CENTER);

        searchingFrame.setVisible(true);

        // Ensure timer is disposed after execution to avoid lingering events
        Timer searchTimer = new Timer(3000, e -> {
            searchingFrame.dispose();
            showReceiptWithAddressAndDriver(address);
        });
        searchTimer.setRepeats(false); // Ensure it only runs once
        searchTimer.start();
    }


    //Receipt Display

    private void showReceiptWithAddressAndDriver(String address) {
        // If a receipt frame is already visible, dispose of it before creating a new one
        if (receiptFrame != null) {
            receiptFrame.dispose();
        }

        receiptFrame = new JFrame("Checkout Receipt");
        receiptFrame.getContentPane().removeAll(); // Ensure no residual components
        receiptFrame.setIconImage(new ImageIcon("src/dummyimages/food-panda-logo.png").getImage());
        receiptFrame.setSize(400, 500);
        receiptFrame.setLocationRelativeTo(null);
        receiptFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        receiptFrame.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Checkout Receipt", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        receiptFrame.add(headerLabel, BorderLayout.NORTH);

        JPanel receiptPanel = new JPanel();
        receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));

        // Use the filtered cart items
        ArrayList<CartItem> items = filteredCart.getItems();
        double total = 0;
        for (CartItem item : items) {
            JLabel itemLabel = new JLabel(item.getName() + " - Rs." + item.getPrice());
            receiptPanel.add(itemLabel);
            total += item.getPrice();
        }

        JLabel addressLabel = new JLabel("Delivery Address: " + address);
        receiptPanel.add(addressLabel);

        JLabel totalLabel = new JLabel("Total: Rs." + String.format("%.2f", total));
        receiptPanel.add(totalLabel);

        JLabel deliveryTimeLabel = new JLabel();
        JLabel driverLabel = new JLabel("Driver Found: John Doe");
        receiptPanel.add(driverLabel);

        int mins = new Random().nextInt(0, 60);
        deliveryTimeLabel.setText("Estimated Delivery Time: " + mins + " minutes");
        receiptPanel.add(deliveryTimeLabel);

        JScrollPane scrollPane = new JScrollPane(receiptPanel);
        receiptFrame.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = GuiMaker.makeJButton("Close");
        closeButton.setForeground(new Color(220, 26, 91));
        closeButton.setBackground(Color.WHITE);
        closeButton.addActionListener(e -> receiptFrame.dispose());
        receiptFrame.add(closeButton, BorderLayout.SOUTH);

        receiptFrame.setVisible(true);
    }

}
