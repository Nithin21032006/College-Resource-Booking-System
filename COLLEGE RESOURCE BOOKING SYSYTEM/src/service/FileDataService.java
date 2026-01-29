package service;

import model.BookingStatus;
import model.Resource;
import model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDataService {
    private static final String USERS_FILE = "users.dat";
    private static final String RESOURCES_FILE = "resources.dat";
    private static final String BOOKINGS_FILE = "bookings.dat";
    
    public void saveUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<User> loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            return (List<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Users file not found, starting with empty list");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return new ArrayList<>();
    }
    
    public void saveResources(List<Resource> resources) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RESOURCES_FILE))) {
            oos.writeObject(resources);
        } catch (IOException e) {
            System.err.println("Error saving resources: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Resource> loadResources() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RESOURCES_FILE))) {
            return (List<Resource>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Resources file not found, starting with empty list");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading resources: " + e.getMessage());
        }
        return new ArrayList<>();
    }
    
    public void saveBookings(List<BookingStatus> bookings) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE))) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.err.println("Error saving bookings: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<BookingStatus> loadBookings() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKINGS_FILE))) {
            return (List<BookingStatus>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Bookings file not found, starting with empty list");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading bookings: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}