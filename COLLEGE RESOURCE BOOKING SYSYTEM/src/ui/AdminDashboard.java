package ui;

import service.AuthService;
import service.ResourceService;
import service.BookingService;
import model.Booking;
import model.Resource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminDashboard extends JFrame {
    private AuthService authService;
    private ResourceService resourceService;
    private BookingService bookingService;

    private JTable resourcesTable, bookingsTable;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ISO_LOCAL_TIME;

    public AdminDashboard(AuthService authService, ResourceService resourceService, BookingService bookingService) {
        this.authService = authService;
        this.resourceService = resourceService;
        this.bookingService = bookingService;

        initializeUI();
        loadResourcesData();
        loadBookingsData();
    }

    private void initializeUI() {
        setTitle("Admin Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Admin Dashboard", JLabel.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            authService.logout();
            dispose();
            new LoginFrame(authService, resourceService, bookingService);
        });
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Resources", createResourcesPanel());
        tabbedPane.addTab("Bookings", createBookingsPanel());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createResourcesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] cols = {"ID", "Name", "Type", "Capacity", "Location", "Available"};
        resourcesTable = new JTable(new DefaultTableModel(cols, 0));
        panel.add(new JScrollPane(resourcesTable), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> loadResourcesData());
        panel.add(refresh, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] cols = {"Booking ID", "User", "Resource", "Date", "Start", "End", "Status"};
        bookingsTable = new JTable(new DefaultTableModel(cols, 0));
        panel.add(new JScrollPane(bookingsTable), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        JButton approve = new JButton("Approve");
        JButton reject = new JButton("Reject");
        actionPanel.add(approve);
        actionPanel.add(reject);
        panel.add(actionPanel, BorderLayout.SOUTH);

        approve.addActionListener(e -> manageBooking(true));
        reject.addActionListener(e -> manageBooking(false));

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> loadBookingsData());
        panel.add(refresh, BorderLayout.NORTH);

        return panel;
    }

    private void loadResourcesData() {
        DefaultTableModel model = (DefaultTableModel) resourcesTable.getModel();
        model.setRowCount(0);
        List<Resource> list = resourceService.getAllResources();
        for (Resource r : list) model.addRow(new Object[]{
                r.getResourceId(), r.getName(), r.getType().getDisplayName(),
                r.getCapacity(), r.getLocation(), r.isAvailable() ? "Yes" : "No"
        });
    }

    private void loadBookingsData() {
        DefaultTableModel model = (DefaultTableModel) bookingsTable.getModel();
        model.setRowCount(0);
        List<Booking> list = bookingService.getAllBookings();
        for (Booking b : list) model.addRow(new Object[]{
                b.getBookingId(), b.getUser().getName(), b.getResource().getName(),
                b.getDate(), b.getStartTime(), b.getEndTime(), b.getStatus()
        });
    }

    private void manageBooking(boolean approve) {
        int row = bookingsTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a booking."); return; }
        String bookingId = (String) bookingsTable.getValueAt(row, 0);
        Booking booking = bookingService.getAllBookings().stream()
                .filter(b -> b.getBookingId().equals(bookingId)).findFirst().orElse(null);
        if (booking != null) {
            booking.setStatus(approve ? Booking.Status.APPROVED : Booking.Status.REJECTED);
            if (approve) booking.getResource().setAvailable(false);
            loadBookingsData();
            loadResourcesData();
            JOptionPane.showMessageDialog(this, approve ? "Booking approved." : "Booking rejected.");
        }
    }
}
