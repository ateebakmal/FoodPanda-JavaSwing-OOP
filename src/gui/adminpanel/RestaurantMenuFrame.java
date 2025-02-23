package gui.adminpanel;

import Classes.Restaurant;
import Classes.RestaurantItem;
import gui.helper.BinaryFileModification;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RestaurantMenuFrame extends JFrame { // Extend JFrame
    private JTable menuTable; // Table to display menu items
    private Restaurant restaurant; // Store reference to restaurant

    public RestaurantMenuFrame(Restaurant restaurant) {
        this.getContentPane().removeAll();
        this.restaurant = restaurant; // Initialize restaurant reference

        // Frame setup
        setTitle(restaurant.getName() + " Menu");
        setIconImage(new ImageIcon("src/dummyimages/food-panda-logo.png").getImage());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this frame without exiting the application
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel(restaurant.getName() + " Menu", SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setFont(new Font("Google Sans", Font.PLAIN, 22));
        titleLabel.setBackground(new Color(178, 28, 92));
        titleLabel.setForeground(Color.WHITE);
        getContentPane().add(titleLabel, BorderLayout.NORTH); // Add to frame's content pane

        // Create a table to display menu items
        menuTable = new JTable();
        loadMenuData();
        menuTable.setFont(new Font("Google Sans", Font.PLAIN, 17));
        menuTable.setBackground(Color.WHITE);
        menuTable.setForeground(Color.BLACK);
        menuTable.getTableHeader().setFont(new Font("Google Sans", Font.PLAIN, 18));
        menuTable.getTableHeader().setBackground(Color.DARK_GRAY);
        menuTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(menuTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER); // Add scroll pane to frame's content pane

        // Create a button panel for better layout
        JPanel buttonPanel = new JPanel();

        // Create a remove item button
        JButton removeItemButton = new JButton("Remove Selected Item");
        removeItemButton.setBackground(new Color(178, 28, 92)); // Set background color
        removeItemButton.setForeground(Color.WHITE); // Set text color
        removeItemButton.addActionListener(new RemoveItemButtonHandler());

        buttonPanel.add(removeItemButton); // Add button to panel

        getContentPane().add(buttonPanel, BorderLayout.SOUTH); // Add button panel to frame's content pane

        // Make frame visible
        setVisible(true);
    }

    private void loadMenuData() {
        ArrayList<RestaurantItem> menuItems = restaurant.getMenu(); // Get menu items from restaurant

        // Set up table model
        DefaultTableModel model = new DefaultTableModel(new String[]{"Item Name", "Price"}, 0);

        for (RestaurantItem item : menuItems) {
            model.addRow(new Object[]{item.getName(), item.getPrice()});
        }

        menuTable.setModel(model);
    }

    private class RemoveItemButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                // Remove item based on selected row index
                restaurant.removeItem(selectedRow); // Call method to remove item from restaurant's menu
                updateBinaryFile(); // Update binary file after removal
                loadMenuData(); // Refresh the table after removal
            } else {
                JOptionPane.showMessageDialog(RestaurantMenuFrame.this, "Please select an item to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void updateBinaryFile() {
            // Update binary file with modified restaurant data
            String restaurantFile = "src/data/restaurant.bin";
            ArrayList<Restaurant> restaurants = BinaryFileModification.readObjectsFromFile(restaurantFile, Restaurant.class);

            for (int i = 0; i < restaurants.size(); i++) {
                if (restaurants.get(i).getName().equals(restaurant.getName())) {
                    restaurants.set(i, restaurant); // Update the specific restaurant object
                    break;
                }
            }
            BinaryFileModification.writeObjectsToFile(restaurantFile, restaurants); // Write updated list back to file

            JOptionPane.showMessageDialog(RestaurantMenuFrame.this, "Item removed successfully.");
        }
    }
}
