package gui.startup.restaurant;

import gui.startup.user.UserLogin;
import gui.startup.user.UserRegister;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class RestaurantGui {
    public JFrame mainFrame;
    public JTabbedPane tabbedPane = new JTabbedPane();

    public RestaurantGui(){
        this.mainFrame = new JFrame("Restaurant Page");
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.setIconImage(new ImageIcon("src/dummyimages/food-panda-logo.png").getImage());
        this.mainFrame.setSize(400,200);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.tabbedPane = new JTabbedPane();


        JPanel userPanel = new RestaurantLogin();
        JPanel userRegister = new RestaurantRegister();

        this.tabbedPane.add("Log In",userPanel);
        this.tabbedPane.add("Register", userRegister);
        this.tabbedPane.setForeground(new Color(178, 28, 92));
        mainFrame.add(tabbedPane);
        this.mainFrame.setVisible(true);

    }
    public static void main(String[] args){
        new RestaurantGui();
    }
}
