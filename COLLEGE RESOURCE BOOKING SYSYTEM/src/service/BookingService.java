package service;

import model.Booking;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private List<Booking> bookings;

    public BookingService() { bookings = new ArrayList<>(); }

    public void addBooking(Booking booking) { bookings.add(booking); }

    public List<Booking> getAllBookings() { return bookings; }
}
