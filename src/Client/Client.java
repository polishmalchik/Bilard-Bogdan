package Client;

import sample.BallFactory;
import util.*;


public class Client implements Runnable{
    String id;
    String opid;
    Thread t;
    Controller c;
    Controller s;
    BallFactory ball;
    Turn turn;
    StartGame startGame;
   public static NetworkUtil nc;

   public Client(Controller c, Controller s,Turn turn1,StartGame str) {
        this.c=c;
        this.s=s;
        this.turn=turn1;
        t=new Thread(this);
        t.start();
       startGame=str;
    }

     @Override
        public void run() {
        {
                String serverAddress = "127.0.0.1";
                int serverPort = 8090;
                nc = new NetworkUtil(serverAddress, serverPort);

            while(opid==null){
                opid=(String)nc.read();
            }

            while(id==null) {
                id = startGame.getUserid();
                nc.write(id);
            }

                new ReadThread(nc, s,turn);
                new WriteThread(nc, "Client", c);


            while (true);
            }

        }
}

