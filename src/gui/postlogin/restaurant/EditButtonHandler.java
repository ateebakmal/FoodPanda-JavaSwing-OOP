package gui.postlogin.restaurant;

import Classes.Restaurant;
import gui.helper.GuiMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditButtonHandler implements ActionListener {
    Restaurant restaurant;
    RestaurantLoggedIn classThatCalledThisMethod;
    int itemIdToBeEdited;
    JFrame mainFrame = new JFrame("Add A Item");

    JPanel itemNamePanel;
    JLabel itemNameLabel;
    JTextField itemNameField;

    JPanel itemPricePanel;
    JLabel itemPriceLabel;
    JTextField itemPriceField;

    JPanel selectImagePanel;
    JButton selectImageButton;
    JTextArea selectedImageTextArea;

    JPanel addItemPanel;
    JButton addItemButton;

    public EditButtonHandler(Restaurant restaurant, RestaurantLoggedIn classThatCalledThisMethod){

        this.restaurant = restaurant;
        this.classThatCalledThisMethod = classThatCalledThisMethod;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.getContentPane().removeAll();//Removing all existing components, To avoid adding components to existing mainFrame

        //Get input from user and checks if it is correct
        if(!getItemIdInput()){
            return;
        }

        //Setup mainFrame
        mainFrame.setSize(400,300);
        mainFrame.setLayout(new GridLayout(4,1));
        mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        mainFrame.setResizable(false);

        //itemNamePanel - Setup
        itemNamePanel = new JPanel(new FlowLayout());
        itemNamePanel.setSize(400,70);

        //itemNameLabel
        itemNameLabel = GuiMaker.makeJLabel("Item Name : ");

        //itemNameField
        itemNameField = GuiMaker.makeJTextField(250,30);
        itemNameField.setText(restaurant.getMenu().get(itemIdToBeEdited).getName());

        itemNamePanel.add(itemNameLabel);
        itemNamePanel.add(itemNameField);

        //itemPricePanel - Setup
        itemPricePanel = new JPanel(new FlowLayout());
        itemPricePanel.setSize(400,70);

        //itemNameLabel
        itemPriceLabel = GuiMaker.makeJLabel("Item Price : ");

        //itemNameField
        itemPriceField = GuiMaker.makeJTextField(250,30);
        itemPriceField.setText(String.valueOf(restaurant.getMenu().get(itemIdToBeEdited).getPrice()));
        itemPricePanel.add(itemPriceLabel);
        itemPricePanel.add(itemPriceField);

        //selectImagePanel - Setup

        selectImagePanel = new JPanel(new FlowLayout());
        selectImagePanel.setSize(400,30);

        //Select Image button
        selectImageButton = GuiMaker.makeJButton("Change Image");
        selectImageButton.addActionListener(new SelectImageButtonHandler());
        //SelectedImageLabel
        selectedImageTextArea = new JTextArea("No Image Selected");
        selectedImageTextArea.setLineWrap(true); // Enable line wrapping
        selectedImageTextArea.setWrapStyleWord(true); // Wrap at word boundaries
        selectedImageTextArea.setEditable(false); // Make it non-editable

        selectedImageTextArea.setRows(2); // Suggest 3 lines of visible text
        selectedImageTextArea.setColumns(20); // Suggest a width of 20 characters

        JScrollPane scrollPane = new JScrollPane(selectedImageTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

// Set preferred size for proper layout


        selectImagePanel.add(selectImageButton);
        selectImagePanel.add(scrollPane);

        //Add Item Panel - Setup
        addItemPanel = new JPanel(new FlowLayout());
        addItemPanel.setSize(400,100);

        //Add Image Button
        addItemButton = GuiMaker.makeJButton("Edit Item");

        addItemButton.addActionListener(new Handler());
        addItemPanel.add(addItemButton);

        //mainFrame final setup
        mainFrame.add(itemNamePanel);
        mainFrame.add(itemPricePanel);
        mainFrame.add(selectImagePanel);
        mainFrame.add(addItemPanel);
        mainFrame.setVisible(true);

    }

    private boolean getItemIdInput(){
        //returns true, Only when the given input is correct
        //Otherwise does the required operations and return false

        String userInputString = JOptionPane.showInputDialog("Enter Item ID : ");
        int userInputInteger;

        if(userInputString == null){
            return false;
        }
        //Validations
            //#1 : Check if it is empty
        if(userInputString.isEmpty()){
            JOptionPane.showMessageDialog(null,"Please enter an item ID", "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }

            //#2 : Check if it is not an integer value
        try{
            userInputInteger = Integer.parseInt(userInputString);
        }catch (NumberFormatException exception){
            JOptionPane.showMessageDialog(null,"Item ID can be number only", "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }

            //#3 : Check if it doesn't exits

        if(userInputInteger < 0 || userInputInteger > restaurant.getMenu().size() - 1){
            JOptionPane.showMessageDialog(null,userInputString + "doesn't exist", "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }


        //If all the validations are true.
        //Update the variable that we had up
        itemIdToBeEdited = userInputInteger;
        return true;
    }
    private class SelectImageButtonHandler implements ActionListener{
        JFileChooser fileChooser;
        @Override
        public void actionPerformed(ActionEvent e) {
            String dummyImagesDirectory = "C:\\Users\\ateeb\\Desktop\\ateeb\\university\\semesterr-3\\OOP-1\\New folder\\food-delivery-project\\src\\dummyImages";
            String desktopDirectory = "C:\\Users\\ateeb\\Desktop";
            fileChooser = new JFileChooser(dummyImagesDirectory);

            int actionPerformed = fileChooser.showOpenDialog(null);

            if(actionPerformed == JFileChooser.APPROVE_OPTION){

                //If the user has opened a file

                //Extract the filePath that the user has chosen
                String fileChosen = fileChooser.getSelectedFile().getAbsolutePath();

                //We will try to make imageIcon to see if the selected image is actually an image or not.
                //If it is not we show an error dialogue
                ImageIcon imageIcon = new ImageIcon(fileChosen);
                if(imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE){
                    JOptionPane.showMessageDialog(null,"File chosen is not an image","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Else the file is opened and we set the text of selectedImageLabel to the imageSelected FilePath
                selectedImageTextArea.setText(fileChosen);

            }
        }
    }
    private class Handler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String itemName = itemNameField.getText();
            String itemPriceString = itemPriceField.getText();
            Double itemPriceDouble;

            //Show a dialogue box to make sure
            int userInput = JOptionPane.showConfirmDialog(null, "Are you sure to edit the item?");
            if(userInput != 0){
                return;
            }

            //Check if name or price is empty
            if(itemName.isEmpty() || itemPriceString.isEmpty()){
                JOptionPane.showMessageDialog(null,"Name and Price cannot be empty", "Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Check if the price entered is not a valid double value
            try{
                itemPriceDouble = Double.parseDouble(itemPriceString);
            }catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null,"Price can be number only", "Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Check if the price entered is negative
            if(itemPriceDouble <= 0){
                JOptionPane.showMessageDialog(null,"Price cannot be less than equal to zero", "Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Check if the new image is selected or not
            //If no image is selected then keep the current image#
            //OtherWise set the imageIcon to new Image
            if(!selectedImageTextArea.getText().equals("No Image Selected")){
                ImageIcon icon = new ImageIcon(selectedImageTextArea.getText());
                restaurant.getMenu().get(itemIdToBeEdited).setImage(icon);
            }

            //Update the name and price
            restaurant.getMenu().get(itemIdToBeEdited).setName(itemName);
            restaurant.getMenu().get(itemIdToBeEdited).setPrice(itemPriceDouble);
            classThatCalledThisMethod.populate(restaurant.getMenu());
            JOptionPane.showMessageDialog(null,"Item Edited Successfully");
            mainFrame.dispose();
        }
    }
}
