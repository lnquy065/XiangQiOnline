package Model;

import jdbc.MySQLConnUtils;
import jdbc.XiangqiQuery;


public class CountDownThread implements Runnable{
    private MySQLConnUtils mysql=null;
    private int roomID;
    private String col;

    public CountDownThread(int roomID, String col) {
        this.roomID = roomID;
        this.col = col;
        mysql = new MySQLConnUtils();
        mysql.openConnection();
    }

    
    @Override
    public void run() {
        Room tmp = XiangqiQuery.getRoom(mysql, roomID);
       // System.out.println(tmp.toString());
        if (tmp.totalTime>=0) {
            tmp.totalTime-=1;
            XiangqiQuery.setRoom(mysql, tmp);
        } else {
            System.out.println("Close Thread");
            mysql.closeConnection();
            Thread.currentThread().isInterrupted();
        }
        
    }

}
