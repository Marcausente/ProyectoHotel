package com.example.hotelv2.model;

/**
 * Clase que representa una habitación de hotel
 */
public class Room {
    private String id;
    private RoomType type;
    private double pricePerNight;
    private boolean available;
    private String description;
    private String imageUrl;

    public Room(String id, RoomType type, double pricePerNight, boolean available, String description, String imageUrl) {
        this.id = id;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.available = available;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return type.getDisplayName() + " - " + id;
    }
} 