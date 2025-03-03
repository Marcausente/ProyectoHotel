package com.example.hotelv2.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Clase de servicio que gestiona las habitaciones y reservas del hotel
 */
public class HotelService {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public HotelService() {
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        initializeRooms();
    }

    private void initializeRooms() {
        // Habitaciones Single
        rooms.add(new Room("S101", RoomType.SINGLE, 80.0, true, 
                "Habitación individual con vistas al jardín", "/com/example/hotelv2/images/single.jpg"));
        rooms.add(new Room("S102", RoomType.SINGLE, 85.0, true, 
                "Habitación individual con vistas a la ciudad", "/com/example/hotelv2/images/single.jpg"));
        rooms.add(new Room("S103", RoomType.SINGLE, 80.0, true, 
                "Habitación individual con baño completo", "/com/example/hotelv2/images/single.jpg"));
        
        // Habitaciones Double
        rooms.add(new Room("D201", RoomType.DOUBLE, 120.0, true, 
                "Habitación doble con cama king size", "/com/example/hotelv2/images/double.jpg"));
        rooms.add(new Room("D202", RoomType.DOUBLE, 125.0, true, 
                "Habitación doble con vistas al mar", "/com/example/hotelv2/images/double.jpg"));
        rooms.add(new Room("D203", RoomType.DOUBLE, 130.0, true, 
                "Habitación doble con balcón", "/com/example/hotelv2/images/double.jpg"));
        
        // Habitaciones Twin
        rooms.add(new Room("T301", RoomType.TWIN, 110.0, true, 
                "Habitación twin con dos camas individuales", "/com/example/hotelv2/images/twin.jpg"));
        rooms.add(new Room("T302", RoomType.TWIN, 115.0, true, 
                "Habitación twin con vistas a la montaña", "/com/example/hotelv2/images/twin.jpg"));
        
        // Suites
        rooms.add(new Room("S401", RoomType.SUITE, 200.0, true, 
                "Suite de lujo con jacuzzi", "/com/example/hotelv2/images/suite.jpg"));
        rooms.add(new Room("S402", RoomType.SUITE, 250.0, true, 
                "Suite presidencial con terraza", "/com/example/hotelv2/images/suite.jpg"));
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    public List<Room> getAvailableRooms() {
        return rooms.stream()
                .filter(Room::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Room> getRoomsByType(RoomType type) {
        return rooms.stream()
                .filter(room -> room.getType() == type && room.isAvailable())
                .collect(Collectors.toList());
    }

    public Room getRoomById(String roomId) {
        return rooms.stream()
                .filter(room -> room.getId().equals(roomId))
                .findFirst()
                .orElse(null);
    }

    public Reservation createReservation(String roomId, String guestName, String guestEmail, 
                                        LocalDate checkIn, LocalDate checkOut) {
        Room room = getRoomById(roomId);
        
        if (room == null || !room.isAvailable()) {
            return null;
        }
        
        String reservationId = UUID.randomUUID().toString().substring(0, 8);
        Reservation reservation = new Reservation(reservationId, room, guestName, guestEmail, checkIn, checkOut);
        
        // Marcar la habitación como no disponible
        room.setAvailable(false);
        
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }

    public boolean cancelReservation(String reservationId) {
        Reservation reservation = reservations.stream()
                .filter(r -> r.getId().equals(reservationId))
                .findFirst()
                .orElse(null);
                
        if (reservation != null) {
            // Marcar la habitación como disponible nuevamente
            reservation.getRoom().setAvailable(true);
            reservations.remove(reservation);
            return true;
        }
        
        return false;
    }
} 