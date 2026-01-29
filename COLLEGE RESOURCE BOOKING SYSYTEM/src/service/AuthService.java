package service;

import model.User;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private List<User> users;
    private User currentUser;

    public AuthService() {
        users = new ArrayList<>();
        preloadUsers();
    }

    private void preloadUsers() {
        users.add(new User("U001", "Alice", "alice@example.com", "admin123", true));
        users.add(new User("U002", "Bob", "bob@example.com", "user123", false));
        users.add(new User("U003", "Charlie", "charlie@example.com", "user123", false));
    }

    public User authenticate(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                currentUser = user;
                return user;
            }
        }
        return null;
    }

    public void logout() { currentUser = null; }

    public boolean isAuthenticated() { return currentUser != null; }

    public User getCurrentUser() { return currentUser; }
}
