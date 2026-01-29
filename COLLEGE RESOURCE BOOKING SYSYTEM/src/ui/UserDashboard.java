package ui;

import service.AuthService;
import service.ResourceService;
import service.BookingService;
import model.User;
import model.Booking;
import model.Resource;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class UserDashboard extends JFrame {
    private AuthService authService;
    private ResourceService resourceService;
    private BookingService bookingService;
    private User user;

    private JTextArea bookingsArea;
    private JComboBox<Resource> resourceCombo;
    private JTextField dateField, startTimeField, endTimeField;

    public UserDashboard(AuthService authService, ResourceService resourceService, BookingService bookingService, User user) {
        this.authService = authService;
        this.resourceService = resourceService;
        this.bookingService = bookingService;
        this.user = user;

        initializeUI();
        loadUserBookings();
        loadResources();
    }

    private void initializeUI() {
        setTitle("User Dashboard - " + user.getName());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel welcome = new JLabel("Welcome " + user.getName(), JLabel.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            authService.logout();
            dispose();
            new LoginFrame(authService, resourceService, bookingService);
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(welcome, BorderLayout.CENTER);
        topPanel.add(logoutButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        bookingsArea = new JTextArea(); bookingsArea.setEditable(false);
        add(new JScrollPane(bookingsArea), BorderLayout.CENTER);

        JPanel bookingPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        bookingPanel.setBorder(BorderFactory.createTitledBorder("Book a Resource"));

        bookingPanel.add(new JLabel("Select Resource:"));
        resourceCombo = new JComboBox<>(); bookingPanel.add(resourceCombo);

        bookingPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField(); bookingPanel.add(dateField);

        bookingPanel.add(new JLabel("Start Time (HH:MM):"));
        startTimeField = new JTextField(); bookingPanel.add(startTimeField);

        bookingPanel.add(new JLabel("End Time (HH:MM):"));
        endTimeField = new JTextField(); bookingPanel.add(endTimeField);

        JButton bookButton = new JButton("Book Resource");
        bookButton.addActionListener(e -> bookResource());
        bookingPanel.add(bookButton);

        add(bookingPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadUserBookings() {
        StringBuilder sb = new StringBuilder("Your Bookings:\n\n");
        for (Booking b : bookingService.getAllBookings()) {
            if (b.getUser().getUserId().equals(user.getUserId())) sb.append(b).append("\n");
        }
        bookingsArea.setText(sb.toString());
    }

    private void loadResources() {
        resourceCombo.removeAllItems();
        for (Resource r : resourceService.getAllResources()) if (r.isAvailable()) resourceCombo.addItem(r);
    }

    private void bookResource() {
        Resource selected = (Resource) resourceCombo.getSelectedItem();
        if (selected == null) { JOptionPane.showMessageDialog(this, "Please select a resource!"); return; }

        try {
            LocalDate date = LocalDate.parse(dateField.getText());
            LocalTime start = LocalTime.parse(startTimeField.getText());
            LocalTime end = LocalTime.parse(endTimeField.getText());

            String bookingId = "B" + (bookingService.getAllBookings().size() + 1);
            Booking booking = new Booking(bookingId, selected, user, date, start, end);
            bookingService.addBooking(booking);

            JOptionPane.showMessageDialog(this, "Booking request submitted!");
            loadUserBookings();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date or time format!");
        }
    }
}
