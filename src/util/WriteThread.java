
package util;

public class WriteThread implements Runnable {

    private Thread thr;
    private NetworkUtil nc;
    String name;
    Controller c;

    public WriteThread(NetworkUtil nc, String name, Controller c) {
        this.c=c;
        this.nc = nc;
        this.name=name;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while(true) {
                nc.write(c.clone());
            }
        } catch(Exception e) {
            System.out.println (e);
        }
    }
}



