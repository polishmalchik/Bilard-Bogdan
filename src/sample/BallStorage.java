package sample;

public class BallStorage {
    double solidballx;
    double solidbally;
    double stripeballx;
    double stripbally;
    int solidballsize;
    int stripeballsize;

    BallStorage(){
        solidballx=70+50+3;
        solidbally=626;

        stripbally=626;
        stripeballx=670+50+3;
        solidballsize=0;
        stripeballsize=0;
    }

    public void pushSolidball(DrawBall ball){
        ball.setVx(0);
        ball.setVy(0);
        ball.sphere.setLayoutX(solidballx);
        ball.sphere.setLayoutY(solidbally);
        solidballx+=20;
        solidballsize++;
    }


    public void pushStripeball(DrawBall ball){
        ball.setVx(0);
        ball.setVy(0);
        ball.sphere.setLayoutX(stripeballx);
        ball.sphere.setLayoutY(stripbally);
        stripeballx+=20;
        stripeballsize++;
    }

}