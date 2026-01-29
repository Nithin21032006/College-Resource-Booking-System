package ui;

import model.Booking;
import model.Resource;
import service.BookingService;
import service.ResourceService;
import utils.DateUtils;
import utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingDialog extends JDialog {
    private final BookingService bookingService;
    private final ResourceService resourceService;
    private final Resource resource;
    private final String username;

    private JTextField dateField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextArea purposeArea;
    private JButton bookButton;
    private JButton cancelButton;
    private JLabel conflictLabel;

    public BookingDialog(JFrame parent, BookingService bookingService, ResourceService resourceService,
                         Resource resource, String username) {
        super(parent, "Book Resource: " + resource.getName(), true);
        this.bookingService = bookingService;
        this.resourceService = resourceService;
        this.resource = resource;
        this.username = username;

        initializeUI();
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        // Main form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Resource info
        formPanel.add(new JLabel("Resource:"));
        formPanel.add(new JLabel(resource.getName() + " (" + resource.getLocation() + ")"));

        // Date
        formPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField(LocalDate.now().toString());
        formPanel.add(dateField);

        // Start Time
        formPanel.add(new JLabel("Start Time (HH:MM):"));
        startTimeField = new JTextField("09:00");
        formPanel.add(startTimeField);

        // End Time
        formPanel.add(new JLabel("End Time (HH:MM):"));
        endTimeField = new JTextField("10:00");
        formPanel.add(endTimeField);

        // Purpose
        formPanel.add(new JLabel("Purpose:"));
        purposeArea = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(purposeArea);
        formPanel.add(scrollPane);

        add(formPanel, BorderLayout.CENTER);

        // Conflict warning
        conflictLabel = new JLabel(" ");
        conflictLabel.setForeground(Color.RED);
        add(conflictLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        bookButton = new JButton("Book Now");
        cancelButton = new JButton("Cancel");

        bookButton.setBackground(new Color(46, 204, 113));
        bookButton.setForeground(Color.WHITE);

        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setupEventListeners();
    }

    private void setupEventListeners() {
        ActionListener conflictChecker = e -> checkForConflicts();

        dateField.addActionListener(conflictChecker);
        startTimeField.addActionListener(conflictChecker);
        endTimeField.addActionListener(conflictChecker);

        bookButton.addActionListener(e -> attemptBooking());

        cancelButton.addActionListener(e -> dispose());
    }

    private void checkForConflicts() {
        LocalDate date = DateUtils.parseDate(dateField.getText());
        LocalTime startTime = DateUtils.parseTime(startTimeField.getText());
        LocalTime endTime = DateUtils.parseTime(endTimeField.getText());

        if (date == null || startTime == null || endTime == null) {
            conflictLabel.setText("Invalid date or time format");
            conflictLabel.setForeground(Color.RED);
            return;
        }

        if (!ValidationUtils.isTimeRangeValid(startTimeField.getText(), endTimeField.getText())) {
            conflictLabel.setText("Invalid time range or outside operating hours (08:00-22:00)");
            conflictLabel.setForeground(Color.RED);
            return;
        }

        if (!ValidationUtils.isFutureOrPresentDate(date)) {
            conflictLabel.setText("Booking date must be today or in the future");
            conflictLabel.setForeground(Color.RED);
            return;
        }

        boolean available = bookingService.isTimeSlotAvailable(resource.getResourceId(), date, startTime, endTime);

        if (available) {
            conflictLabel.setText("Time slot is available");
            conflictLabel.setForeground(new Color(46, 204, 113));
        } else {
            conflictLabel.setText("Time slot is not available - Conflict detected");
            conflictLabel.setForeground(Color.RED);
        }
    }

    private void attemptBooking() {
        LocalDate date = DateUtils.parseDate(dateField.getText());
        LocalTime startTime = DateUtils.parseTime(startTimeField.getText());
        LocalTime endTime = DateUtils.parseTime(endTimeField.getText());
        String purpose = purposeArea.getText().trim();

        if (date == null) {
            JOptionPane.showMessageDialog(this, "Please enter a valid date (YYYY-MM-DD)");
            return;
        }
        if (startTime == null || endTime == null) {
            JOptionPane.showMessageDialog(this, "Please enter valid times (HH:MM)");
            return;
        }
        if (!ValidationUtils.isTimeRangeValid(startTimeField.getText(), endTimeField.getText())) {
            JOptionPane.showMessageDialog(this, "Invalid time range or outside operating hours (08:00-22:00)");
            return;
        }
        if (!ValidationUtils.isFutureOrPresentDate(date)) {
            JOptionPane.showMessageDialog(this, "Booking date must be today or in the future");
            return;
        }
        if (purpose.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a purpose for the booking");
            return;
        }

        String bookingId = bookingService.generateBookingId();
        Booking booking = new Booking(bookingId, username, resource.getResourceId(), date, startTime, endTime, purpose);

        if (bookingService.bookResource(booking)) {
            JOptionPane.showMessageDialog(this,
                    "Booking successful!\nBooking ID: " + bookingId +
                            "\nResource: " + resource.getName() +
                            "\nDate: " + date +
                            "\nTime: " + startTime + " - " + endTime);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Booking failed! Time slot may be unavailable.",
                    "Booking Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
