package gui.startup.user;

import Classes.User;
import gui.helper.GuiMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class UserRegister extends JPanel{
    private int labelWidth = 75;
    private int labelHeight  =30;
    JPanel usernamePanel;
    JTextField usernameField;
    JTextField passwordField;



    UserRegister(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));


        usernamePanel = new JPanel();
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

        JButton registerButton = GuiMaker.makeJButton("Register", labelWidth + 10,labelHeight);
        registerButton.setForeground(new Color(178, 28, 92));
        registerButton.setBackground(Color.WHITE);
        registerButton.addActionListener(new Handler());
        this.add(usernamePanel);
        this.add(passwordFrame);
        this.add(registerButton);
        this.setVisible(true);


        }

    class Handler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //Make a new User Object
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.equals("") || password.equals("")){
                JOptionPane.showMessageDialog(usernamePanel, "Username or Password Cannot Be Empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Check if username already exists
            if(this.checkUsernameExists(username)){
                JOptionPane.showMessageDialog(usernamePanel, "Username Already Exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = new User(username,password);

            //Write it to bin file
            String fileName = "src/data/users.bin";
            File file = new File(fileName);

            try (ObjectOutputStream objWriter = file.exists() && file.length() > 0
                    ? new AppendableObjectOutputStream(new FileOutputStream(file, true))
                    : new ObjectOutputStream(new FileOutputStream(file))){

                objWriter.writeObject(user);
                JOptionPane.showMessageDialog(usernamePanel,"User Registered Successfully", "Message",JOptionPane.INFORMATION_MESSAGE);

                //Reset username and password fields
                usernameField.setText("");
                passwordField.setText("");

            }catch (IOException exception){
                exception.printStackTrace();
            }
        }

        private boolean checkUsernameExists(String username) {
            String fileName = "src/data/users.bin";
            File file = new File(fileName);

            if(!file.exists() || file.length() == 0)
                return false;

            //We will read whole file and check if we find a username with the same name
            try(ObjectInputStream objReader = new ObjectInputStream(new FileInputStream(file))){
                while(true){
                    try{
                        User user = (User) objReader.readObject();
                        if(user.getUsername().equals(username))
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

    class AppendableObjectOutputStream extends ObjectOutputStream {
        // Constructor
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        // Overriding the header-writing method to skip writing it
        @Override
        protected void writeStreamHeader() throws IOException {
            // Do nothing to prevent header from being written again
        }
    }


    public static void main(String[] args){
        System.out.println(System.getProperty("user.dir"));
    }
}
