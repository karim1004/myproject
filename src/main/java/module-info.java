module com.mycompany.hotelsystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.hotelsystem      to javafx.fxml;
    opens application.controller.java   to javafx.fxml;

    exports com.mycompany.hotelsystem;
    exports hotel.people;
    exports hotel.rooms;
    exports hotel.reservations;
    exports hotel.database;
    exports hotel.model;
    exports hotel.exceptions;
    exports hotel.interfaces;
}
