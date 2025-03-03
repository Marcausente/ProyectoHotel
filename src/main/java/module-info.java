module com.example.hotelv2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hotelv2 to javafx.fxml;
    exports com.example.hotelv2;
}