package gui.startup.restaurant;

import Classes.Restaurant;
import Classes.User;
import gui.helper.AppendableObjectOutputStream;
import gui.helper.GuiMaker;
import gui.startup.user.UserRegister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RestaurantRegister extends JPanel{
    private int labelWidth = 120;
    private int labelHeight  =30;
    JPanel restaurantNamePanel;
    JTextField restaurantNameField;
    JTextField passwordField;



    RestaurantRegister() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        restaurantNamePanel = new JPanel();
        restaurantNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel username = GuiMaker.makeJLabel("restaurant name", labelWidth, labelHeight);
        restaurantNameField = GuiMaker.makeJTextField(400 - this.labelWidth - 50, labelHeight);
        restaurantNamePanel.add(username);
        restaurantNamePanel.add(restaurantNameField);

        JPanel passwordFrame = new JPanel();
        passwordFrame.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel passwordLabel = GuiMaker.makeJLabel("password", labelWidth, labelHeight);
        passwordField = GuiMaker.makeJTextField(400 - this.labelWidth - 50, labelHeight);
        passwordFrame.add(passwordLabel);
        passwordFrame.add(passwordField);

        JButton registerButton = GuiMaker.makeJButton("Register", labelWidth + 10, labelHeight);
        registerButton.setForeground(new Color(178, 28, 92));
        registerButton.setBackground(Color.WHITE);
        registerButton.addActionListener(new Handler());

        this.add(restaurantNamePanel);
        this.add(passwordFrame);
        this.add(registerButton);
        this.setVisible(true);

    }

    class Handler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = restaurantNameField.getText();
            String password = passwordField.getText();

            if (username.equals("") || password.equals("")){
                JOptionPane.showMessageDialog(restaurantNamePanel, "Username or Password Cannot Be Empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Check if username already exists
            if(this.restaurantNameExists(username)){
                JOptionPane.showMessageDialog(restaurantNamePanel, "Restaurant Already Exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Write it to bin file
            String fileName = "src/data/restaurant.bin";
            File file = new File(fileName);

            Restaurant restaurant = new Restaurant(username,password);
            try (ObjectOutputStream objWriter = file.exists() && file.length() > 0
                    ? new AppendableObjectOutputStream(new FileOutputStream(file, true))
                    : new ObjectOutputStream(new FileOutputStream(file))){

                objWriter.writeObject(restaurant);
                JOptionPane.showMessageDialog(restaurantNamePanel,"Restaurant Registered Successfully", "Message",JOptionPane.INFORMATION_MESSAGE);

                //Reset username and password fields
                restaurantNameField.setText("");
                passwordField.setText("");

            }catch (IOException exception){
                exception.printStackTrace();
            }

        }

        private boolean restaurantNameExists(String username) {
            String fileName = "src/data/restaurant.bin";
            File file = new File(fileName);

            if(!file.exists() || file.length() == 0) {
                System.out.println("Sikee");
                return false;
            }
            //We will read whole file and check if we find a username with the same name
            try(ObjectInputStream objReader = new ObjectInputStream(new FileInputStream(file))){
                while(true){
                    try{
                        Restaurant restaurant = (Restaurant) objReader.readObject();
                        System.out.println(restaurant.getName());
                        if(restaurant.getName().equals(username))
                            return true;
                    }catch (EOFException e){
                        break;
                    }

                }
            }catch (IOException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            return false;
        }
    }




}
