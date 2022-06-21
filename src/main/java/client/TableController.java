package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class TableController{

    @FXML public TextField p1;
    @FXML public TextField p2;
    @FXML public TextField p3;
    @FXML public TextField p4;
    @FXML public TextField p5;
    @FXML public TextField c1;
    @FXML public TextField c2;
    @FXML public TextField c3;
    @FXML public TextField c4;
    @FXML public TextField c5;
    @FXML private TextField raisebox;
    @FXML public TextField whichPturn;

    @FXML public  ImageView card1;
    @FXML public  ImageView card2;

    public Circle circle;
    public Circle circle2;
    public Circle circle3;
    public Circle circle4;
    public Circle circle5;
    public Circle pot1;
    public Circle pot2;
    public Circle pot3;
    public Circle pot4;
    public Circle pot5;
    public Circle pot6;
    public Circle avatar1;
    public Circle avatar2;
    public Circle avatar3;
    public Circle avatar4;
    public Circle avatar5;

    @FXML public ImageView cc1;
    @FXML public ImageView cc2;
    @FXML public ImageView cc3;
    @FXML public ImageView cc4;
    @FXML public ImageView cc5;
    @FXML public ImageView card21;
    @FXML public ImageView card22;
    @FXML public ImageView card31;
    @FXML public ImageView card32;
    @FXML public ImageView card41;
    @FXML public ImageView card42;
    @FXML public ImageView card51;
    @FXML public ImageView card52;

    @FXML public Text bet;
    @FXML public Text action;
    @FXML public Text pot;
    @FXML public Text message;
    @FXML public Text action2;
    @FXML public Text action3;
    @FXML public Text action4;
    @FXML public Text action5;

    public void call(){

          message.setText(" ");

          if(main.client.isTurn){
              if(main.client.currentbet - main.client.selfBet<= main.client.chips){
                  if(main.client.currentbet != main.client.selfBet){

                      main.client.out.println("call");
                      main.client.selfBet = main.client.currentbet;
                      main.client.isTurn = false;
                  }
                  else message.setText("Nie moższ teraz sprawdzić. Wybierz Podbij lub Rzucam.");
              }
              else {
                  message.setText("Musisz rzucić karty!"); // today

                  main.client.out.println("fold");
                  main.client.isTurn = false;
                //  card1.setImage(new Image("resources/download.jpg"));
                 // card2.setImage(new Image("resources/download.jpg"));
              }
          }
          else message.setText("Nie Twoja kolej!");
      }

    public void check(){

        message.setText(" ");

          if(main.client.isTurn){
              if(main.client.currentbet == main.client.selfBet){
                  main.client.out.println("check");
                  main.client.isTurn = false;
              }
              else message.setText("Musisz sprawdzić aktualny zakład!");
          }
          else message.setText("Nie Twoja kolej!");
    }

    public void raise() {

        message.setText(" ");

        try{
            if (main.client.isTurn) {

                if (Integer.parseInt(raisebox.getText()) - main.client.selfBet <= main.client.chips) {

                    if(Integer.parseInt(raisebox.getText()) > main.client.currentbet){

                        main.client.out.println("raise#" + raisebox.getText());
                        main.client.selfBet = Integer.parseInt(raisebox.getText());
                        raisebox.clear();
                        main.client.isTurn = false;
                    }
                    else message.setText("Nie możesz postawić mniej, niż aktualna wartość zakładu.");
                }
                else message.setText("Za mało chipów!");
            }
            else message.setText("Nie Twoja kolej!");

        }catch (NumberFormatException e){
            message.setText("Należy wprowadzić liczby");
        }
    }

    public void fold(){

        message.setText(" ");

        if(main.client.isTurn){
            main.client.out.println("fold");
            main.client.isTurn = false;

           // card1.setImage(new Image("resources/download.jpg"));
           // card2.setImage(new Image("resources/download.jpg"));
        }
    }

    public void logout(){

        if(main.client.canLogout){
            main.client.out.println("logout");
            Platform.exit();
            System.exit(0);
        }

    }

}