package bank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class Withdraw extends JPanel implements ActionListener {
    JLabel lTitle, lAmount;
    JTextField tfAmount;
    JButton bWith;
    String card = "";
    MainFrame frame;

    public Withdraw(MainFrame frame) {
        this.frame = frame;
        setLayout(null);

        lTitle = new JLabel("WITHDRAW SYSTEM");
        lTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lTitle.setBounds(310, 40, 250, 30);
        add(lTitle);

        lAmount = new JLabel("Enter Amount (Rs):");
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

        bWith = new JButton("Confirm Withdraw");
        bWith.setBounds(380, 180, 150, 40);
        bWith.addActionListener(this);
        add(bWith);
    }

    public void setCardNumber(String cardNo) {
        this.card = cardNo;
    }

    public void actionPerformed(ActionEvent ae) {
        String amountStr = tfAmount.getText();
        if(amountStr.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter an amount!");
            return;
        }
        try {
            double wAmount = Double.parseDouble(amountStr);
            if (wAmount <= 0) {
                JOptionPane.showMessageDialog(null, "Amount must be greater than 0.");
                return;
            }

            File oldFile = new File("Data.txt");
            File newFile = new File("Temp.txt");

            if(!oldFile.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(oldFile));
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(newFile)));

            String line;
            boolean sufficientFunds = false;
            boolean isProcessed = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if(data.length < 9) {
                    pw.println(line);
                    continue;
                }

                if (data[0].equals(card)) {
                    double currentBalance = Double.parseDouble(data[8]);
                    if (currentBalance >= wAmount) {
                        double newBalance = currentBalance - wAmount;
                        pw.println(data[0]+","+data[1]+","+data[2]+","+data[3]+","+data[4]+","+data[5]+","+data[6]+","+data[7]+","+newBalance);
                        sufficientFunds = true;
                        isProcessed = true;
                    } else {
                        pw.println(line);
                        isProcessed = true;
                    }
                } else {
                    pw.println(line);
                }
            }
            pw.flush(); pw.close(); br.close();

            Files.move(newFile.toPath(), oldFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            if (sufficientFunds) {
                JOptionPane.showMessageDialog(null, "Rs. " + wAmount + " Withdrawn Successfully!");
                tfAmount.setText("");
                frame.tabbedPane.setSelectedIndex(2);
            } else if (isProcessed) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance!");
            } else {
                JOptionPane.showMessageDialog(null, "User Account Not Found!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Transaction Error! " + e.getMessage());
        }
    }
}