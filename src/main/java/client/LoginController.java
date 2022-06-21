package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
/**
 * Klasa LoginController okno logowania
 * @author K.Woelke
 */
public class LoginController{

    @FXML private TextField userName_log;
    @FXML private PasswordField password_log;

    /**
     * Logowanie do aplikacji
     */
    public void logIn() {
        if (userName_log.getText() != null && password_log.getText() != null) {

           main.client.out.println("login#"+userName_log.getText()+"#"+password_log.getText());
        }
    }

    /**
     * metoda pozawalająca na utworzenie konta użytkownika
     * przygotowanie sceny dla formatki rejestracji gracza
     */
    public void createAccount(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signupScreen.fxml"));
            Parent root = loader.load();

            SignupController sc = loader.getController();
            main.client.setSignUpController(sc);

            main.stage.close();
            main.stage.setTitle("Online Poker - Zapisz");
            main.stage.setScene(new Scene(root, 950, 520));
            main.stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


