package sample;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import static javafx.scene.paint.Color.WHITE;

public class DrawBall {
    public  Sphere sphere =new Sphere(10,40);
    private double v;
    private double vx;
    private double vy;
    private double angle;
    private double time;
    private double ax;
    private double ay;
    private double a=10;
    int ballid;
    DrawBall(){

    }

    DrawBall(Group root, double x, double y, String imageName, Color color) {

        sphere.setLayoutX(x);
        sphere.setLayoutY(y);
        sphere.setRadius(10);

        Image myimage = new Image(imageName);

        PhongMaterial material = new PhongMaterial();
        material.setSpecularColor(WHITE);
        if (color == Color.BLACK) {
            material.setDiffuseColor(color);
        }

        material.setDiffuseMap(myimage);
        material.setSpecularPower(30);
        sphere.setMaterial(material);

        root.getChildren().addAll(sphere);


        DropShadow d=new DropShadow();
        d.setSpread(40);
        d.setOffsetX(10);
        d.setOffsetY(10);
        sphere.setEffect(d);

         double vx=0;
         double vy=0;
         double angle=0;
         double time=0;
         double a=7;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getAngle() {
        return angle;
    }

    public double getTime() {
        return time;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public double getAx() {
        return ax;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public double getAy() {
        return ay;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getA() {
        return a;
    }

    public void setV(double v) {
        this.v = v;
    }

    public double getV() {
        return v;
    }

    public void setBallid(int ballid) {
        this.ballid = ballid;
    }

    public int getBallid() {
        return ballid;
    }
}
