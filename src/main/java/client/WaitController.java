package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.media.MediaView;

/**
 * Klasa WaitController
 * poczekalnia dla graczy oczekujących w kolejce
 */
public class WaitController {
    @FXML
    private MediaView mediaView;
    /**
     * metoda plogoutFromWait pozwala opuścić poczekalnie przed czasem
     * i zaskończyć grę
     */
    public void logoutFromWait(){

        main.client.out.println("logoutwait");
        Platform.exit();
        System.exit(0);
    }
}
