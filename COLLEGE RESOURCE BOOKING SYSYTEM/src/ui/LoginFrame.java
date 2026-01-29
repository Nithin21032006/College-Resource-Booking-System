package ui;

import service.AuthService;
import service.ResourceService;
import service.BookingService;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private AuthService authService;
    private ResourceService resourceService;
    private BookingService bookingService;
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame(AuthService authService, ResourceService resourceService, BookingService bookingService) {
        this.authService = authService;
        this.resourceService = resourceService;
        this.bookingService = bookingService;

        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Email:"));
        emailField = new JTextField(); add(emailField);
        add(new JLabel("Password:"));
        passwordField = new JPasswordField(); add(passwordField);

        JButton loginButton = new JButton("Login"); add(loginButton);
        JButton exitButton = new JButton("Exit"); add(exitButton);

        loginButton.addActionListener(e -> login());
        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void login() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        User user = authService.authenticate(email, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            if (user.isAdmin()) new AdminDashboard(authService, resourceService, bookingService);
            else new UserDashboard(authService, resourceService, bookingService, user);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password!");
        }
    }
}
