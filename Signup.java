package bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.*;

public class Signup extends JPanel implements ActionListener {
    JLabel lTitle, lName, lFName, lCnic, lDob, lGender, lProvince;
    JTextField tfName, tfFName, tfCnic;
    JComboBox<String> cbDate, cbMonth, cbYear, cbProvince;
    JRadioButton rbMale, rbFemale;
    ButtonGroup genderGroup;
    JButton bSubmit, bCancel;
    MainFrame frame;

    public Signup(MainFrame frame) {
        this.frame = frame;
        setLayout(null);

        lTitle = new JLabel("Create New Account");
        lTitle.setFont(new Font("Arial", Font.BOLD, 25));
        lTitle.setBounds(270, 20, 300, 40);
        add(lTitle);

        lName = new JLabel("Full Name:");
        lName.setFont(new Font("Arial", Font.BOLD, 16));
        lName.setBounds(150, 90, 150, 30);
        add(lName);

        tfName = new JTextField();
        tfName.setBounds(300, 90, 250, 30);
        tfName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar())) e.consume();
            }
        });
        add(tfName);

        lFName = new JLabel("Father Name:");
        lFName.setFont(new Font("Arial", Font.BOLD, 16));
        lFName.setBounds(150, 140, 150, 30);
        add(lFName);

        tfFName = new JTextField();
        tfFName.setBounds(300, 140, 250, 30);
        tfFName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar())) e.consume();
            }
        });
        add(tfFName);

        lCnic = new JLabel("Cnic No :");
        lCnic.setFont(new Font("Arial", Font.BOLD, 16));
        lCnic.setBounds(150, 190, 150, 30);
        add(lCnic);

        tfCnic = new JTextField();
        tfCnic.setBounds(300, 190, 250, 30);
        tfCnic.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) || tfCnic.getText().length() >= 13) e.consume();
            }
        });
        add(tfCnic);

        lDob = new JLabel("Date of Birth:");
        lDob.setFont(new Font("Arial", Font.BOLD, 16));
        lDob.setBounds(150, 240, 150, 30);
        add(lDob);

        String[] dates = new String[31];
        for (int i = 0; i < 31; i++) dates[i] = String.valueOf(i + 1);
        cbDate = new JComboBox<>(dates);
        cbDate.setBounds(300, 240, 60, 30);
        add(cbDate);

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        cbMonth = new JComboBox<>(months);
        cbMonth.setBounds(370, 240, 80, 30);
        add(cbMonth);

        String[] years = new String[66];
        int startYear = 2008;
        for (int i = 0; i < 66; i++) years[i] = String.valueOf(startYear - i);
        cbYear = new JComboBox<>(years);
        cbYear.setBounds(460, 240, 90, 30);
        add(cbYear);

        lGender = new JLabel("Gender:");
        lGender.setFont(new Font("Arial", Font.BOLD, 16));
        lGender.setBounds(150, 290, 150, 30);
        add(lGender);

        rbMale = new JRadioButton("Male");
        rbMale.setBounds(300, 290, 80, 30);
        rbFemale = new JRadioButton("Female");
        rbFemale.setBounds(400, 290, 80, 30);
        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);
        add(rbMale);
        add(rbFemale);

        lProvince = new JLabel("Province:");
        lProvince.setFont(new Font("Arial", Font.BOLD, 16));
        lProvince.setBounds(150, 340, 150, 30);
        add(lProvince);

        String[] provinces = {"Federal Capital", "Punjab", "Khyber Pakhtunkhwa", "Sindh", "Balochistan"};
        cbProvince = new JComboBox<>(provinces);
        cbProvince.setBounds(300, 340, 250, 30);
        add(cbProvince);

        bSubmit = new JButton("Submit");
        bSubmit.setBounds(300, 400, 110, 30);
        add(bSubmit);

        bCancel = new JButton("Cancel");
        bCancel.setBounds(440, 400, 110, 30);
        add(bCancel);

        bSubmit.addActionListener(this);
        bCancel.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bCancel) {
            frame.tabbedPane.setSelectedIndex(0);
        } else if (ae.getSource() == bSubmit) {
            String name = tfName.getText();
            String fname = tfFName.getText();
            String cnic = tfCnic.getText();
            String dob = cbDate.getSelectedItem() + "-" + cbMonth.getSelectedItem() + "-" + cbYear.getSelectedItem();
            String gender = rbMale.isSelected() ? "Male" : (rbFemale.isSelected() ? "Female" : "");
            String province = (String) cbProvince.getSelectedItem();

            if (name.equals("") || fname.equals("") || cnic.length() != 13 || gender.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all fields correctly!");
            } else {
                try {
                    Random ran = new Random();
                    String cardNo = "";
                    boolean unique = false;

                    while (!unique) {
                        cardNo = "" + Math.abs((ran.nextLong() % 90000000L) + 5040936000000000L);
                        unique = true;
                        File file = new File("Data.txt");
                        if (file.exists()) {
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] data = line.split(",");
                                if (data.length > 0 && data[0].equals(cardNo)) {
                                    unique = false; // Match found, generate again
                                    break;
                                }
                            }
                            br.close();
                        }
                    }

                    String pin = String.valueOf(1000 + ran.nextInt(9000));
                    String balance = "0.0";

                    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Data.txt", true)));
                    pw.println(cardNo + "," + pin + "," + name + "," + fname + "," + cnic + "," + dob + "," + gender + "," + province + "," + balance);
                    pw.flush(); pw.close();

                    JOptionPane.showMessageDialog(null, "Account Created!\nCard Number: " + cardNo + "\nPIN: " + pin);

                    tfName.setText(""); tfFName.setText(""); tfCnic.setText("");
                    cbDate.setSelectedIndex(0); cbMonth.setSelectedIndex(0); cbYear.setSelectedIndex(0); cbProvince.setSelectedIndex(0);
                    genderGroup.clearSelection();

                    frame.tabbedPane.setSelectedIndex(0);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "System Error! Data could not be saved.");
                }
            }
        }
    }
}