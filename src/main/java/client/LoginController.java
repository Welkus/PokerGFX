package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Klasa LoginController okno logowania
 *
 * @author K.Woelke
 */
public class LoginController {


    @FXML
    private TextField userName_log;
    @FXML
    private PasswordField password_log;
    @FXML
    private MediaView mediaView;


    /**
     * Logowanie do aplikacji
     */
    public void logIn() {
        if (userName_log.getText() != null && password_log.getText() != null) {

            Game.client.out.println("login#" + userName_log.getText() + "#" + password_log.getText());
        }
    }

    /**
     * metoda pozawalająca na utworzenie konta użytkownika
     * przygotowanie sceny dla formatki rejestracji gracza
     */
    public void createAccount() {
        onPlayAudio();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signupScreen.fxml"));
            Parent root = loader.load();

            SignupController sc = loader.getController();
            Game.client.setSignUpController(sc);

            Game.stage.close();
            Game.stage.setTitle("Online Poker - Zapisz");
            Game.stage.setScene(new Scene(root, 950, 520));
            Game.stage.setResizable(false);
            Game.stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPlayAudio() {
        if (mediaView.getMediaPlayer() == null) {
            try {
                String fileName = getClass().getResource("/click.mp3").toURI().toString();
                Media media = new Media(fileName);
                MediaPlayer player = new MediaPlayer(media);
                mediaView.setMediaPlayer(player);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        mediaView.getMediaPlayer().seek(mediaView.getMediaPlayer().getStartTime());
        mediaView.getMediaPlayer().play();
    }
}


