module client {
    requires javafx.controls;
    requires javafx.fxml;

    exports client;
    opens client to javafx.fxml;
}