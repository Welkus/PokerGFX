package client;

import javafx.application.Platform;

public class WaitController {

    public void logoutFromWait(){

        main.client.out.println("logoutwait");
        Platform.exit();
        System.exit(0);
    }
}
