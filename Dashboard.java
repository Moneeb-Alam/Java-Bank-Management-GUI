package bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Dashboard extends JPanel implements ActionListener {
    MainFrame frame;
    String card = "";
    JLabel lTitle;
    JButton bDep, bWith, bBal, bExit;

    public Dashboard(MainFrame frame) {
        this.frame = frame;
        setLayout(null);

        lTitle = new JLabel("Please Select Your Transaction");
        lTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lTitle.setBounds(230, 60, 400, 40);
        add(lTitle);

        bDep = new JButton("Deposit Cash");
        bDep.setBounds(230, 150, 160, 45);
        add(bDep);

        bWith = new JButton("Cash Withdrawal");
        bWith.setBounds(430, 150, 160, 45);
        add(bWith);

        bBal = new JButton("Check Balance");
        bBal.setBounds(230, 220, 160, 45);
        add(bBal);

        bExit = new JButton("Logout ");
        bExit.setBounds(430, 220, 160, 45);
        add(bExit);

        bDep.addActionListener(this);
        bWith.addActionListener(this);
        bBal.addActionListener(this);
        bExit.addActionListener(this);
    }

    public void setCardNumber(String cardNo) {
        this.card = cardNo;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bExit) {
            frame.logout();
        } else if (ae.getSource() == bDep) {
            frame.tabbedPane.setSelectedIndex(3);
        } else if (ae.getSource() == bWith) {
            frame.tabbedPane.setSelectedIndex(4);
        } else if (ae.getSource() == bBal) {
            try {
                File file = new File("Data.txt");
                if (!file.exists()) return;

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                String currentBalance = "0.0";

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 9 && data[0].equals(card)) {
                        currentBalance = data[8];
                        break;
                    }
                }
                br.close();

                JOptionPane.showMessageDialog(null, "Your Current Balance is: Rs " + currentBalance);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error fetching balance!");
            }
        }
    }

    public void refreshBalance() {
    }
}