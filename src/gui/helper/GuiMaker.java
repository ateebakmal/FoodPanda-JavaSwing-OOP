package gui.helper;

import javax.swing.*;
import java.awt.*;

public class GuiMaker {
    public static JLabel makeJLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD,14));
        label.setOpaque(true);
        return label;
    }

    public static JLabel makeJLabel(String text, int width, int height){
        Dimension size = new Dimension(width,height);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD,14));
        label.setOpaque(true);
        label.setMinimumSize(size);
        label.setMaximumSize(size);
        label.setPreferredSize(size);
        return label;
    }

    public static JTextField makeJTextField(int width, int height){
        Dimension size = new Dimension(width,height);
        JTextField textField = new JTextField();
        textField.setFont(new Font("Times New Roman", Font.BOLD,14));
        textField.setOpaque(true);
        textField.setMinimumSize(size);
        textField.setMaximumSize(size);
        textField.setPreferredSize(size);
        return textField;
    }

    public static JTextField makeJTextField(){
        JTextField textField = new JTextField();
        textField.setFont(new Font("Times New Roman", Font.BOLD,14));
        textField.setOpaque(true);
        return textField;
    }

    public static JButton makeJButton(String text, int width, int height){
        Dimension size = new Dimension(width, height);
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.BOLD,14));
        button.setOpaque(true);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        button.setPreferredSize(size);
        return button;
    }

    public static JButton makeJButton(String text){
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.BOLD,14));
        button.setOpaque(true);
        return button;
    }
}
