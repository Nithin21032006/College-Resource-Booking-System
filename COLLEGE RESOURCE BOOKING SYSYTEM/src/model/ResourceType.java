package model;

public enum ResourceType {
    ROOM("Room"),
    EQUIPMENT("Equipment");

    private final String displayName;

    ResourceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
