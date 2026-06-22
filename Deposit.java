package bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class Deposit extends JPanel implements ActionListener {
    JLabel lTitle, lAmount;
    JTextField tfAmount;
    JButton btnDeposit;
    String cardNumber = "";
    MainFrame frame;

    public Deposit(MainFrame frame) {
        this.frame = frame;
        setLayout(null);

        lTitle = new JLabel("DEPOSIT SYSTEM");
        lTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lTitle.setBounds(320, 40, 200, 30);
        add(lTitle);

        lAmount = new JLabel("Enter Amount :");
        lAmount.setFont(new Font("Arial", Font.BOLD, 18));
        lAmount.setBounds(200, 120, 200, 30);
        add(lAmount);

        tfAmount = new JTextField();
        tfAmount.setBounds(380, 120, 200, 30);
        tfAmount.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) e.consume();
            }
        });
        add(tfAmount);

        btnDeposit = new JButton("Confirm Deposit");
        btnDeposit.setBounds(380, 180, 150, 40);
        btnDeposit.addActionListener(this);
        add(btnDeposit);
    }

    public void setCardNumber(String cardNo) {
        this.cardNumber = cardNo;
    }

    public void actionPerformed(ActionEvent ae) {
        String amountStr = tfAmount.getText();
        if(amountStr.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter an amount!");
            return;
        }

        try {
            double dAmount = Double.parseDouble(amountStr);
            if (dAmount <= 0) {
                JOptionPane.showMessageDialog(null, "Amount must be greater than 0.");
                return;
            }

            File oldFile = new File("Data.txt");
            File newFile = new File("Temp.txt");

            if(!oldFile.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(oldFile));
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(newFile)));

            String line;
            boolean isProcessed = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if(data.length < 9) {
                    pw.println(line);
                    continue;
                }

                if (data[0].equals(cardNumber)) {
                    double currentBalance = Double.parseDouble(data[8]);
                    double newBalance = currentBalance + dAmount;
                    pw.println(data[0]+","+data[1]+","+data[2]+","+data[3]+","+data[4]+","+data[5]+","+data[6]+","+data[7]+","+newBalance);
                    isProcessed = true;
                } else {
                    pw.println(line);
                }
            }
            pw.flush(); pw.close(); br.close();

            Files.move(newFile.toPath(), oldFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            if (isProcessed) {
                JOptionPane.showMessageDialog(null, "Rs. " + dAmount + " Deposited Successfully!");
                tfAmount.setText("");
                frame.tabbedPane.setSelectedIndex(2);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Transaction Error! " + e.getMessage());
        }
    }
}