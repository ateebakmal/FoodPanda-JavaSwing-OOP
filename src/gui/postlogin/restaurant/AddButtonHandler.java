package gui.postlogin.restaurant;

import Classes.DrinkItem;
import Classes.PizzaItem;
import Classes.Restaurant;
import Classes.RestaurantItem;
import gui.helper.GuiMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddButtonHandler implements ActionListener {
    Restaurant restaurant;
    RestaurantLoggedIn classThatCalledThisMethod;
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

    JPanel itemTypeRadioButtonsPanel;

    JRadioButton drinkRadioButton;
    JRadioButton pizzaRadioButton;
    JRadioButton noneRadioButton;
    JPanel addItemPanel;
    JButton addItemButton;

    public AddButtonHandler(Restaurant restaurant, RestaurantLoggedIn classThatCalledThisMethod){

        this.restaurant = restaurant;
        this.classThatCalledThisMethod = classThatCalledThisMethod;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.getContentPane().removeAll();//Removing all existing components, To avoid adding components to existing mainFrame

        //Setup mainFrame
        mainFrame.setSize(400,300);
        mainFrame.setLayout(new GridLayout(5,1));
        mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        mainFrame.setResizable(false);

            //itemNamePanel - Setup
        itemNamePanel = new JPanel(new FlowLayout());
        itemNamePanel.setSize(400,70);

                //itemNameLabel
        itemNameLabel = GuiMaker.makeJLabel("Item Name : ");

                //itemNameField
        itemNameField = GuiMaker.makeJTextField(250,30);

        itemNamePanel.add(itemNameLabel);
        itemNamePanel.add(itemNameField);

            //itemPricePanel - Setup
        itemPricePanel = new JPanel(new FlowLayout());
        itemPricePanel.setSize(400,70);

                //itemNameLabel
        itemPriceLabel = GuiMaker.makeJLabel("Item Price : ");

                //itemNameField
        itemPriceField = GuiMaker.makeJTextField(250,30);

        itemPricePanel.add(itemPriceLabel);
        itemPricePanel.add(itemPriceField);

            //selectImagePanel - Setup

        selectImagePanel = new JPanel(new FlowLayout());
        selectImagePanel.setSize(400,30);

                //Select Image button
        selectImageButton = GuiMaker.makeJButton("Select Image");
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


        //Type of item panel
        itemTypeRadioButtonsPanel = new JPanel(new FlowLayout());
        drinkRadioButton = new JRadioButton("Drink");
        pizzaRadioButton = new JRadioButton("Pizza");
        noneRadioButton = new JRadioButton("None");

        ButtonGroup group = new ButtonGroup();
        group.add(drinkRadioButton);
        group.add(pizzaRadioButton);
        group.add(noneRadioButton);

        itemTypeRadioButtonsPanel.add(drinkRadioButton);
        itemTypeRadioButtonsPanel.add(pizzaRadioButton);
        itemTypeRadioButtonsPanel.add(noneRadioButton);

            //Add Item Panel - Setup
        addItemPanel = new JPanel(new FlowLayout());
        addItemPanel.setSize(400,100);

                //Add Image Button
        addItemButton = GuiMaker.makeJButton("Add Item");
        addItemButton.setForeground(new Color(178, 28, 92));
        addItemButton.setBackground(Color.WHITE);
        addItemButton.addActionListener(new AddItemHandler());
        addItemPanel.add(addItemButton);

        //mainFrame final setup
        mainFrame.add(itemNamePanel);
        mainFrame.add(itemPricePanel);
        mainFrame.add(selectImagePanel);
        mainFrame.add(itemTypeRadioButtonsPanel);
        mainFrame.add(addItemPanel);
        mainFrame.setVisible(true);

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

    private class AddItemHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String itemName = itemNameField.getText();
            String itemPriceString = itemPriceField.getText();
            Double itemPriceDouble;
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

            //Check if the image is selected or not
            if(selectedImageTextArea.getText().equals("No Image Selected")){
                JOptionPane.showMessageDialog(null,"Select an Image", "Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            //After passing all the checks, Make object of RestaurantItem
            ImageIcon icon = new ImageIcon(selectedImageTextArea.getText());
            RestaurantItem item;

            //Check if no option is selected
            if(!pizzaRadioButton.isSelected() && !drinkRadioButton.isSelected() && !noneRadioButton.isSelected() ){
                JOptionPane.showMessageDialog(null,"Please select the type of item");
                return;
            }
            //Check if it a pizza drink or none
            if(pizzaRadioButton.isSelected()){
                //Get prices for all the sizes
                item = new PizzaItem(itemName,itemPriceDouble,icon);

            }
            else if(drinkRadioButton.isSelected()){
                item = new DrinkItem(itemName,itemPriceDouble,icon);
            }
            else{
                item = new RestaurantItem(itemName, itemPriceDouble, icon);
            }

            restaurant.addItem(item);

            classThatCalledThisMethod.populate(restaurant.getMenu());
            JOptionPane.showMessageDialog(null,"Item Added Successfully");


            //Reset everything in the window
            itemNameField.setText("");
            itemPriceField.setText("");
            selectedImageTextArea.setText("");
//            mainFrame.dispose();
        }
    }

    private class PizzaSizePriceForm{
        JFrame mainFrame = new JFrame();

        JPanel smallSizePanel;

        JPanel mediumSizePanel;
        JPanel largeSizePanel;
        JPanel extraLargeSizePanel;
        PizzaSizePriceForm(){
            this.mainFrame.getContentPane().removeAll();
            this.mainFrame = new JFrame("Pizza Prices Info");
            this.mainFrame.setSize(400,250);
            this.mainFrame.setLayout(new GridLayout(5,1));

            smallSizePanel = new JPanel(new FlowLayout());
            smallSizePanel.setSize(400,50);
            JLabel smallSizeLabel = GuiMaker.makeJLabel("Small : ",100,30);
            JTextField smallSizeField = GuiMaker.makeJTextField(200,30);

            smallSizePanel.add(smallSizeLabel);
            smallSizePanel.add(smallSizeField);

            mediumSizePanel = new JPanel(new FlowLayout());
            mediumSizePanel.setSize(400,50);
            JLabel mediumSizeLabel = GuiMaker.makeJLabel("Medium : ",100,30);
            JTextField mediumSizeField = GuiMaker.makeJTextField(200,30);

            mediumSizePanel.add(mediumSizeLabel);
            mediumSizePanel.add(mediumSizeField);

            largeSizePanel = new JPanel(new FlowLayout());
            largeSizePanel.setSize(400,50);
            JLabel largeSizeLabel = GuiMaker.makeJLabel("Large : ",100,30);
            JTextField largeSizeField = GuiMaker.makeJTextField(200,30);

            largeSizePanel.add(largeSizeLabel);
            largeSizePanel.add(largeSizeField);

            extraLargeSizePanel = new JPanel(new FlowLayout());
            extraLargeSizePanel.setSize(400,50);
            JLabel extraLargeSizeLabel = GuiMaker.makeJLabel("Extra Large : ",100,30);
            JTextField extraLargeSizeField = GuiMaker.makeJTextField(200,30);

            extraLargeSizePanel.add(extraLargeSizeLabel);
            extraLargeSizePanel.add(extraLargeSizeField);

            JPanel addButtonPricesPanel = new JPanel();
            JButton addButtonPricesButton = GuiMaker.makeJButton("Add");
            addButtonPricesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Check if any if the Fields are empty
                    if(smallSizeField.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Enter Small Size Price");
                        return;
                    }
                    if(mediumSizeField.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Enter Medium Size Price");
                        return;
                    }
                    if(largeSizeField.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Enter Large Size Price");
                        return;
                    }
                    if(extraLargeSizeField.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Enter Extra Large Size Price");
                        return;
                    }
                }
            });

            this.mainFrame.add(smallSizePanel);
            this.mainFrame.add(mediumSizePanel);
            this.mainFrame.add(largeSizePanel);
            this.mainFrame.add(extraLargeSizePanel);

            this.mainFrame.setVisible(true);
            this.mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }
}
