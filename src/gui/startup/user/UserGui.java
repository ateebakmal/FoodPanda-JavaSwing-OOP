package gui.startup.user;

import javax.swing.*;
import java.awt.*;

public class UserGui {
    public JFrame mainFrame;
    public JTabbedPane tabbedPane = new JTabbedPane();

    public JFrame loginFrame = new JFrame();
    public JFrame registerFrame = new JFrame();
    public UserGui(){
        this.mainFrame = new JFrame("User Page");
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.setIconImage(new ImageIcon("src/dummyimages/food-panda-logo.png").getImage());
        this.mainFrame.setSize(400,200);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.tabbedPane = new JTabbedPane();
        tabbedPane.setForeground(new Color(178, 28, 92));
        tabbedPane.setBackground(new Color(255, 255, 255));

        JPanel userPanel = new UserLogin();
        JPanel userRegister = new UserRegister();

        this.tabbedPane.add("Log In",userPanel);
        this.tabbedPane.add("Register", userRegister);
        mainFrame.add(tabbedPane);
        this.mainFrame.setVisible(true);

    }


    public static void main(String[] args){
        new UserGui();
    }
}
