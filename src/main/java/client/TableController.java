package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URISyntaxException;

/**
 * Klasa kontrolera gry
 *
 * @author K.Woelke
 */
public class TableController {

    @FXML
    public Label p1;
    @FXML
    public Label p2;
    @FXML
    public Label c1;
    @FXML
    public Label c2;
    @FXML
    private TextField raisebox;
    @FXML
    public TextField whichPturn;


    @FXML
    public ImageView cc1;
    @FXML
    public ImageView cc2;
    @FXML
    public ImageView cc3;
    @FXML
    public ImageView cc4;
    @FXML
    public ImageView cc5;
    @FXML
    public ImageView card21;
    @FXML
    public ImageView card22;

    @FXML
    public Text bet;
    @FXML
    public Text action;
    @FXML
    public Text pot;
    @FXML
    public Text message;
    @FXML
    public Text action2;

    @FXML
    public ImageView card1;
    @FXML
    public ImageView card2;

    public Circle circle;
    public Circle circle2;
    public Circle pot1;
    public Circle pot2;
    public Circle pot3;
    public Circle pot4;
    public Circle pot5;
    public Circle pot6;
    public Circle avatar1;
    public Circle avatar2;
    @FXML
    private MediaView mediaView;

    /**
     * metoda (call) pozwalająca graczowi na sprawdzenie
     * podbicia po jednym zakładzie i jednym lub kilku podbiciach
     * wywołana, wysyła do serwera String "call"
     */
    public void call() {
        onPlayAudio("click.mp3");
        message.setText(" ");

        if (Game.client.isTurn) {
            if (Game.client.currentbet - Game.client.selfBet <= Game.client.chips) {
                if (Game.client.currentbet != Game.client.selfBet) {

                    Game.client.out.println("call");
                    Game.client.selfBet = Game.client.currentbet;
                    Game.client.isTurn = false;
                } else message.setText("Nie moższ teraz sprawdzić. Wybierz Podbij lub Pas.");
            } else {
                message.setText("Musisz rzucić karty!");

                Game.client.out.println("fold");
                Game.client.isTurn = false;
            }
        } else message.setText("Teraz nie możesz tego zrobić!");
    }

    /**
     * metoda (check) "czekam" - przekazanie ruchu do drugiego gracza
     * bez wnoszenia zakładu.
     * wywołana wysyła do serwera String "check"
     */
    public void check() {
        onPlayAudio("click.mp3");
        message.setText(" ");

        if (Game.client.isTurn) {
            if (Game.client.currentbet == Game.client.selfBet) {
                Game.client.out.println("check");
                Game.client.isTurn = false;
            } else message.setText("Musisz sprawdzić aktualny zakład!");
        } else message.setText("Teraz nie możesz tego zrobić!");
    }

    /**
     * metoda podbijam (raise), podbicie zakładu
     * wywołana wysyła do serwera String "raise"
     */
    public void raise() {
        onPlayAudio("click.mp3");
        message.setText(" ");

        try {
            if (Game.client.isTurn) {

                if (Integer.parseInt(raisebox.getText()) - Game.client.selfBet <= Game.client.chips) {

                    if (Integer.parseInt(raisebox.getText()) > Game.client.currentbet) {

                        Game.client.out.println("raise#" + raisebox.getText());
                        Game.client.selfBet = Integer.parseInt(raisebox.getText());
                        raisebox.clear();
                        Game.client.isTurn = false;
                    } else message.setText("Nie możesz postawić mniej, niż aktualna wartość zakładu.");
                } else message.setText("Za mało chipów!");
            } else message.setText("Teraz nie możesz tego zrobić");

        } catch (NumberFormatException e) {
            message.setText("Należy wprowadzić liczby");
        }
    }

    /**
     * metoda fold (pasowanie kart, rezygnacja z danego rozdania)
     * wywołana wysyła do serwera String "fold"
     */
    public void fold() {
        onPlayAudio("click.mp3");
        message.setText(" ");

        if (Game.client.isTurn) {
            Game.client.out.println("fold");
            Game.client.isTurn = false;
        }
    }

    /**
     * metoda pozwalająca na opuszczenie gry.
     * wywołana, wysyła do serwera String "logout"
     */
    public void logout() {
        onPlayAudio("click.mp3");
        Game.client.out.println("logout");
        System.out.println("logout");
        Platform.exit();
        System.exit(0);
    }

    public void onPlayAudio(String file) {
        if (mediaView.getMediaPlayer() == null) {
            try {
                String fileName = getClass().getResource(file).toURI().toString();
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

