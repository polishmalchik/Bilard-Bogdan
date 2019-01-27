package util;

import sample.DrawBall;

import java.io.Serializable;

public class Controller  implements Serializable ,Cloneable {

    DrawBall[] ball=new DrawBall[16];
    double value;
    double angle;
    double layoutx;
    double layouty;
    double Initial_v;
    boolean isFaul;
    double lineEnlarge;
    public  boolean isMouseDraged;
    public boolean isMouseRealesed;

    public Controller(){

    }


   public Controller(double value,double angle,double layoutx,double layouty){
        this.value=value;
        this.angle=angle;
        this.layoutx=layoutx;
        this.layouty=layouty;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setLayoutx(double layoutx) {
        this.layoutx = layoutx;
    }

    public void setLayouty(double layouty) {
        this.layouty = layouty;
    }

    public void setInitial_v(double v){
        Initial_v=v;
    }

    public synchronized  double getAngle() {
        return angle;
    }

    public synchronized  double getValue() {
        return value;
    }

    public double getLayoutx() {
        return layoutx;
    }

    public double getLayouty() {
        return layouty;
    }

    public synchronized  double getInitial_v() {
        return Initial_v;
    }


    public boolean isFaul() {
        return isFaul;
    }


    public void setFaul(boolean faul) {
        isFaul = faul;
    }




    public void setLineEnlarge(double lineEnlarge) {
        this.lineEnlarge = lineEnlarge;
    }
    public double getLineEnlarge() {
        return lineEnlarge;
    }

}
