package com.example.hotelv2.controller;

import com.example.hotelv2.model.HotelService;
import com.example.hotelv2.model.Reservation;
import com.example.hotelv2.model.Room;
import com.example.hotelv2.model.RoomType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controlador principal de la aplicación de hotel
 */
public class HotelController {

    @FXML
    private ComboBox<RoomType> roomTypeComboBox;

    @FXML
    private ListView<Room> roomListView;

    @FXML
    private Label roomIdLabel;

    @FXML
    private Label roomTypeLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView roomImageView;

    @FXML
    private TextField guestNameField;

    @FXML
    private TextField guestEmailField;

    @FXML
    private DatePicker checkInDatePicker;

    @FXML
    private DatePicker checkOutDatePicker;

    @FXML
    private Button reserveButton;

    private HotelService hotelService;
    private ObservableList<Room> roomObservableList;

    @FXML
    public void initialize() {
        hotelService = new HotelService();
        
        // Inicializar ComboBox con tipos de habitación
        roomTypeComboBox.setItems(FXCollections.observableArrayList(RoomType.values()));
        roomTypeComboBox.setPromptText("Seleccionar tipo de habitación");
        
        // Configurar el formato de visualización del ComboBox
        roomTypeComboBox.setCellFactory(param -> new ListCell<RoomType>() {
            @Override
            protected void updateItem(RoomType item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getDisplayName());
                }
            }
        });
        
        roomTypeComboBox.setButtonCell(new ListCell<RoomType>() {
            @Override
            protected void updateItem(RoomType item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getDisplayName());
                }
            }
        });
        
        // Configurar el ListView
        roomObservableList = FXCollections.observableArrayList(hotelService.getAvailableRooms());
        roomListView.setItems(roomObservableList);
        
        // Configurar el formato de visualización del ListView
        roomListView.setCellFactory(param -> new ListCell<Room>() {
            @Override
            protected void updateItem(Room item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getType().getDisplayName() + " - " + item.getId() + " - $" + item.getPricePerNight());
                }
            }
        });
        
        // Listener para el ComboBox
        roomTypeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filterRoomsByType(newValue);
            } else {
                loadAllAvailableRooms();
            }
        });
        
        // Listener para el ListView
        roomListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayRoomDetails(newValue);
            } else {
                clearRoomDetails();
            }
        });
        
        // Configurar DatePickers
        checkInDatePicker.setValue(LocalDate.now());
        checkOutDatePicker.setValue(LocalDate.now().plusDays(1));
        
        // Deshabilitar el botón de reserva hasta que se seleccione una habitación
        reserveButton.setDisable(true);
    }
    
    private void filterRoomsByType(RoomType roomType) {
        List<Room> filteredRooms = hotelService.getRoomsByType(roomType);
        roomObservableList.clear();
        roomObservableList.addAll(filteredRooms);
    }
    
    private void loadAllAvailableRooms() {
        List<Room> availableRooms = hotelService.getAvailableRooms();
        roomObservableList.clear();
        roomObservableList.addAll(availableRooms);
    }
    
    private void displayRoomDetails(Room room) {
        roomIdLabel.setText(room.getId());
        roomTypeLabel.setText(room.getType().getDisplayName());
        priceLabel.setText(String.format("$%.2f por noche", room.getPricePerNight()));
        descriptionLabel.setText(room.getDescription());
        
        try {
            Image image = new Image(getClass().getResourceAsStream(room.getImageUrl()));
            roomImageView.setImage(image);
        } catch (Exception e) {
            // Si no se puede cargar la imagen, usar una imagen por defecto
            roomImageView.setImage(null);
        }
        
        // Habilitar el botón de reserva
        reserveButton.setDisable(false);
    }
    
    private void clearRoomDetails() {
        roomIdLabel.setText("");
        roomTypeLabel.setText("");
        priceLabel.setText("");
        descriptionLabel.setText("");
        roomImageView.setImage(null);
        reserveButton.setDisable(true);
    }
    
    @FXML
    private void handleReserveButtonAction() {
        Room selectedRoom = roomListView.getSelectionModel().getSelectedItem();
        
        if (selectedRoom == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se ha seleccionado ninguna habitación");
            return;
        }
        
        String guestName = guestNameField.getText().trim();
        String guestEmail = guestEmailField.getText().trim();
        LocalDate checkInDate = checkInDatePicker.getValue();
        LocalDate checkOutDate = checkOutDatePicker.getValue();
        
        // Validaciones
        if (guestName.isEmpty() || guestEmail.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Por favor, complete todos los campos");
            return;
        }
        
        if (checkInDate == null || checkOutDate == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Por favor, seleccione las fechas de entrada y salida");
            return;
        }
        
        if (checkOutDate.isBefore(checkInDate)) {
            showAlert(Alert.AlertType.ERROR, "Error", "La fecha de salida debe ser posterior a la fecha de entrada");
            return;
        }
        
        // Crear la reserva
        Reservation reservation = hotelService.createReservation(
                selectedRoom.getId(), guestName, guestEmail, checkInDate, checkOutDate);
        
        if (reservation != null) {
            showConfirmationAlert(reservation);
            
            // Actualizar la lista de habitaciones disponibles
            loadAllAvailableRooms();
            
            // Limpiar los campos del formulario
            guestNameField.clear();
            guestEmailField.clear();
            checkInDatePicker.setValue(LocalDate.now());
            checkOutDatePicker.setValue(LocalDate.now().plusDays(1));
            
            // Limpiar la selección
            roomListView.getSelectionModel().clearSelection();
            clearRoomDetails();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo realizar la reserva. La habitación ya no está disponible.");
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showConfirmationAlert(Reservation reservation) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reserva Confirmada");
        alert.setHeaderText("¡Su reserva ha sido confirmada!");
        
        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("ID de Reserva: " + reservation.getId()),
                new Label("Habitación: " + reservation.getRoom().getType().getDisplayName() + " - " + reservation.getRoom().getId()),
                new Label("Huésped: " + reservation.getGuestName()),
                new Label("Email: " + reservation.getGuestEmail()),
                new Label("Fecha de entrada: " + reservation.getCheckInDate()),
                new Label("Fecha de salida: " + reservation.getCheckOutDate()),
                new Label(String.format("Precio total: $%.2f", reservation.getTotalPrice()))
        );
        
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }
    
    @FXML
    private void handleClearFilterButtonAction() {
        roomTypeComboBox.getSelectionModel().clearSelection();
        loadAllAvailableRooms();
    }
} 