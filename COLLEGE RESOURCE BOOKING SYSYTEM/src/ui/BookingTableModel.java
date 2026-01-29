package ui;

import model.Booking;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BookingTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Booking ID", "User", "Resource", "Date", "Start Time", "End Time", "Status", "Purpose"};
    private List<Booking> bookings;
    
    public BookingTableModel(List<Booking> bookings) {
        this.bookings = bookings;
    }
    
    @Override
    public int getRowCount() {
        return bookings.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Booking booking = bookings.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return booking.getBookingId();
            case 1: return booking.getUsername();
            case 2: return booking.getResourceId();
            case 3: return booking.getDate().toString();
            case 4: return booking.getStartTime().toString();
            case 5: return booking.getEndTime().toString();
            case 6: return booking.getStatus().toString();
            case 7: return booking.getPurpose();
            default: return null;
        }
    }
    
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
        fireTableDataChanged();
    }
    
    public Booking getBookingAt(int rowIndex) {
        return bookings.get(rowIndex);
    }
}