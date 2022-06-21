package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Klasa kontrolera gry
 * @author K.Woelke
 */
public class TableController {

    @FXML
    public TextField p1;
    @FXML
    public TextField p2;
    @FXML
    public TextField c1;
    @FXML
    public TextField c2;
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

    /**
     * metoda (call) pozwalająca graczowi na sprawdzenie
     * podbicia po jednym zakładzie i jednym lub kilku podbiciach
     * wywołana, wysyła do serwera String "call"
     */
    public void call() {

        message.setText(" ");

        if (main.client.isTurn) {
            if (main.client.currentbet - main.client.selfBet <= main.client.chips) {
                if (main.client.currentbet != main.client.selfBet) {

                    main.client.out.println("call");
                    main.client.selfBet = main.client.currentbet;
                    main.client.isTurn = false;
                } else message.setText("Nie moższ teraz sprawdzić. Wybierz Podbij lub Rzucam.");
            } else {
                message.setText("Musisz rzucić karty!");

                main.client.out.println("fold");
                main.client.isTurn = false;
            }
        } else message.setText("Nie Twoja kolej!");
    }

    /**
     * metoda (check) "czekam" - przekazanie ruchu do drugiego gracza
     * bez wnoszenia zakładu.
     * wywołana wysyła do serwera String "check"
     */
    public void check() {

        message.setText(" ");

        if (main.client.isTurn) {
            if (main.client.currentbet == main.client.selfBet) {
                main.client.out.println("check");
                main.client.isTurn = false;
            } else message.setText("Musisz sprawdzić aktualny zakład!");
        } else message.setText("Nie Twoja kolej!");
    }

    /**
     * metoda podbijam (raise), podbicie zakładu
     * wywołana wysyła do serwera String "raise"
     */
    public void raise() {

        message.setText(" ");

        try {
            if (main.client.isTurn) {

                if (Integer.parseInt(raisebox.getText()) - main.client.selfBet <= main.client.chips) {

                    if (Integer.parseInt(raisebox.getText()) > main.client.currentbet) {

                        main.client.out.println("raise#" + raisebox.getText());
                        main.client.selfBet = Integer.parseInt(raisebox.getText());
                        raisebox.clear();
                        main.client.isTurn = false;
                    } else message.setText("Nie możesz postawić mniej, niż aktualna wartość zakładu.");
                } else message.setText("Za mało chipów!");
            } else message.setText("Nie Twoja kolej!");

        } catch (NumberFormatException e) {
            message.setText("Należy wprowadzić liczby");
        }
    }

    /**
     * metoda fold (pasowanie kart, rezygnacja z danego rozdania)
     * wywołana wysyła do serwera String "fold"
     */
    public void fold() {

        message.setText(" ");

        if (main.client.isTurn) {
            main.client.out.println("fold");
            main.client.isTurn = false;
        }
    }

    /**
     * metoda pozwalająca na opuszczenie gry.
     * wywołana, wysyła do serwera String "logout"
     */
    public void logout() {

        main.client.out.println("logout");
        System.out.println("logout");
        Platform.exit();
        System.exit(0);
    }

}

