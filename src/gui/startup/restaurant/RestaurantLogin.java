package gui.startup.restaurant;

import Classes.Restaurant;
import gui.helper.GuiMaker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import gui.postlogin.restaurant.RestaurantLoggedIn;

public class RestaurantLogin extends JPanel{
        private int labelWidth = 100;
        private int labelHeight  =30;

        private JTextField usernameIdField;
        private JTextField passwordField;



        RestaurantLogin(){
            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));


            JPanel restaurandNameField = new JPanel();
            restaurandNameField.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel restaurantId = GuiMaker.makeJLabel("restaurant id",labelWidth,labelHeight);
            usernameIdField = GuiMaker.makeJTextField(400 - this.labelWidth - 50, labelHeight);
            restaurandNameField.add(restaurantId);
            restaurandNameField.add(usernameIdField);

            JPanel passwordFrame = new JPanel();
            passwordFrame.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel passwordLabel = GuiMaker.makeJLabel("password",labelWidth,labelHeight);
            passwordField = GuiMaker.makeJTextField(400 - this.labelWidth - 50, labelHeight);
            passwordFrame.add(passwordLabel);
            passwordFrame.add(passwordField);

            JButton loginButton = GuiMaker.makeJButton("Log In", labelWidth + 10,labelHeight);
            loginButton.setForeground(new Color(178, 28, 92));
            loginButton.setForeground(Color.WHITE);
            loginButton.addActionListener(new LoginButtonHandler());
            this.add(restaurandNameField);
            this.add(passwordFrame);
            this.add(loginButton);
            this.setVisible(true);
        }

        private class LoginButtonHandler implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                Restaurant restaurant = checkUsernameAndPassword();
                if(restaurant == null){
                    JOptionPane.showMessageDialog(null,"name or password is incorrect","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                System.out.println("Logged In");
                new RestaurantLoggedIn(restaurant);
            }

            private Restaurant checkUsernameAndPassword(){
                String fileName = "src/data/restaurant.bin";

                File file = new File(fileName);

                Restaurant restaurant;
                if(!file.exists()){
                    return null;
                }

                try(ObjectInputStream objReader = new ObjectInputStream(new FileInputStream(file))){

                    while(true){
                        try{
                            restaurant = (Restaurant)objReader.readObject();
                            String username = usernameIdField.getText();
                            String password = passwordField.getText();
                            if(restaurant.getName().equals(username) && restaurant.getPassword().equals(password)){
                                return restaurant;
                            }
                        }catch (EOFException e){
                            break;
                        }
                    }

                }catch (IOException e){
                    System.out.println("Some Error Occured While Reading this file");
                    e.printStackTrace();
                }catch (ClassNotFoundException e){
                    System.out.println("Class read and the class you are casting to are different");
                    e.printStackTrace();
                }

                return null;
            }
        }
}
