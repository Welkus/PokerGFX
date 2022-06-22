package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.media.MediaView;

public class WaitController {
    @FXML
    private MediaView mediaView;

    public void logoutFromWait() {

        main.client.out.println("logoutwait");
        Platform.exit();
        System.exit(0);
    }
}
