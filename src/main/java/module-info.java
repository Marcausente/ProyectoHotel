module com.example.hotelv2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.example.hotelv2 to javafx.fxml;
    opens com.example.hotelv2.controller to javafx.fxml;
    opens com.example.hotelv2.model to javafx.base;
    
    exports com.example.hotelv2;
    exports com.example.hotelv2.controller;
    exports com.example.hotelv2.model;
}