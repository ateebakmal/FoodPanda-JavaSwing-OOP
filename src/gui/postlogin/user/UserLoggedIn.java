package gui.postlogin.user;

import Classes.*;
import gui.helper.BinaryFileModification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class UserLoggedIn {
    User userThatCalledThisClass;
    JFrame mainFrame = new JFrame();
    JPanel topPanel;
    JPanel restaurantsPanelContainer;
    JPanel cartPanel;
    JPanel totalBillPanel; // Renamed panel for displaying total bill and checkout button
    ArrayList<JCheckBox> checkBoxes;

    Cart cart;
    JButton checkoutButton;
    JLabel totalAmountLabel;


    public UserLoggedIn(ArrayList<Restaurant> restaurants, User user) {
        //User that called this login method to keep track of user who logs in
        this.userThatCalledThisClass = user;
        // Reset every time we open this window
        this.mainFrame.getContentPane().removeAll();

        // Frame setup
        this.mainFrame.setTitle("Food Panda - User View");
        this.mainFrame.setSize(1000, 700);
        this.mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        // Top Panel with title
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        topPanel.setBackground(new Color(220, 26, 91));
        ImageIcon foodPandaICon = new ImageIcon("src/dummyimages/food-panda-logo.png");
        Image img = foodPandaICon.getImage().getScaledInstance(50,50,10);
        JLabel foodPandaIconLabel = new JLabel(new ImageIcon(img));

        JLabel titleLabel = new JLabel("Welcome to Food Panda!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(foodPandaIconLabel);
        topPanel.add(titleLabel);

        // Cart object to keep track of all the items
        cart = new Cart();

        // Cart Panel
        cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout());
        cartPanel.setBackground(Color.LIGHT_GRAY);
        checkoutButton = new JButton("Proceed to Checkout");

        // Total Amount Display
        totalAmountLabel = new JLabel("Total: 0.00", SwingConstants.CENTER);
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Get Total Button
        JButton getTotalButton = new JButton("Get Total");
        getTotalButton.setBackground(new Color(220, 26, 91));
        getTotalButton.setForeground(new Color(255, 255, 255));
        getTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double sum = 0;
                for (JCheckBox checkBox : checkBoxes) {
                    if (checkBox.isSelected()) {
                        sum += Double.parseDouble(parseStringFromCheckBox(checkBox.getText()));
                    }
                }

                // Update the total amount label
                totalAmountLabel.setText("Total: " + sum);
            }
        });


        //Adding a new Button "selectAll" that selects all the items in the cart for user convenience
        JButton selectAllButton = new JButton("Select All");
        selectAllButton.setBackground(new Color(220, 26, 91));
        selectAllButton.setForeground(new Color(255, 255, 255));
        selectAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkBoxes.isEmpty())
                    return;
                for(JCheckBox checkbox : checkBoxes){
                    checkbox.setSelected(true);
                }
            }
        });

        // Total Bill Panel
        totalBillPanel = new JPanel();
        totalBillPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add the total amount label to the panel
        totalBillPanel.add(totalAmountLabel, gbc);

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 1;
        gbc1.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 20);

        totalBillPanel.add(getTotalButton,gbc1);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc1.gridx = 2;
        gbc1.gridy = 0;
        gbc.insets = new Insets(10, 20, 10, 10);
        totalBillPanel.add(selectAllButton,gbc2);

        // Initialize restaurantsPanelContainer
        restaurantsPanelContainer = new JPanel();
        restaurantsPanelContainer.setLayout(new BoxLayout(restaurantsPanelContainer, BoxLayout.Y_AXIS));

        // Populate restaurants panel
        this.populateRestaurants(restaurants);

        // Add components to the frame
        this.mainFrame.add(topPanel, BorderLayout.NORTH);
        this.mainFrame.add(new JScrollPane(restaurantsPanelContainer), BorderLayout.CENTER);
        this.mainFrame.add(cartPanel, BorderLayout.EAST);
        this.mainFrame.add(totalBillPanel, BorderLayout.SOUTH);
        this.mainFrame.setVisible(true);
    }

    public void populateRestaurants(ArrayList<Restaurant> restaurants) {
        if (restaurants == null || restaurants.isEmpty()) {
            System.out.println("No restaurants to display.");
            return;
        }

        // Loop through each restaurant and display their items
        for (Restaurant restaurant : restaurants) {
            JPanel restaurantPanel = new JPanel(new BorderLayout());
            restaurantPanel.setPreferredSize(new Dimension(350, 280));
            restaurantPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel restaurantNameLabel = new JLabel(restaurant.getName());
            restaurantNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
            restaurantPanel.add(restaurantNameLabel, BorderLayout.NORTH);

            JPanel menuPanel = new JPanel();
            menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

            ArrayList<RestaurantItem> menu = restaurant.getMenu();
            for (RestaurantItem item : menu) {
                JPanel itemPanel = createItemPanel(item, restaurant.getName());
                menuPanel.add(itemPanel);
            }

            JScrollPane menuScrollPane = new JScrollPane(menuPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            restaurantPanel.add(menuScrollPane, BorderLayout.CENTER);

            restaurantsPanelContainer.add(restaurantPanel);
        }

        JScrollPane restaurantsScrollPane = new JScrollPane(restaurantsPanelContainer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        restaurantsScrollPane.setPreferredSize(new Dimension(800, 600));
        this.mainFrame.add(restaurantsScrollPane, BorderLayout.CENTER);
    }

    private void addToCart(RestaurantItem item, String restaurantName) {
        // Making a copy of that item instead of passing it as it is, so when the user adds it to its cart,
        cart.addItem(new RestaurantItem(item), restaurantName);
        updateCartPanel();
    }

    private JPanel createItemPanel(RestaurantItem item, String restaurantName) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setPreferredSize(new Dimension(280, 200));
        itemPanel.setBackground(Color.WHITE);
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = item.getImage();
        if (imageIcon != null) {
            Image scaledImage = imageIcon.getImage().getScaledInstance(180, 100, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            ImageIcon placeholderIcon = new ImageIcon("images/placeholder.png");
            Image scaledPlaceholder = placeholderIcon.getImage().getScaledInstance(180, 100, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledPlaceholder));
        }

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        JLabel nameLabel = new JLabel(item.getName(), JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel priceLabel = new JLabel("Price: " + item.getPrice(), JLabel.CENTER);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> addToCart(item, restaurantName));

        itemPanel.add(imageLabel, BorderLayout.NORTH);
        itemPanel.add(infoPanel, BorderLayout.CENTER);
        itemPanel.add(addToCartButton, BorderLayout.SOUTH);

        return itemPanel;
    }

    private void updateCartPanel() {
        cartPanel.removeAll();

        // Panel to hold the cart content
        JPanel cartContent = new JPanel();
        cartContent.setLayout(new BoxLayout(cartContent, BoxLayout.Y_AXIS));

        // Map each item to a checkbox for selection
        checkBoxes = new ArrayList<>();

        ArrayList<CartItem> cartItems = cart.getItems();
        for (CartItem item : cartItems) {
            JPanel cartItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            // Checkbox for selection
            JCheckBox checkBox = new JCheckBox(item.getName() + " - Rs." + item.getPrice());
            checkBoxes.add(checkBox); // Track the checkbox
            checkBox.putClientProperty("item", item); // Link checkbox with the cart item

            // Add checkbox to the panel
            cartItemPanel.add(checkBox);

            // Add the panel to the cart content
            cartContent.add(cartItemPanel);
        }

        // Checkout button
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new CheckOutHandler(cart, checkBoxes,userThatCalledThisClass));

        // Remove button
        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> {
            // Remove selected items
            for (int i = checkBoxes.size() - 1; i >= 0; i--) {
                if (checkBoxes.get(i).isSelected()) {
                    cartItems.remove(i); // Remove item from cart
                    checkBoxes.remove(i); // Remove checkbox from list
                }
            }

            // Update the cart panel
            updateCartPanel();
        });

        JScrollPane cartScrollBar = new JScrollPane(cartContent);
        cartScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cartScrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add cart content and buttons to the panel (Now adding scrollbar - by ateeb)
        cartPanel.add(cartScrollBar, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);

        // Adding all the stuff to our cart panel
        cartPanel.add(buttonPanel, BorderLayout.SOUTH);

        cartPanel.revalidate();
        cartPanel.repaint();
    }


    private String parseStringFromCheckBox(String text) {
        // Extracts the price from the checkbox text
        StringBuilder newString = new StringBuilder();
        boolean firstDot = true;
        for (int i = text.length() - 1; i >= 0; i--) {

            if(text.charAt(i) == '.' && firstDot){
                newString.append(text.charAt(i));
                firstDot = false;
                continue;
            }
            if (!Character.isDigit(text.charAt(i)))
                break;
            newString.append(text.charAt(i));
        }

        return newString.reverse().toString();
    }

    public static void main(String[] args) {
        // Path to the binary file
        String filePath = "src/data/restaurant.bin";

        // Read the file and initialize the restaurants list
        ArrayList<Restaurant> restaurants = BinaryFileModification.readObjectsFromFile(filePath, Restaurant.class);

        // Pass the list to the UserView constructor
        new UserLoggedIn(restaurants, new User("ateeb","a"));
    }
}
