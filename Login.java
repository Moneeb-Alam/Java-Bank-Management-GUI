package bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Login extends JPanel implements ActionListener {
    JLabel lWelcome, lCard, lPin;
    JTextField tfCardNo;
    JPasswordField pfPinNo;
    JButton bLogin, bClear, bSignup;
    MainFrame frame;

    public Login(MainFrame frame) {
        this.frame = frame;
        setLayout(null);

        lWelcome = new JLabel("Welcome to Bank System");
        lWelcome.setFont(new Font("Arial", Font.BOLD, 30));
        lWelcome.setBounds(200, 40, 450, 40);
        add(lWelcome);

        lCard = new JLabel("Card No :");
        lCard.setFont(new Font("Arial", Font.BOLD, 18));
        lCard.setBounds(100, 150, 180, 30);
        add(lCard);

        tfCardNo = new JTextField();
        tfCardNo.setBounds(300, 150, 250, 30);
        tfCardNo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || tfCardNo.getText().length() >= 16) e.consume();
            }
        });
        add(tfCardNo);

        lPin = new JLabel("Pin No :");
        lPin.setFont(new Font("Arial", Font.BOLD, 18));
        lPin.setBounds(111, 220, 160, 30);
        add(lPin);

        pfPinNo = new JPasswordField();
        pfPinNo.setBounds(300, 220, 250, 30);
        pfPinNo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || new String(pfPinNo.getPassword()).length() >= 4) e.consume();
            }
        });
        add(pfPinNo);

        bLogin = new JButton("Login");
        bLogin.setBounds(300, 300, 100, 30);
        add(bLogin);

        bClear = new JButton("Clear");
        bClear.setBounds(450, 300, 100, 30);
        add(bClear);

        bSignup = new JButton("Sign up");
        bSignup.setBounds(300, 350, 250, 30);
        add(bSignup);

        bLogin.addActionListener(this);
        bClear.addActionListener(this);
        bSignup.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == bLogin) {
                String enteredCard = tfCardNo.getText();
                String enteredPin = new String(pfPinNo.getPassword());

                if (enteredCard.length() != 16 || enteredPin.length() != 4) {
                    JOptionPane.showMessageDialog(null, "Please enter correct 16-digit Card No and 4-digit PIN!");
                } else {
                    boolean matchFound = false;
                    File file = new File("Data.txt");
                    if (file.exists()) {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] data = line.split(",");
                            if (data.length >= 9 && data[0].equals(enteredCard) && data[1].equals(enteredPin)) {
                                matchFound = true;
                                break;
                            }
                        }
                        br.close();
                    }
                    if (matchFound) {
                        tfCardNo.setText("");
                        pfPinNo.setText("");
                        frame.loginSuccess(enteredCard);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Card Num or PIN!");
                    }
                }
            } else if (ae.getSource() == bClear) {
                tfCardNo.setText("");
                pfPinNo.setText("");
            } else if (ae.getSource() == bSignup) {
                frame.tabbedPane.setSelectedIndex(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}