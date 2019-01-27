package sample;


import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import util.StartGame;
import util.Turn;

import java.net.URL;

public class BallFactory {
    private DrawBall[] ball;
    Text msg;
    boolean indicate;
    public Circle indicator;
    double ang;
    AudioClip start;
    AudioClip ballcolide;
    public ControllCueBall controllstick;
    Group storeroot;
    Scene scene;
    double a = 0;
    double time = 0;
    public Turn turn = new Turn(true);

    Rectangle direction;
    Rectangle direction2;

    double cons;
    boolean flag = true;
    double indicatorx;
    double indicatory;
    boolean isBound = true;
    boolean ismyturn;
    public int myballtype;
    public boolean isMychance = true;
    public boolean isgameOver = false;
    public boolean isplay;
    StartGame text;

    int solidballsize;
    int stripeballsize;

    double ini_v = 0;
    BallStorage ballStorage;
    boolean isSetVelocity1 = false;
    boolean isSetVelocity2 = false;
    public boolean isFaul = true;
    String[] imageName = {"sample\\1.png", "sample\\2.png", "sample\\3.png",
            "sample\\4.png", "sample\\5.png", "sample\\6.png", "sample\\7.png",
            "sample\\8.png", "sample\\9.png", "sample\\10.png", "sample\\11.png",
            "sample\\12.png", "sample\\13.png", "sample\\14.png", "sample\\15.png",
            "sample\\cueball.png",
    };


    public BallFactory(Group root, Scene scene) {

        ballStorage = new BallStorage();
        controllstick = new ControllCueBall(root, scene);

        direction = new Rectangle();
        direction.setWidth(1.7);
        direction.setFill(Color.BROWN);
        direction.getTransforms().add(new Rotate(-90, Rotate.Z_AXIS));
        direction2 = new Rectangle();
        direction2.setWidth(1.7);
        direction2.setFill(Color.BROWN);
        direction2.getTransforms().add(new Rotate(-90, Rotate.Z_AXIS));

        this.scene = scene;
        storeroot = root;
        int c = 0;
        double r = 21;
        double variable;
        ball = new DrawBall[16];

        for (int i = 1; i <= 5; i++) {
            int v = 0;
            for (int j = 1; j <= i; j++) {
                if (c == 0) {
                    variable = 2;
                } else variable = 0;

                if (c < 7) {
                    if (i % 2 == 0) {
                        ball[c] = new DrawBall(root, 700 + r * i, 350 + variable + 10 + r * v, imageName[c], Color.BROWN);
                    } else {
                        ball[c] = new DrawBall(root, 700 + r * i, 350 + variable + r * v, imageName[c], Color.BROWN);
                    }
                } else if (c == 7) {
                    if (i % 2 == 0) {
                        ball[c] = new DrawBall(root, 700 + r * i, 350 + variable + 10 + r * v, imageName[c], Color.BLACK);
                    } else {
                        ball[c] = new DrawBall(root, 700 + r * i, 350 + variable + r * v, imageName[c], Color.BLACK);
                    }
                } else if (c < 15) {
                    if (i % 2 == 0) {
                        ball[c] = new DrawBall(root, 700 + r * i, 350 + variable + 10 + r * v, imageName[c], Color.DARKBLUE);
                    } else {
                        ball[c] = new DrawBall(root, 700 + r * i, 350 + variable + r * v, imageName[c], Color.DARKBLUE);
                    }
                }

                if (v < 0) {
                    v = (v / (-1));
                } else {
                    v = (v + 1) / (-1);
                }
                c++;
            }
        }

        ball[15] = new DrawBall(root, 300, 350, imageName[15], Color.ANTIQUEWHITE);


        for (int i = 0; i < 7; i++) {
            ball[i].setBallid(0);
        }
        ball[7].setBallid(8);

        for (int i = 8; i < 15; i++) {
            ball[i].setBallid(1);
        }
        ball[15].setBallid(10);
        msg = new Text("");
        msg.setFont(Font.font("Times New Roman", FontWeight.THIN, 30));
        msg.setFill(Color.BLACK);
        msg.setLayoutX(320);
        msg.setLayoutY(640);
        msg.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().addAll(direction, msg);
    }


    public boolean isVelocityZero() {
        int i = 0;
        while (ball[i].getV() <= 0.07) {
            i++;
            if (i == 16) {
                return true;
            }
        }
        return false;

    }

    public void AddBallFactory(StartGame text) {
        this.text = text;
    }


    public void UpdateGameState(Scene scene) {

        if (turn.getTurn()) {
            ball[15].sphere.setOnMouseDragged(event ->
            {
                if (isBounded() && isFaul) {
                    flag = false;
                    storeroot.getChildren().remove(controllstick.getStick());
                    storeroot.getChildren().remove(indicator);
                    storeroot.getChildren().remove(controllstick.line);
                    ball[15].sphere.setCursor(Cursor.CLOSED_HAND);
                    ball[15].sphere.setLayoutX(event.getSceneX());
                    ball[15].sphere.setLayoutY(event.getSceneY());
                    isBounded();
                }

                ball[15].sphere.setOnMouseReleased(event1 -> {
                    storeroot.getChildren().remove(controllstick.getStick());
                    storeroot.getChildren().remove(controllstick.line);
                    storeroot.getChildren().remove(indicator);
                    flag = true;
                    isBounded();
                });
            });
        }

        if (flag == true) {
            controllstick = new ControllCueBall(storeroot, scene);
            indicator = new Circle();
            indicator.setRadius(10);
            indicator.setFill(Color.TRANSPARENT);
            indicator.setStroke(Color.AQUA);
            indicator.setStrokeWidth(2);
            indicator.setStrokeType(StrokeType.INSIDE);
            DropShadow d = new DropShadow();
            indicator.setEffect(d);
            storeroot.getChildren().add(indicator);
            Stick();
            flag = false;
            indicator.setLayoutX(ball[15].sphere.getLayoutX());
            indicator.setLayoutY(ball[15].sphere.getLayoutY());
            indicator.setVisible(false);
        }

        if (turn.getTurn()) {
            controllstick.MoveStick(ball[15].sphere.getLayoutX(), ball[15].sphere.getLayoutY());
        }
        if (turn.getTurn()) {
            controllstick.RotateStick(scene, ball[15].sphere.getLayoutX(), ball[15].sphere.getLayoutY());
        }
    }

    public void Stick() {
        controllstick.InitializeStick(ball[15].sphere.getLayoutX(), ball[15].sphere.getLayoutY());
    }


    public void setVelocityofCueball() {
        controllstick.setInitial_v();
        if ((turn.getTurn() && controllstick.getInitial_v() > 1 && !isSetVelocity1)) {
            isFaul = false;
            double angle = (controllstick.getAngle() + 180) % 360;
            ball[15].setAngle(Math.toRadians(angle));
            ball[15].setV(controllstick.getInitial_v());
            ball[15].setVx((ball[15].getV() * Math.cos((ball[15].getAngle()))));
            ball[15].setVy((ball[15].getV() * Math.sin((ball[15].getAngle()))));
            ball[15].setAx((ball[15].getA() * Math.cos((ball[15].getAngle()))));
            ball[15].setAy(ball[15].getA() * Math.sin((ball[15].getAngle())));
            isSetVelocity1 = true;
            solidballsize = ballStorage.solidballsize;
            stripeballsize = ballStorage.stripeballsize;

        }

        if ((!turn.getTurn() && getIni_v() > 1 && !isSetVelocity2)) {
            isFaul = false;
            double angle = (controllstick.getAngle() + 180) % 360;
            ball[15].setAngle(Math.toRadians(angle));
            ball[15].setV(getIni_v());

            ball[15].setVx((ball[15].getV() * Math.cos((ball[15].getAngle()))));
            ball[15].setVy((ball[15].getV() * Math.sin((ball[15].getAngle()))));
            ball[15].setAx((ball[15].getA() * Math.cos((ball[15].getAngle()))));
            ball[15].setAy(ball[15].getA() * Math.sin((ball[15].getAngle())));
            isSetVelocity2 = true;
            solidballsize = ballStorage.solidballsize;
            stripeballsize = ballStorage.stripeballsize;
        }
    }

    public void controllVelocityofCueball() {
        if (isSetVelocity1) {
            for (int i = 0; i < 16; i++) {
                storeroot.getChildren().remove(indicator);
                storeroot.getChildren().remove(controllstick.getStick());
                storeroot.getChildren().remove(controllstick.line);


                ball[i].setV(Math.sqrt(Math.pow(ball[i].getVx(), 2) + Math.pow(ball[i].getVy(), 2)));

                if (ball[i].getV() > 0.07) {
                    ball[i].setVx(ball[i].getVx() - (ball[i].getAx() * ball[i].getTime()));
                    ball[i].setVy(ball[i].getVy() - (ball[i].getAy() * ball[i].getTime()));

                    ball[i].setTime(ball[i].getTime() + .00005);

                    ball[i].sphere.setLayoutX(ball[i].sphere.getLayoutX() + ball[i].getVx());
                    ball[i].sphere.setLayoutY(ball[i].sphere.getLayoutY() + ball[i].getVy());

                }

                if (ball[i].getV() <= 0.07) {
                    ball[i].setTime(0);
                    ball[i].setAngle(0);
                    ball[i].setVx(0);
                    ball[i].setVy(0);
                }
            }
            if (isVelocityZero()) {
                controllstick.setInitial_v(0);
                flag = true;
                isSetVelocity1 = false;
                if (turn.getTurn() && myballtype == 0 && ballStorage.solidballsize == solidballsize) {
                    turn.setTurn(false);
                } else if (turn.getTurn() && myballtype == 1 && ballStorage.stripeballsize == stripeballsize) {
                    turn.setTurn(false);
                } else text.setText("");
            }
        }

        if (isSetVelocity2) {
            for (int i = 0; i < 16; i++) {

                storeroot.getChildren().remove(indicator);
                storeroot.getChildren().remove(controllstick.getStick());
                storeroot.getChildren().remove(controllstick.line);


                ball[i].setV(Math.sqrt(Math.pow(ball[i].getVx(), 2) + Math.pow(ball[i].getVy(), 2)));

                if (ball[i].getV() > 0.07) {
                    ball[i].setVx(ball[i].getVx() - (ball[i].getAx() * ball[i].getTime()));
                    ball[i].setVy(ball[i].getVy() - (ball[i].getAy() * ball[i].getTime()));

                    ball[i].setTime(ball[i].getTime() + .00005);

                    ball[i].sphere.setLayoutX(ball[i].sphere.getLayoutX() + ball[i].getVx());
                    ball[i].sphere.setLayoutY(ball[i].sphere.getLayoutY() + ball[i].getVy());

                }

                if (ball[i].getV() <= 0.07) {
                    ball[i].setTime(0);
                    ball[i].setAngle(0);
                    ball[i].setVx(0);
                    ball[i].setVy(0);
                }
            }
            if (isVelocityZero()) {

                setIni_v(0);
                flag = true;
                isSetVelocity2 = false;

                if (!turn.getTurn() && myballtype == 1 && ballStorage.solidballsize == solidballsize) {
                    turn.setTurn(true);
                } else if (!turn.getTurn() && myballtype == 0 && ballStorage.stripeballsize == stripeballsize) {
                    turn.setTurn(true);
                }

            }
        }
    }

    public void detectWallCollision() {

        for (int i = 0; i < 16; i++) {
            //1//
            if (ball[i].sphere.getLayoutX() >= 125 && ball[i].sphere.getLayoutX() <= 475) {
                if ((ball[i].sphere.getLayoutY() - 150 <= 11)) {
                    ball[i].setVy(-(ball[i].getVy()));
                    ball[i].setAy(-(ball[i].getAy()));
                    UpdateAngle(ball[i]);
                }
            }

            //2//
            if (ball[i].sphere.getLayoutX() >= 525 && ball[i].sphere.getLayoutX() <= 875) {
                if ((ball[i].sphere.getLayoutY() - 150 <= 11)) {
                    ball[i].setVy(-(ball[i].getVy()));
                    ball[i].setAy(-(ball[i].getAy()));
                    UpdateAngle(ball[i]);
                }
            }

            //3//
            if (ball[i].sphere.getLayoutX() >= 125 && ball[i].sphere.getLayoutX() <= 474) {
                if ((550 - ball[i].sphere.getLayoutY()) <= 11) {
                    ball[i].setVy(-(ball[i].getVy()));
                    ball[i].setAy(-(ball[i].getAy()));
                    UpdateAngle(ball[i]);
                }
            }

            //4//
            if (ball[i].sphere.getLayoutX() >= 525 && ball[i].sphere.getLayoutX() <= 875) {
                if ((-ball[i].sphere.getLayoutY() + 550) <= 11) {
                    ball[i].setVy(-(ball[i].getVy()));
                    ball[i].setAy(-(ball[i].getAy()));
                    UpdateAngle(ball[i]);
                }
            }

            //5//

            if (ball[i].sphere.getLayoutY() >= 175 && ball[i].sphere.getLayoutY() <= 525) {
                if ((ball[i].sphere.getLayoutX() - 100) <= 11) {
                    ball[i].setVx(-(ball[i].getVx()));
                    ball[i].setAx(-(ball[i].getAx()));
                    UpdateAngle(ball[i]);
                }
            }

            //6//

            if (ball[i].sphere.getLayoutY() >= 175 && ball[i].sphere.getLayoutY() <= 525) {
                if ((-ball[i].sphere.getLayoutX() + 900) <= 11) {
                    ball[i].setVx(-(ball[i].getVx()));
                    ball[i].setAx(-(ball[i].getAx()));
                    UpdateAngle(ball[i]);
                }
            }
        }
    }


    public void handleCollisions() {
        double xDist, yDist;
        for (int i = 0; i < 16; i++) {
            DrawBall A = ball[i];
            for (int j = i + 1; j < 16; j++) {
                DrawBall B = ball[j];
                xDist = A.sphere.getLayoutX() - B.sphere.getLayoutX();
                yDist = A.sphere.getLayoutY() - B.sphere.getLayoutY();
                double distSquared = xDist * xDist + yDist * yDist;
                if (distSquared <= 20 * 20) {
                    double xVelocity = B.getVx() - A.getVx();
                    double yVelocity = B.getVy() - A.getVy();
                    double dotProduct = xDist * xVelocity + yDist * yVelocity;
                    if (dotProduct > 0) {
                        double collisionScale = dotProduct / distSquared;
                        double xCollision = xDist * collisionScale;
                        double yCollision = yDist * collisionScale;
                        double combinedMass = 10;
                        double collisionWeightA = 1;
                        double collisionWeightB = 1;

                        A.setVx((collisionWeightA * xCollision) + A.getVx());
                        A.setVy(collisionWeightA * yCollision + A.getVy());
                        B.setVx(B.getVx() - collisionWeightB * xCollision);
                        B.setVy(B.getVy() - collisionWeightB * yCollision);

                        A.setAngle(Math.toDegrees(Math.atan2(A.getVy(), A.getVx())));
                        B.setAngle(Math.toDegrees(Math.atan2(B.getVy(), B.getVx())));

                        if (A.getAngle() < 0) {
                            A.setAngle((A.getAngle() + 360));
                        }
                        if (B.getAngle() < 0) {
                            B.setAngle((B.getAngle() + 360));
                        }


                        A.setAx(A.getA() * Math.cos(Math.toRadians(A.getAngle())));
                        A.setAy(A.getA() * Math.sin(Math.toRadians(A.getAngle())));

                        B.setAx(B.getA() * Math.cos(Math.toRadians(B.getAngle())));
                        B.setAy(B.getA() * Math.sin(Math.toRadians(B.getAngle())));


                    }
                }
            }
        }
    }

    public void detectWallCollision2() {

        for (int i = 0; i < 16; i++) {
            ///1///
            if (ball[i].sphere.getLayoutX() >= 98 && ball[i].sphere.getLayoutX() < 125) {
                if (isTouch(15, -20, 1125, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    handleCollisionsInWallPlus(90 - 36.86, ball[i]);
                }
            }

            //2//
            if (ball[i].sphere.getLayoutX() > 475 && ball[i].sphere.getLayoutY() > 135 && ball[i].sphere.getLayoutY() < 162) {
                if (isTouch(-15, -10, 8625, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    handleCollisionsInWallMinus(33.7, ball[i]);

                }
            }

            //3//
            if (ball[i].sphere.getLayoutX() < 525 && ball[i].sphere.getLayoutY() > 135 && ball[i].sphere.getLayoutY() < 162) {
                if (isTouch(15, -10, -6375, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    handleCollisionsInWallPlus(33.7, ball[i]);
                }
            }

            //4//
            if (ball[i].sphere.getLayoutX() > 875 && ball[i].sphere.getLayoutX() < 902) {
                if (isTouch(-15, -20, 16125, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    handleCollisionsInWallMinus(90 - 36.86, ball[i]);
                }
            }

            //5//
            if (ball[i].sphere.getLayoutX() >= 98 && ball[i].sphere.getLayoutX() < 125) {
                if (isTouch(15, 20, -12875, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    handleCollisionsInWallMinus(90 - 36.86, ball[i]);
                }
            }

            //6//
            if (ball[i].sphere.getLayoutX() > 474 && ball[i].sphere.getLayoutY() > 540 && ball[i].sphere.getLayoutY() <= 565) {
                if (isTouch(15, -10, -1625, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    handleCollisionsInWallPlus(33.7, ball[i]);
                }
            }

            //7//
            if (ball[i].sphere.getLayoutX() < 525 && ball[i].sphere.getLayoutY() < 565 && ball[i].sphere.getLayoutY() >= 540) {
                if (isTouch(15, 10, -13375, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    handleCollisionsInWallMinus(33.7, ball[i]);
                }
            }
            //8//
            if (ball[i].sphere.getLayoutX() > 875 && ball[i].sphere.getLayoutX() < 902) {
                if (isTouch(15, -20, -2125, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    handleCollisionsInWallPlus(90 - 36.86, ball[i]);
                }
            }

            //9//
            if (ball[i].sphere.getLayoutY() > 149 && ball[i].sphere.getLayoutY() < 175) {
                if (isTouch(20, -15, 625, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    handleCollisionsInWallMinus1(90 - 36.86, ball[i]);
                }
            }

            //10//
            if (ball[i].sphere.getLayoutY() > 525 && ball[i].sphere.getLayoutY() < 551) {
                if (isTouch(20, 15, -9875, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    handleCollisionsInWallPlus1(90 - 36, ball[i]);
                }
            }

            //11//
            if (ball[i].sphere.getLayoutY() > 149 && ball[i].sphere.getLayoutY() < 175) {
                if (isTouch(20, 15, -20625, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    handleCollisionsInWallPlus1(90 - 36, ball[i]);
                }
            }

            //12//
            if (ball[i].sphere.getLayoutY() > 525 && ball[i].sphere.getLayoutY() < 551) {
                if (isTouch(20, -15, -10125, ball[i].sphere.getLayoutX(), ball[i].sphere.getLayoutY())) {
                    handleCollisionsInWallMinus1(90 - 36.86, ball[i]);
                }
            }
        }
    }


    public void handleCollisionsInWallMinus(double angle, DrawBall a) {

        UpdateAngle(a);
        UpdateAngle(a, -angle);

        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));

        a.setVx(-a.getVx());
        a.setAx(-a.getAx());
        UpdateAngle(a);

        UpdateAngle(a, +angle);

        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));
    }

    public void handleCollisionsInWallPlus(double angle, DrawBall a) {

        UpdateAngle(a);
        UpdateAngle(a, angle);
        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));

        a.setVx(-a.getVx());
        a.setAx(-a.getAx());
        UpdateAngle(a);
        UpdateAngle(a, -angle);

        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));
    }

    public void UpdateAngle(DrawBall ball) {
        double t = Math.toDegrees(Math.atan2(ball.getVy(), ball.getVx()));
        if (t < 0) {
            t += 360;
        }
        ball.setAngle(Math.toRadians(t));
    }

    public void handleCollisionsInWallMinus1(double angle, DrawBall a) {

        UpdateAngle(a);
        UpdateAngle(a, -angle);
        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));

        a.setVy(-a.getVy());
        a.setAy(-a.getAy());
        UpdateAngle(a);

        UpdateAngle(a, +angle);

        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));
    }


    public void handleCollisionsInWallPlus1(double angle, DrawBall a) {

        UpdateAngle(a);

        UpdateAngle(a, angle);


        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));

        a.setVy(-a.getVy());
        a.setAy(-a.getAy());
        UpdateAngle(a);


        UpdateAngle(a, -angle);
        a.setVx((a.getV() * Math.cos((a.getAngle()))));
        a.setVy((a.getV() * Math.sin((a.getAngle()))));
        a.setAx(a.getA() * Math.cos((a.getAngle())));
        a.setAy(a.getA() * Math.sin((a.getAngle())));
    }

    public void UpdateAngle(DrawBall ball, double ang) {
        double t = Math.toDegrees(Math.atan2(ball.getVy(), ball.getVx()));
        t = t + ang;
        if (t < 0) {
            t += 360;
        }
        ball.setAngle(Math.toRadians(t));
    }


    boolean isTouch(double a, double b, double c, double x, double y) {

        double temp = Math.abs(a * x + b * y + c);
        double temp2 = Math.sqrt(a * a + b * b);
        double distance = temp / temp2;

        if (distance <= 10) {
            return true;
        }
        return false;
    }

    public void detectAndHandlePocket() {
        for (int i = 0; i < 16; i++) {
            double distance1 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 85, 2) + Math.pow(ball[i].sphere.getLayoutY() - 135, 2));
            double distance2 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 500, 2) + Math.pow(ball[i].sphere.getLayoutY() - 127, 2));
            double distance3 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 915, 2) + Math.pow(ball[i].sphere.getLayoutY() - 135, 2));
            double distance4 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 85, 2) + Math.pow(ball[i].sphere.getLayoutY() - 565, 2));
            double distance5 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 500, 2) + Math.pow(ball[i].sphere.getLayoutY() - 574, 2));
            double distance6 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 915, 2) + Math.pow(ball[i].sphere.getLayoutY() - 565, 2));

            if (distance1 < 20 - 2 || distance2 < 20 - 2 || distance3 < 20 - 2 || distance4 < 20 - 2 || distance5 < 20 - 2 || distance6 < 20 - 2) {
                if (turn.getTurn() && ballStorage.solidballsize == 0 && ballStorage.stripeballsize == 0) {
                    if (i < 7) {
                        myballtype = 0;
                    } else if (i > 7 && i < 15) {
                        myballtype = 1;
                    }
                }

                if (!turn.getTurn() && ballStorage.solidballsize == 0 && ballStorage.stripeballsize == 0) {
                    if (i < 7) {
                        myballtype = 1;
                    } else if (i > 7 && i < 15) {
                        myballtype = 0;
                    }
                }

                if (turn.getTurn() && i < 7) {
                    ballStorage.pushSolidball(ball[i]);
                    isFaul = false;
                    if (myballtype == 1) {
                        isplay = true;
                        turn.setTurn(false);
                    }
                } else if (!turn.getTurn() && i < 7) {
                    ballStorage.pushSolidball(ball[i]);
                    if (myballtype == 0) {
                        isplay = true;
                        turn.setTurn(true);
                    } else turn.setTurn(false);
                } else if (turn.getTurn() && i == 15) {
                    ball[15].setVx(0);
                    ball[15].setVy(0);
                    ball[15].setV(0);
                    ball[15].sphere.setLayoutX(500);
                    ball[15].sphere.setLayoutY(350);
                    isFaul = false;
                    turn.setTurn(false);
                } else if (!turn.getTurn() && i == 15) {
                    ball[15].setVx(0);
                    ball[15].setVy(0);
                    ball[15].setV(0);
                    ball[15].sphere.setLayoutX(500);
                    ball[15].sphere.setLayoutY(350);
                    isFaul = true;
                    turn.setTurn(true);
                } else if (turn.getTurn() && i == 7) {
                    if (myballtype == 0 && ballStorage.solidballsize < 7) {
                        ballStorage.pushSolidball(ball[7]);
                        msg.setText("Przegrana");
                        isgameOver = true;
                    } else if (myballtype == 1 && ballStorage.stripeballsize < 7) {
                        ballStorage.pushStripeball(ball[7]);
                        msg.setText("Przegrana");
                        isgameOver = true;
                    }
                } else if (!turn.getTurn() && i == 7) {
                    if (myballtype == 0 && ballStorage.solidballsize < 7) {
                        ballStorage.pushSolidball(ball[7]);
                        msg.setText("Wygrana");
                        isgameOver = true;
                    } else if (myballtype == 1 && ballStorage.stripeballsize < 7) {
                        ballStorage.pushStripeball(ball[7]);
                        msg.setText("Wygrana");
                        isgameOver = true;
                    }
                } else if (turn.getTurn() && myballtype == 0 && ballStorage.solidballsize == 7) {
                    ballStorage.pushSolidball(ball[7]);
                    msg.setText("Wygrana");
                    isgameOver = true;
                } else if (turn.getTurn() && myballtype == 1 && ballStorage.stripeballsize == 7) {
                    ballStorage.pushStripeball(ball[7]);
                    msg.setText("Wygrana");
                    isgameOver = true;
                } else if (!turn.getTurn() && myballtype == 0 && ballStorage.solidballsize == 7) {
                    ballStorage.pushSolidball(ball[7]);
                    msg.setText("Wygrana");
                    isgameOver = true;
                } else if (!turn.getTurn() && myballtype == 1 && ballStorage.stripeballsize == 7) {
                    ballStorage.pushStripeball(ball[7]);
                    msg.setText("Wygrana");
                    isgameOver = true;
                } else if (turn.getTurn() && i < 15) {
                    ballStorage.pushStripeball(ball[i]);
                    if (myballtype == 0) {
                        isplay = true;
                        turn.setTurn(false);
                        isFaul = false;
                    }
                } else if (!turn.getTurn() && i < 15) {
                    ballStorage.pushStripeball(ball[i]);
                    if (myballtype == 1) {
                        isplay = true;
                        turn.setTurn(true);
                        isFaul = true;
                    } else turn.setTurn(false);
                }
            }
        }
    }

    public void line(Scene scene) {
        indicator.setLayoutX(ball[15].sphere.getLayoutX() + controllstick.lineEnlarge * Math.cos(Math.toRadians((controllstick.getAngle() + 180) % 360)));
        indicator.setLayoutY(ball[15].sphere.getLayoutY() + controllstick.lineEnlarge * Math.sin(Math.toRadians((controllstick.getAngle() + 180) % 360)));

        controllstick.line.setLayoutX(ball[15].sphere.getLayoutX());
        controllstick.line.setLayoutY(ball[15].sphere.getLayoutY());
    }


    boolean isTouch2() {
        int i = 0;
        while (i < 16) {
            double xDist = ball[i].sphere.getLayoutX() - indicator.getLayoutX();
            double yDist = ball[i].sphere.getLayoutY() - indicator.getLayoutY();
            double dis = Math.sqrt(xDist * xDist + yDist * yDist);
            if (dis <= (indicator.getRadius() + ball[i].sphere.getRadius()) && (dis + 2) > (indicator.getRadius() + ball[i].sphere.getRadius())) {
                ang = Math.toDegrees(Math.atan2(yDist, xDist));
                if (ang < 0) {
                    ang += 360;
                }
                return true;
            } else {
                i++;

            }
        }

        return false;
    }

    public void setDirection() {
        if (isTouch2()) {
            if (indicator != null) {
                direction.setLayoutX(indicator.getLayoutX());
                direction.setLayoutY(indicator.getLayoutY());
                direction.setHeight(100);
                direction.getTransforms().add(new Rotate((ang - a), Rotate.Z_AXIS));
                a = ang;
            }
        } else {
            direction.setLayoutX(0);
            direction.setLayoutY(0);
            direction.setHeight(0);
        }
    }

    public void setLineEnlarge(double line) {
        controllstick.lineEnlarge = line;
        controllstick.line.setHeight(line);
    }

    public double getLineEnlarge() {
        return controllstick.lineEnlarge;
    }


    public void spin() {
        for (int i = 0; i < 16; i++) {

            Rotate rx = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
            Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
            rx.setAngle(Math.toDegrees(ball[i].getVy() / 10));
            ry.setAngle(-Math.toDegrees(ball[i].getVx() / 10));
            ball[i].sphere.getTransforms().addAll(rx, ry);
        }
    }


    boolean isBounded() {

        if (ball[15].sphere.getLayoutX() > 100 + 10 && ball[15].sphere.getLayoutY() > 160 && ball[15].sphere.getLayoutX() < 900 - 10 &&
                ball[15].sphere.getLayoutY() < 550 - 10) {
            isBound = true;
            return isBound;
        } else {
            isBound = false;
            if (ball[15].sphere.getLayoutX() <= 110) {
                ball[15].sphere.setLayoutX(112);

            }
            if (ball[15].sphere.getLayoutY() <= 160) {
                ball[15].sphere.setLayoutY(162);
            }
            if (ball[15].sphere.getLayoutX() >= 890) {
                ball[15].sphere.setLayoutX(888);
            }

            if (ball[15].sphere.getLayoutY() >= 540) {
                ball[15].sphere.setLayoutY(538);
            }
            return isBound;
        }
    }


    public void setValue(double value) {
        controllstick.setValue(value);
    }

    public void setAngle(double angle) {
        controllstick.setAngle(angle);
        controllstick.setRotateStick(angle);
        controllstick.MoveStick(ball[15].sphere.getLayoutX(), ball[15].sphere.getLayoutY());
    }

    public void setLayout(double x, double y) {
        ball[15].sphere.setLayoutX(x);
        ball[15].sphere.setLayoutY(y);
    }

    public void setInitial_v(double v) {
        controllstick.setInitial_v(v);
    }

    public void setIni_v(double initial_v) {
        ini_v = initial_v;
    }

    public double getIni_v() {
        return ini_v;
    }

    public double getLayoutx() {
        return ball[15].sphere.getLayoutX();
    }

    public double getLayouty() {
        return ball[15].sphere.getLayoutY();
    }

    public void setIsmyturn(boolean ismyturn) {
        this.ismyturn = ismyturn;
    }

    public boolean ismyturn() {
        return ismyturn;
    }
}