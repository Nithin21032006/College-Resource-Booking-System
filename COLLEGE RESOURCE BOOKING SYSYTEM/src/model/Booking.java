package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    public enum Status { PENDING, APPROVED, REJECTED }

    private String bookingId;
    private Resource resource;
    private User user;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Status status;

    public Booking(String bookingId, Resource resource, User user, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.bookingId = bookingId;
        this.resource = resource;
        this.user = user;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = Status.PENDING;
    }

    public String getBookingId() { return bookingId; }
    public Resource getResource() { return resource; }
    public User getUser() { return user; }
    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return bookingId + " | " + resource.getName() + " | " + date + " | " + startTime + "-" + endTime + " | " + status;
    }
}
