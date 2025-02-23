package gui.startup.user;

import Classes.Restaurant;
import Classes.User;
import gui.adminpanel.AdminPanelGui;
import gui.helper.BinaryFileModification;
import gui.helper.GuiMaker;
import gui.postlogin.user.UserLoggedIn;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserLogin extends JPanel{
    private int labelWidth = 75;
    private int labelHeight  =30;
    JTextField usernameField;
    JTextField passwordField;

    User userThatLoggedIn;

    UserLogin(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));


        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel username = GuiMaker.makeJLabel("username",labelWidth,labelHeight);
        usernameField = GuiMaker.makeJTextField(400 - this.labelWidth - 50, labelHeight);
        usernamePanel.add(username);
        usernamePanel.add(usernameField);

        JPanel passwordFrame = new JPanel();
        passwordFrame.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel passwordLabel = GuiMaker.makeJLabel("password",labelWidth,labelHeight);
        passwordField = GuiMaker.makeJTextField(400 - this.labelWidth - 50, labelHeight);
        passwordFrame.add(passwordLabel);
        passwordFrame.add(passwordField);

        JButton loginButton = GuiMaker.makeJButton("Log In", labelWidth + 10,labelHeight);
        loginButton.setBackground(new Color(255, 255, 255));
        loginButton.setForeground(new Color(178, 28, 92));
        loginButton.addActionListener(new LoginButtonActionHandler());
        this.add(usernamePanel);
        this.add(passwordFrame);
        this.add(loginButton);
        this.setVisible(true);
    }

    private class LoginButtonActionHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            //Check if the username and password entered is on admin
            if(usernameField.getText().equals("Ateeb-Admin") && passwordField.getText().equals("1234")){
                new AdminPanelGui();
                return;
            }

            //Check if username and password is found
            if(!checkUserNameAndPassword()){
                JOptionPane.showMessageDialog(null,"Invalid Username or password");
                return;
            }

            String path = "src/data/restaurant.bin";
            ArrayList<Restaurant> restaurants = BinaryFileModification.readObjectsFromFile(path, Restaurant.class);
            new UserLoggedIn(restaurants,userThatLoggedIn);

        }

        public boolean checkUserNameAndPassword(){
            String filename = "src/data/users.bin";
            ArrayList<User> userArrayList = BinaryFileModification.readObjectsFromFile(filename, User.class);

            for(User user : userArrayList){
                String username = usernameField.getText();
                String password = passwordField.getText();
                if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                    userThatLoggedIn = user;
                    return true;
                }
            }

            return false;
        }
    }

    public static void main(String[] args){
        new UserLogin();
    }
}
