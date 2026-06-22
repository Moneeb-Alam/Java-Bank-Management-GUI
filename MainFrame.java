package bank;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public JTabbedPane tabbedPane;
    public Login loginPanel;
    public Signup signupPanel;
    public Dashboard dashboardPanel;
    public Deposit depositPanel;
    public Withdraw withdrawPanel;
    public String loggedInCard = "";

    public MainFrame() {
        setTitle("Bank Management System");
        setSize(850, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        loginPanel = new Login(this);
        signupPanel = new Signup(this);
        dashboardPanel = new Dashboard(this);
        depositPanel = new Deposit(this);
        withdrawPanel = new Withdraw(this);

        tabbedPane.addTab("Login Screen", loginPanel);
        tabbedPane.addTab("Sign Up", signupPanel);
        tabbedPane.addTab("Dashboard", dashboardPanel);
        tabbedPane.addTab("Deposit Cash", depositPanel);
        tabbedPane.addTab("Withdraw Cash", withdrawPanel);

        lockTransactionTabs();

        add(tabbedPane);
        setVisible(true);
    }

    public void loginSuccess(String cardNo) {
        this.loggedInCard = cardNo;

        tabbedPane.setEnabledAt(2, true);
        tabbedPane.setEnabledAt(3, true);
        tabbedPane.setEnabledAt(4, true);

        dashboardPanel.setCardNumber(cardNo);
        depositPanel.setCardNumber(cardNo);
        withdrawPanel.setCardNumber(cardNo);

        tabbedPane.setSelectedIndex(2);

        tabbedPane.setEnabledAt(0, false);
        tabbedPane.setEnabledAt(1, false);
    }

    public void logout() {
        this.loggedInCard = "";
        dashboardPanel.setCardNumber("");
        depositPanel.setCardNumber("");
        withdrawPanel.setCardNumber("");

        tabbedPane.setEnabledAt(0, true);
        tabbedPane.setEnabledAt(1, true);
        tabbedPane.setSelectedIndex(0);

        lockTransactionTabs();
    }

    private void lockTransactionTabs() {
        tabbedPane.setEnabledAt(2, false);
        tabbedPane.setEnabledAt(3, false);
        tabbedPane.setEnabledAt(4, false);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}