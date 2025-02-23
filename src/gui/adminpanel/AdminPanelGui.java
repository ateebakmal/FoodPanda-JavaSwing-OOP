package gui.adminpanel;

import Classes.Restaurant;
import Classes.User;
import gui.helper.BinaryFileModification;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminPanelGui {
    private JFrame mainFrame;
    private JTable restaurantTable;
    private JTable userTable;

    public AdminPanelGui() {
        // Initialize the main frame
        mainFrame = new JFrame("Administrator Panel");
        mainFrame.getContentPane().removeAll();
        mainFrame.setIconImage(new ImageIcon("src/dummyimages/food-panda-logo.png").getImage());
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        // Set background color
        mainFrame.getContentPane().setBackground(Color.MAGENTA);

        // Create title panel with logo and text
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE); // Set background color to white

        // Load and scale the food panda logo
        JLabel foodPandaIconLabel = new JLabel();
        ImageIcon foodPandaImageIcon = new ImageIcon("src/dummyimages/food-panda-logo.png");

        // Adjust the size of the image to match text size (scaled to 24px height)
        Image scaledImage = foodPandaImageIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        foodPandaIconLabel.setIcon(new ImageIcon(scaledImage));

        JLabel foodPandaTextLabel = new JLabel("foodpanda");
        foodPandaTextLabel.setFont(new Font("Google Sans", Font.BOLD, 24));
        foodPandaTextLabel.setForeground(new Color(178, 28, 92)); // Set text color

        titlePanel.add(foodPandaIconLabel);
        titlePanel.add(foodPandaTextLabel);

        // Add title panel to the top of the frame
        mainFrame.add(titlePanel, BorderLayout.NORTH);

        // Create tables for restaurants and users
        restaurantTable = createStyledTable();
        userTable = createStyledTable();

        // Load data from binary files
        loadRestaurantData();
        loadUserData();

        // Add tables to scroll panes
        JScrollPane restaurantScrollPane = new JScrollPane(restaurantTable);
        JScrollPane userScrollPane = new JScrollPane(userTable);

        // Create remove button
        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(new RemoveButtonHandler());

        // Create view button for selected restaurant
        JButton viewButton = new JButton("View Selected Restaurant");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = restaurantTable.getSelectedRow();
                if (selectedRow != -1) {
                    String restaurantName = (String) restaurantTable.getValueAt(selectedRow, 0);
                    Restaurant restaurant = findRestaurantByName(restaurantName);
                    if (restaurant != null) {
                        showRestaurantMenu(restaurant);
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Restaurant not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Please select a restaurant to view.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Set button color to match foodpanda text color
        removeButton.setBackground(new Color(178, 28, 92));
        removeButton.setForeground(Color.WHITE); // Set text color to white

        viewButton.setBackground(new Color(178, 28, 92));
        viewButton.setForeground(Color.WHITE); // Set text color to white

        JButton viewOrdersButton = new JButton("View Orders History");
        viewOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrdersHistoryFrame(); // Open the orders history frame
            }
        });
        viewOrdersButton.setBackground(new Color(178, 28, 92));
        viewOrdersButton.setForeground(Color.WHITE); // Set text color to white

        // Clear Selection Button to unselect any unwanted selected
        JButton clearSelectionButton = new JButton("Clear Selection");
        // Set button color to match foodpanda text color
        clearSelectionButton.setBackground(new Color(178, 28, 92));
        clearSelectionButton.setForeground(Color.WHITE); // Set text color to white

        clearSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restaurantTable.clearSelection();
                userTable.clearSelection();
            }
        });

        // Add components to the frame
        JPanel centerPanel = new JPanel(new GridLayout(1, 2)); // Center panel for tables

        // Restaurant panel with styled heading
        JPanel restaurantPanel = new JPanel(new BorderLayout());
        JLabel restaurantHeading = new JLabel("Restaurants");
        restaurantHeading.setFont(new Font("Google Sans", Font.BOLD, 20));
        restaurantHeading.setForeground(Color.BLACK);
        restaurantHeading.setOpaque(true);
        restaurantHeading.setBackground(Color.WHITE);
        restaurantHeading.setHorizontalAlignment(SwingConstants.CENTER);

        restaurantPanel.add(restaurantHeading, BorderLayout.NORTH);
        restaurantPanel.add(restaurantScrollPane, BorderLayout.CENTER);

        centerPanel.add(restaurantPanel); // Add restaurant panel to center panel

        // User panel with styled heading
        JPanel userPanel = new JPanel(new BorderLayout());
        JLabel userHeading = new JLabel("Users");

        userHeading.setFont(new Font("Google Sans", Font.BOLD, 20));
        userHeading.setForeground(Color.BLACK);

        userHeading.setOpaque(true);
        userHeading.setBackground(Color.WHITE);

        userHeading.setHorizontalAlignment(SwingConstants.CENTER);

        userPanel.add(userHeading, BorderLayout.NORTH);

        userPanel.add(userScrollPane, BorderLayout.CENTER);

        centerPanel.add(userPanel); // Add user panel to center panel

        mainFrame.add(centerPanel, BorderLayout.CENTER); // Add center panel to frame

        // Add buttons at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(removeButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(clearSelectionButton);
        buttonPanel.add(viewOrdersButton); // Add View Orders History button


        mainFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Show the frame
        mainFrame.setVisible(true);
    }

    private JTable createStyledTable() {
        JTable table = new JTable();
        table.setFont(new Font("Google Sans", Font.PLAIN, 17));
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Google Sans", Font.PLAIN, 18));
        table.getTableHeader().setBackground(Color.DARK_GRAY);
        table.getTableHeader().setForeground(Color.WHITE);
        return table;
    }

    private void loadRestaurantData() {
        String restaurantFile = "src/data/restaurant.bin";
        ArrayList<Restaurant> restaurants = BinaryFileModification.readObjectsFromFile(restaurantFile, Restaurant.class);
        DefaultTableModel model = new DefaultTableModel(new String[]{"Name", "Password"}, 0);
        for (Restaurant restaurant : restaurants) {
            model.addRow(new Object[]{restaurant.getName(), restaurant.getPassword()});
        }
        restaurantTable.setModel(model);
    }

    private void loadUserData() {
        String usersFile = "src/data/users.bin";
        ArrayList<User> users = BinaryFileModification.readObjectsFromFile(usersFile, User.class);
        DefaultTableModel model = new DefaultTableModel(new String[]{"Username", "Password"}, 0);
        for (User user : users) {
            model.addRow(new Object[]{user.getUsername(), user.getPassword()});
        }
        userTable.setModel(model);
    }

    private class RemoveButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRestaurantRow = restaurantTable.getSelectedRow();
            int selectedUserRow = userTable.getSelectedRow();
            if (selectedRestaurantRow != -1) {
                String restaurantName = (String) restaurantTable.getValueAt(selectedRestaurantRow, 0);
                removeRestaurant(restaurantName);
                loadRestaurantData(); // Refresh table after removal
            } else if (selectedUserRow != -1) {
                String username = (String) userTable.getValueAt(selectedUserRow, 0);
                removeUser(username);
                loadUserData(); // Refresh table after removal
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select a restaurant or user to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void removeRestaurant(String name) {
        String restaurantFile = "src/data/restaurant.bin";
        ArrayList<Restaurant> restaurants = BinaryFileModification.readObjectsFromFile(restaurantFile, Restaurant.class);
        restaurants.removeIf(restaurant -> restaurant.getName().equals(name));
        BinaryFileModification.writeObjectsToFile(restaurantFile, restaurants);
        JOptionPane.showMessageDialog(mainFrame, "Restaurant removed successfully.");
    }

    private void removeUser(String username) {
        String usersFile = "src/data/users.bin";
        ArrayList<User> users = BinaryFileModification.readObjectsFromFile(usersFile, User.class);
        users.removeIf(user -> user.getUsername().equals(username));
        BinaryFileModification.writeObjectsToFile(usersFile, users);
        JOptionPane.showMessageDialog(mainFrame, "User removed successfully.");
    }

    private Restaurant findRestaurantByName(String name) {
        String restaurantFile = "src/data/restaurant.bin";
        ArrayList<Restaurant> restaurants = BinaryFileModification.readObjectsFromFile(restaurantFile, Restaurant.class);
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(name)) {
                return restaurant; // Return the matching restaurant
            }
        }
        return null; // Not found
    }

    private void showRestaurantMenu(Restaurant restaurant) {
        new RestaurantMenuFrame(restaurant); // Create and add the new panel for displaying the restaurant's menu
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPanelGui()); // Launch GUI in the Event Dispatch Thread
    }
}
