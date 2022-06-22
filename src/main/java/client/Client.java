package client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa clienta
 * Realizuje połączenie z serwerem gry
 * @author K.Woelke
 */
public class Client{

    public Socket socket;
    private BufferedReader in;
    public PrintWriter out;
    public String username;
    public int chips;
    public int selfBet;
    public int currentbet;

    private boolean isLoggedIn;
    public boolean isTurn;
    public boolean canLogout;

    private LoginController lc;
    private SignupController sc;
    private TableController tc;
    private WaitController wc;
    public List<Player> players;



    Client(){
        isLoggedIn = false;
        isTurn = false;
        canLogout = true;

        try {
            socket = new Socket("171.25.230.60",31001);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(()->receive(),"client receive thread").start();
    }

    /**
     * metoda receive, odczyt danych z serwera
     * @Throws Exeception
     */
    private void receive() {

        while(true){
            try {
                String data = in.readLine();
                System.out.println(data);
                Platform.runLater(
                        ()-> parseData(data)
                );


            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * @param data
     * metoda parsująca dane z serwera.
     */
    private void parseData(String data){

        String [] message = data.split("#");

        switch (message[0]){

            case "login done":
                username = message[1];
                chips = Integer.parseInt(message[2]);

                players = new ArrayList<>();
                players.add(new Player(username,chips));

                openTable();
                tc.message.setText("Oczekiwanie na gracza...");
                break;

            case "wait":
                openWait();
                break;

            case "opponentAdded":
                players.add(new Player(message[1],Integer.parseInt(message[2])));

                if(players.size()==2) {

                    tc.p2.setText(message[1]);
                    tc.c2.setText(String.valueOf(message[2]));
                    tc.circle2.setVisible(true);
                    tc.circle2.setFill(new ImagePattern(new Image("blackChip2.jpg")));
                    tc.avatar2.setFill(new ImagePattern(new Image("face3.jpg")));
                }
                break;

            case "card":
                tc.message.setText(" ");
                tc.card1.setImage(new Image("" + message[1] + ".png"));
                tc.card2.setImage(new Image("" + message[2] + ".png"));
                break;

            case "tokens":
                for(int i=0; i<players.size(); i++){

                    if(message[1].equals(players.get(i).username)){

                        if(i==0){
                            tc.c1.setText(message[2]);
                            chips = Integer.parseInt(message[2]);
                        }

                        if(i==1) tc.c2.setText(message[2]);
                    }
                }
                break;

            case "currentbet":
                currentbet = Integer.parseInt(message[1]);
                tc.bet.setText(message[1]);
                break;

            case "selfbet":
                selfBet = Integer.parseInt(message[1]);
                break;

            case "pot":
                tc.pot.setText(message[1]);
                break;

            case "flop":
                tc.cc1.setImage(new Image("" + message[1] + ".png"));
                tc.cc2.setImage(new Image("" + message[2] + ".png"));
                tc.cc3.setImage(new Image("" + message[3] + ".png"));
                break;

            case "turn":
                tc.cc4.setImage(new Image("" + message[1] + ".png"));
                break;

            case "river":
                tc.cc5.setImage(new Image("" + message[1] + ".png"));
                break;

            case "whichPturn":
                for(Player player: players){
                    if(message[1].equals(player.username)){
                        if(message[1].equals(username)){
                            isTurn = true;
                        }
                        tc.whichPturn.setText(message[1]+" - teraz Twoja kolej!!");
                    }
                }
                break;

            case "winner":
                tc.action.setText(message[1]);
                break;

            case "round":
                tc.action.setText(message[1]);
                break;

            case "move":
                if(tc.p2.getText().equals(message[1])) {
                    tc.action2.setText(message[2]);
                    System.out.println(message[2] + " " + tc.p2.getText());
                }

                if(message[2].equals("Fold")){
                    for(int i=0; i<players.size(); i++){
                        if(players.get(i).username.equals(message[1])){
                            players.remove(i);
                        }
                    }
                }
                break;

            case "cardReset":
                tc.card1.setImage(new Image("download.jpg"));
                tc.card2.setImage(new Image("download.jpg"));
                tc.card21.setImage(new Image("download.jpg"));
                tc.card22.setImage(new Image("download.jpg"));
                tc.cc1.setImage(new Image("download.jpg"));
                tc.cc2.setImage(new Image("download.jpg"));
                tc.cc3.setImage(new Image("download.jpg"));
                tc.cc4.setImage(new Image("download.jpg"));
                tc.cc5.setImage(new Image("download.jpg"));
                tc.action.setText(" ");

                players = new ArrayList<>();
                players.add(new Player(username,chips));
                break;

            case "cardshow":
                for(int i = 0;i<players.size();i++){

                    if(message[1].equals(username))continue;

                    if(tc.p2.getText().equals(message[1])){
                        tc.card21.setImage(new Image("" + message[2] + ".png"));
                        tc.card22.setImage(new Image("" + message[3] + ".png"));
                    }
                }
                break;

            case "removeaction":
                tc.action2.setText(" ");
                break;

            case "logout":
                for(int i=0; i<players.size(); i++){
                    if(players.get(i).username.equals(message[1])){
                        players.remove(i);
                        break;
                    }
                }

                if(tc.p2.getText().equals(message[1])) {
                    tc.p2.setText("Brak");
                    tc.c2.setText("-");
                    tc.avatar2.setFill(null);
                    tc.action2.setText(" ");
                }
                break;

            case "sleep":
                canLogout=false;
                break;

            case "alive":
                canLogout=true;
                break;
        }
    }

    /**
     * metoda wait - wykorzystana do uruchomienia
     * poczekalni dla graczy czekających w kolejce
     * Wyświetlona jest nowa scena zdefiniowana w wait.fxml
     */
    private void openWait(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("wait.fxml"));
            Parent root = loader.load();

            WaitController wc = loader.getController();
            setWaitController(wc);

            Game.stage.close();
            Game.stage.setTitle("POCZEKALNIA");
            Game.stage.setScene(new Scene(root));
            Game.stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.println("error in loading wait");
        }
    }

    /**
     * metoda uruchamia głowną plaszę gry (Stół)
     * wygląd zdefiniowano w pliku table.fxml
     * @Throw Exception
     */
    private void openTable(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"));
            Parent root = loader.load();

            TableController tc = loader.getController();
            setTableController(tc);

            Game.stage.close();
            Game.stage.setTitle("Rozgrywka");
            Game.stage.setScene(new Scene(root));
            Game.stage.show();


            tc.p1.setText(username);
            tc.c1.setText(String.valueOf(chips));

            tc.circle.setFill(new ImagePattern(new Image("blackChip2.jpg")));
            tc.pot1.setFill(new ImagePattern(new Image("redChip.png")));
            tc.pot2.setFill(new ImagePattern(new Image("redChip.png")));
            tc.pot3.setFill(new ImagePattern(new Image("redChip.png")));
            tc.pot4.setFill(new ImagePattern(new Image("redChip.png")));
            tc.pot5.setFill(new ImagePattern(new Image("redChip.png")));
            tc.pot6.setFill(new ImagePattern(new Image("redChip.png")));
            tc.avatar1.setFill(new ImagePattern(new Image("face2.jpg")));
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.println("error in loading table");
        }
    }

    /**
     * @param lc LoginController
     */
    public void setLogInController(LoginController lc) {
        this.lc = lc;
    }

    /**
     * @param sc SignupController
     */
    public void setSignUpController(SignupController sc) {
        this.sc = sc;
    }

    /**
     * @param tc TableController
     */
    public void setTableController(TableController tc){
        this.tc = tc;
    }

    /**
     * @param wc WaitController
     */
    public void setWaitController(WaitController wc){
        this.wc = wc;
    }
}





