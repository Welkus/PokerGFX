package client;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *  Klasa kontrolera zapisu nowego gracza
 *
 */
public class SignupController {

    @FXML private TextField userName_sign;
    @FXML private PasswordField password_sign;

    /**
     * metoda zapisz
     */
    public void signUp(){
        if (userName_sign.getText() != null && password_sign.getText() != null) {

            main.client.out.println("signup#"+userName_sign.getText()+"#"+password_sign.getText());
        }
    }

}
