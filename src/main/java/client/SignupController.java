package client;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URISyntaxException;

/**
 * Klasa kontrolera zapisu nowego gracza
 */
public class SignupController {

    @FXML
    private TextField userName_sign;
    @FXML
    private PasswordField password_sign;
    @FXML
    private MediaView mediaView;

    /**
     * metoda zapisz
     */
    public void signUp() {
        if (userName_sign.getText() != null && password_sign.getText() != null) {

            Game.client.out.println("signup#" + userName_sign.getText() + "#" + password_sign.getText());
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
