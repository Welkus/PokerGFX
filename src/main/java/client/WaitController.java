package client;

import javafx.application.Platform;

/**
 * Klasa WaitController
 * poczekalnia dla graczy oczekujących w kolejce
 */
public class WaitController {

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
