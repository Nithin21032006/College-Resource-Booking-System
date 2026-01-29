package model;

import java.io.Serializable;

public class Resource implements Serializable {
    private String resourceId;
    private String name;
    private ResourceType type;
    private int capacity;
    private String location;
    private boolean available = true;

    public Resource(String resourceId, String name, ResourceType type, int capacity, String location) {
        this.resourceId = resourceId;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.location = location;
    }

    public String getResourceId() { return resourceId; }
    public String getName() { return name; }
    public ResourceType getType() { return type; }
    public int getCapacity() { return capacity; }
    public String getLocation() { return location; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return resourceId + " - " + name;
    }
}
