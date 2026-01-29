package model;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String password;
    private boolean isAdmin;

    public User(String userId, String name, String email, String password, boolean isAdmin) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return isAdmin; }

    @Override
    public String toString() {
        return name + " (" + (isAdmin ? "Admin" : "User") + ")";
    }
}
