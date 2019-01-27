package sample;


import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class ControllCueBall {
    Polygon stick;

    double lineEnlarge=250;
    double angle;
    double a=0;
    double b=180;
    double x=15;
    Group root;
    public double Initial_v;
    double value=0;
    boolean flagAngle=true;
    CreateScrollbar scrollbar;
    Rectangle sc;
    public Rectangle line;
    public double lineangle;

    public  ControllCueBall(){
    }

    public ControllCueBall(Group root,Scene scene)
    {
        this.root=root;
        stick=new Polygon(0,0,0+x,0,0+x,-2.2,25+x,-3,26+x,-3,200+x,-4,202+x,-4.1,260+x,-5,260+x,5,202+x,4.1,
                202+x, -4.1,200+x,-4,200+x,4,26+x,3,26+x,-3,25+x,-3,25+x,3,0+x,2.2,0+x,0);
        stick.setFill(Color.BROWN);
        scrollbar=new CreateScrollbar(root);
        sc=scrollbar.getSc();

        line=new Rectangle();
        line.setWidth(1.8);
        line.setHeight(lineEnlarge);
        line.setFill(Color.BROWN);
        line.setVisible(false);
        DropShadow dropShadow=new DropShadow();
        line.setEffect(dropShadow);
        line.getTransforms().addAll(new Rotate(90,Rotate.Z_AXIS));


        scene.setOnKeyTyped(event -> {
            if(event.getCharacter().equals("a")){
                lineEnlarge+=.5;
                line.setHeight(lineEnlarge);
            }
            if(event.getCharacter().equals("s")){
                lineEnlarge-=.5;
                line.setHeight(lineEnlarge);
            }
            if(event.getCharacter().equals("q")){
                lineEnlarge+=5;
                line.setHeight(lineEnlarge);
            }
            if(event.getCharacter().equals("w")){
                lineEnlarge-=5;
                line.setHeight(lineEnlarge);
            }
        });
        root.getChildren().addAll(stick,line);
    }


    void InitializeStick(double middleX, double middleY ){
        stick.setLayoutX(middleX);
        stick.setLayoutY(middleY);
        line.setLayoutX(middleX);
        line.setLayoutY(middleY);
    }


    public void RotateStick(Scene scene,double pivotx,double pivoty){

        if(flagAngle==true) {
            stick.setOnMouseDragged(event -> {
                stick.setCursor(Cursor.CLOSED_HAND);
                angle = Math.toDegrees(Math.atan2((event.getSceneY() - pivoty), (event.getSceneX() - pivotx)));
                if (angle < 0) {
                    angle += 360;
                }
                lineangle=angle+180;
                lineangle=lineangle%360;
                stick.getTransforms().addAll(new Rotate(angle - a, Rotate.Z_AXIS));

                line.getTransforms().addAll(new Rotate(lineangle-b,Rotate.Z_AXIS));
                a = angle;
                b=lineangle;

            });
        }
    }

    public  void MoveStick(double pivotx,double pivoty) {
        stick.setLayoutX(pivotx +value * Math.cos(Math.toRadians(angle)));
        stick.setLayoutY(pivoty + value * Math.sin(Math.toRadians(angle)));
    }

    public void setInitial_v(double initial_v) {
        Initial_v = initial_v;
    }

    public double getValue() {
        return value;
    }

    public Polygon getStick() {
        return stick;
    }

    public double getAngle() {
        return angle;
    }


    public void setInitial_v(){
        sc.setOnMouseDragged(event -> {
            if((event.getSceneY()-250)<200 && (event.getSceneY()-250)>0) {
                sc.setLayoutY(event.getSceneY());
                value = event.getSceneY() - 250;
                value=value*.5;
            }
        });
        sc.setOnMouseReleased(event -> {
            Initial_v=value/7.5;
            value=0;
            sc.setLayoutY(250);
        });
    }


    public void setValue(double value) {
        this.value = value;
    }

    public void setRotateStick(Double ang){
        lineangle=(angle+180)%360;
        stick.getTransforms().addAll(new Rotate(angle - a, Rotate.Z_AXIS));
        line.getTransforms().addAll(new Rotate(lineangle- b, Rotate.Z_AXIS));
        a = angle;
        b=lineangle;
    }

    public void setMoveStick(double pivotx,double pivoty){
        stick.setLayoutX(pivotx +value * Math.cos(Math.toRadians(angle)));
        stick.setLayoutY(pivoty + value * Math.sin(Math.toRadians(angle)));
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }


    public double getInitial_v() {
        return Initial_v;
    }
}