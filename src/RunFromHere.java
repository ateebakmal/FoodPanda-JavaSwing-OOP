import gui.helper.GuiMaker;
import gui.startup.restaurant.RestaurantGui;
import gui.startup.user.UserGui;

import javax.swing.*;
import java.awt.*;
class MainPageGui{
    JFrame frame = new JFrame("Food Panda");

    JPanel titlePanel;

    JPanel buttonsPanel;

    public MainPageGui(){
        frame.setLocationRelativeTo(null);
        frame.setSize(400,200);
        frame.setIconImage(new ImageIcon("src/dummyimages/food-panda-logo.png").getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setMaximumSize(new Dimension(400,200));

        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel foodPandaIconLabel = new JLabel();
        ImageIcon foodPandaImageIcon = new ImageIcon("src/dummyimages/food-panda-logo.png");
        Image scaledImage = foodPandaImageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        foodPandaIconLabel.setIcon(new ImageIcon(scaledImage));

        JLabel foodPandaTextLabel = new JLabel("foodpanda");
        foodPandaTextLabel.setFont(new Font("Google Sans", Font.BOLD, 24));
        foodPandaTextLabel.setForeground(new Color(178, 28, 92));
        foodPandaTextLabel.setSize(100,100);


//        titlePanel.setBackground(Color.ORANGE);
        titlePanel.add(foodPandaIconLabel);
        titlePanel.add(foodPandaTextLabel);


        //Adding buttons for user and restaurant navigation
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton userButton = GuiMaker.makeJButton("User");
        userButton.setBackground(new Color(255, 255, 255));
        userButton.setForeground(new Color(178, 28, 92));
        userButton.addActionListener(e -> new UserGui());

        JButton restaurantButton = GuiMaker.makeJButton("Restaurant");
        restaurantButton.setBackground(new Color(255, 255, 255));
        restaurantButton.setForeground(new Color(178, 28, 92));
        restaurantButton.addActionListener(e  -> new RestaurantGui());
        buttonsPanel.add(userButton);
        buttonsPanel.add(restaurantButton);


        frame.add(titlePanel,BorderLayout.NORTH);
        frame.add(buttonsPanel,BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
public class RunFromHere {
    public static void main(String[] args){
        new MainPageGui();
    }
}
