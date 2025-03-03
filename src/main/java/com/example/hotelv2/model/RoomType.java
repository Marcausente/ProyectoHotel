package com.example.hotelv2.model;

public enum RoomType {
    SINGLE("Single Room", "Habitación para una persona con una cama individual"),
    DOUBLE("Double Room", "Habitación para dos personas con una cama doble"),
    TWIN("Twin Room", "Habitación para dos personas con dos camas individuales"),
    SUITE("Suite", "Habitación premium con servicios extra");

    private final String displayName;
    private final String description;

    RoomType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
} 