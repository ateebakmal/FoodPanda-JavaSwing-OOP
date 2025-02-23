package gui.postlogin.restaurant;

import Classes.Restaurant;
import Classes.RestaurantItem;
import gui.helper.BinaryFileModification;
import gui.helper.GuiMaker;
import gui.helper.Utility;
import gui.postlogin.restaurant.AddButtonHandler;
import gui.postlogin.restaurant.EditButtonHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RestaurantLoggedIn{
    Restaurant restaurant; //Restaurant that opens this window
    JFrame mainFrame = new JFrame();


    JPanel topButtonsPanel;
    JButton addItemButton;
    JButton editItemButton;
    JButton removeItemButton;

    JPanel itemsContainerPanel;

    public RestaurantLoggedIn(Restaurant restaurant){
            this.restaurant = restaurant;
            //Setup the mainFrame
            this.mainFrame.getContentPane().removeAll();
            this.mainFrame.setIconImage(new ImageIcon("src/dummyimages/food-panda-logo.png").getImage());
            this.mainFrame.setSize(800,600);
            this.mainFrame.setTitle("Restaurant Dashboard - " + this.restaurant.getName());
            this.mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.mainFrame.addWindowListener(new WindowListenerHelper());

            //Setting the panel to contain top three buttons
            topButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            topButtonsPanel.setBackground(new Color(197, 250, 245));
            topButtonsPanel.setSize(800,100);
            topButtonsPanel.setOpaque(true);
            topButtonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                //Add Button
            addItemButton = GuiMaker.makeJButton("Add Item");
            addItemButton.setBackground(new Color(178, 28, 92));
            addItemButton.setForeground(Color.WHITE);

            addItemButton.addActionListener(new AddButtonHandler(restaurant,  this));

            //Edit Button
            editItemButton = GuiMaker.makeJButton("Edit Item");
            editItemButton.setBackground(new Color(178, 28, 92));
            editItemButton.setForeground(Color.WHITE);

            editItemButton.addActionListener(new EditButtonHandler(restaurant, this));

                //Remove Button
            removeItemButton = GuiMaker.makeJButton("Remove Item");
            removeItemButton.setBackground(new Color(178, 28, 92));
            removeItemButton.setForeground(Color.WHITE);

            removeItemButton.addActionListener(new RemoveButtonHandler());

            topButtonsPanel.add(addItemButton);
            topButtonsPanel.add(editItemButton);
            topButtonsPanel.add(removeItemButton);

            //Items Container Panel
            //This panel will contain all the items of the restaurant

            this.itemsContainerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15,15));


                //Wrap the itemContainerPanel with ScrollPane to allow scrolling
            JScrollPane scrollPane = new JScrollPane(itemsContainerPanel);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16); //Ensures smooth scrolling
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                //Populate our itemContainerPanel with menu
            ArrayList<RestaurantItem> menu = this.restaurant.getMenu();
            populate(menu);

            //Adding our components to the mainframe
            this.mainFrame.add(topButtonsPanel,BorderLayout.NORTH);

            this.mainFrame.add(scrollPane, BorderLayout.CENTER);
            this.mainFrame.setVisible(true);

    }

    public void populate(ArrayList<RestaurantItem> menu){
        Dimension itemPanelSize = new Dimension(280,200);

        itemsContainerPanel.removeAll(); //Removes all the containers that are already in it

        int itemContainerWidth = 800; //Fixed item Container Panel width
        int itemContainerHeight = 0; //Dynamic Item Container Panel Height

        int itemsPerRow = itemContainerWidth / itemPanelSize.width; // Calculate items per row
        int totalRows = (int) Math.ceil((double) menu.size() / itemsPerRow); // Total number of rows

        itemContainerHeight = totalRows * (itemPanelSize.height + 15); // Total height calculation

        int id = 0; //Id that is assigned to each item for easy identification for user
        for(RestaurantItem item : menu){

            // A new Panel for each item
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setPreferredSize(itemPanelSize);
            itemPanel.setBackground(Color.LIGHT_GRAY);
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


            //Add an image
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            ImageIcon imageIcon = item.getImage();

            if (imageIcon != null) {
                Image scaledImage = imageIcon.getImage().getScaledInstance(280, 145, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                imageLabel.setText("No Image");
            }

            //A new panel for name and price
            JPanel infoPanel = new JPanel(new GridLayout(3 , 1));


            //Id label
            JLabel idLabel = new JLabel("Item ID : " + id++);
            idLabel.setHorizontalAlignment(JLabel.CENTER);
            idLabel.setFont(new Font("Monospace", Font.BOLD, 14));

            //Name Label
            JLabel nameLabel = new JLabel(item.getName());
            nameLabel.setHorizontalAlignment(JLabel.CENTER);
            nameLabel.setFont(new Font("Monospace", Font.BOLD, 14));

            //Price Label
            JLabel priceLabel = new JLabel(Double.toString(item.getPrice()) + " Rs");
            priceLabel.setHorizontalAlignment(JLabel.CENTER);
            priceLabel.setFont(new Font("Monospace", Font.BOLD, 14));

            //Add nameLabel and ageLabel to our infoLabel
            infoPanel.add(idLabel);
            infoPanel.add(nameLabel);
            infoPanel.add(priceLabel);

            //Add our info and image to our item panel
            itemPanel.add(imageLabel, BorderLayout.NORTH);
            itemPanel.add(infoPanel, BorderLayout.SOUTH);


            //Adding this item panel to our itemsContainer
            this.itemsContainerPanel.add(itemPanel);


        }

        itemsContainerPanel.setPreferredSize(new Dimension(itemContainerWidth,itemContainerHeight));
// Refresh the container
        itemsContainerPanel.revalidate();
        itemsContainerPanel.repaint();

        Utility.printArrayList(this.restaurant.getMenu());
        System.out.println("Size + " + this.restaurant.getMenu().size());
    }

    private class RemoveButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            //Shows a dialogue box prompting to enter a item name id
            String userInput = JOptionPane.showInputDialog(null,"Enter Item ID : ");
            int userInputInteger;
            //Validations
                //Check if dialogue box is empty
            if(userInput.isEmpty()){
                JOptionPane.showMessageDialog(null,"Please enter something","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

                //Check if input given is an integer or not

            try{
                userInputInteger = Integer.parseInt(userInput);
            }catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null,"Item ID can be integer only","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

                //Check if the entered id doesnt exist
            if(userInputInteger < 0 || userInputInteger > restaurant.getMenu().size() - 1){
                JOptionPane.showMessageDialog(null,"Item ID doesn't exist","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

            //After passing all the checks. Remove the item
            restaurant.removeItem(userInputInteger);

            populate(restaurant.getMenu());
            JOptionPane.showMessageDialog(null,"Item ID : '" + userInputInteger + "' is removed");

        }
    }

    private class WindowListenerHelper implements WindowListener{

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            //When the window is closed we save the object state to the restaurant file to store the data
            String fileName = "src/data/restaurant.bin";
            ArrayList<Restaurant> restaurantArrayList = BinaryFileModification.readObjectsFromFile(fileName, Restaurant.class);
            System.out.println("Printing file data before reading");
            BinaryFileModification.printData(fileName,Restaurant.class);

                //We write all the objects as it is and update the current object
            try(ObjectOutputStream objWriter = new ObjectOutputStream(new FileOutputStream(fileName))){
                for(Restaurant currentRestaurant : restaurantArrayList) {
                    //Check if the object that we are writing is the object that we are currently using
                    if (currentRestaurant.getName().equals(restaurant.getName())) {
                        objWriter.writeObject(restaurant);
                    } else {
                        objWriter.writeObject(currentRestaurant);
                    }
                }

            }catch (IOException exception){
                System.out.println("Some Error occured while reading this file");
                exception.printStackTrace();
            }
        }


        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

    public static void main(String[] args){
    }

}