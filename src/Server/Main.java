package Server;

import dao.BilardDbImpl;
import dao.DbAdapter;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.BallFactory;
import util.Controller;
import util.StartGame;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {

        BilardDbImpl bilardDbImpl = new BilardDbImpl();
        bilardDbImpl.connect();
        bilardDbImpl.createTables();

        Parent root1= FXMLLoader.load(getClass().getResource("sample.fxml"));
        Group root =  new Group();

        primaryStage.setTitle("Pool_Server");
        Scene scene=new Scene(root,1000,650);
        scene.setFill(Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

        root.getChildren().addAll(root1);


        BallFactory ball =new BallFactory(root,scene);
        StartGame startGame=new StartGame(root);

        ball.AddBallFactory(startGame);

        Controller server=new Controller();
        Controller client=new Controller();

        ball.turn.setTurn(true);
        ball.isFaul=true;
        Server s=new Server(client,server,ball.turn,startGame);


        Timer t=new Timer();

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ball.setVelocityofCueball();
                if(ball.controllstick.getInitial_v()>1){
                    server.setInitial_v(ball.controllstick.getInitial_v());
                    try {
                        sleep(1000);
                        ball.controllstick.setInitial_v(0);
                        server.setInitial_v(0);

                    }catch (Exception e){

                    }
                }
            }
        },0,30);



        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        final long timeStart = System.currentTimeMillis();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.030),
                new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent ae)
                    {
                        if(!ball.isgameOver) {
                            ball.UpdateGameState(scene);
                            ball.line(scene);
                            ball.setDirection();
                            ball.detectAndHandlePocket();

                            if (ball.turn.getTurn()) {
                                startGame.setText("Twoja kolej");
                                server.setValue(ball.controllstick.getValue());
                                server.setAngle(ball.controllstick.getAngle());
                                server.setLineEnlarge(ball.getLineEnlarge());
                            } else {
                                startGame.setText("Kolej przeciwnika");
                                ball.setAngle(client.getAngle());
                                ball.setValue(client.getValue());
                                ball.setLineEnlarge(client.getLineEnlarge());
                                ball.setIni_v(client.getInitial_v());
                            }
                        }
                        else startGame.setText("Game Over!!!");
                    }
                });

        gameLoop.getKeyFrames().add( kf );
        startGame.start(gameLoop);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(!ball.isgameOver) {
                    ball.handleCollisions();
                    ball.detectWallCollision2();
                    ball.detectWallCollision();
                    ball.controllVelocityofCueball();
                    ball.spin();
                }
            }
        }.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
