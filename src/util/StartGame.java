package util;

import dao.BilardDbImpl;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class StartGame {
  public   Button gameplay;
    public Button gamepause;
    Button start;
   public TextField text;
    Image image1;
    Image image2;
    Rectangle r;
    Rectangle r1;
    TextField name;
    TextField id;
    Rectangle gamewindow;
    String username;
    String userid;
    Image backg;

    public  StartGame(Group root){
        backg=new Image("sample\\pool.jpg");
        ImageView view=new ImageView(backg);
        view.setFitHeight(650);
        view.setFitWidth(1000);

        gameplay =new Button();
        gameplay.setText("Wciśnij Start i poczekaj na drugiego gracza");
        gameplay.setLayoutX(0);
        gameplay.setLayoutY(0);
        gameplay.setPrefWidth(300);
        gamepause =new Button();
        gamepause.setText("Pauza");
        gamepause.setLayoutX(530);
        gamepause.setLayoutY(2);
        gamepause.setVisible(false);
        text=new TextField();
        text.setEditable(false);
        text.setLayoutX(300);
        text.setLayoutY(40);
        text.setPrefWidth(400);
        text.setPrefHeight(50);
        text.setAlignment(Pos.CENTER);
        text.setFont(Font.font(20));
        text.setText("Witam w bilardzie Andrzeja");
        Paint value0 = Paint.valueOf("#FFFFFF");
        TextField colorBox0;
        text.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
        r=new Rectangle(20,5,145,85);
        r1=new Rectangle(820,3,150,88);
        r1.setVisible(false);
        r.setVisible(false);

        //gamewindow=new Rectangle(0,0,1000,650);

        BilardDbImpl bilardDbImpl = new BilardDbImpl();
        bilardDbImpl.connect();
        bilardDbImpl.createTables();



        start=new Button("Start");
        start.setLayoutX(510);
        start.setLayoutY(510);
        start.setFont(Font.font(20));
        name=new TextField();
        name.setFont(Font.font(18));
        name.promptTextProperty().setValue("Podaj Imie");
        name.setLayoutX(430);
        name.setLayoutY(300);
        id=new TextField();
        id.promptTextProperty().setValue("");
        id.setLayoutX(420);
        id.setLayoutY(300);
        id.setFont(Font.font(18));
        root.getChildren().addAll(view,name,id,start);
        id.setVisible(false);





        start.setOnMouseClicked(event -> {
            String usname = name.getText();
            bilardDbImpl.setup(usname);
            userid=id.getText();

            root.getChildren().removeAll(name,id,start,view);
            root.getChildren().addAll(gameplay,gamepause,text,r,r1);
        });
    }


    public void  start(Timeline game){
        gameplay.setOnMouseClicked(event -> {
            game.play();
        });
        gamepause.setOnMouseClicked(event -> {
            game.pause();
        });
    }

    public void setText(String tex){
        text.setText(tex);
    }



    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }
}
