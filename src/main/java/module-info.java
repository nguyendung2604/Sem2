module org.example.healtech {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.healtech to javafx.fxml;
    exports org.example.healtech;
    exports org.example.healtech.controller;
    opens org.example.healtech.controller to javafx.fxml;
}