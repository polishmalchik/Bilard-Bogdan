package util;

import sample.BallFactory;

public class TurnWriterThread  implements Runnable{
    Turn turn;
    NetworkUtil conn;
    Thread th;
    String s="Hellow";
    BallFactory ball;
    public TurnWriterThread(NetworkUtil conn, BallFactory ball)
    {
        this.ball=ball;
        this.conn=conn;
        this.th=new Thread(this);
        th.start();
    }

    @Override
    public void run() {

                while (true) {
                    try {
                        if (!ball.isplay) {
                            conn.write("off");
                        } else conn.write("on");
                    } catch (Exception e) {

                    }
                }
    }
}