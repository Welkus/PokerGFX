module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    exports client;
    opens client to javafx.fxml;
}