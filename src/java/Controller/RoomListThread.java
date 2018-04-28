package Controller;

import Model.Room;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import jdbc.MySQLConnUtils;
import jdbc.XiangqiQuery;

public class RoomListThread implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable command = new RoomScanner(event.getServletContext());
        // Delay 1 Minute to first execution
        long initialDelay = 0;
        TimeUnit unit = TimeUnit.SECONDS;
        // period the period between successive executions
        long period = 5;// 60 Minute!

        scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
        //System.out.println("Bat dau luong");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

}

class RoomScanner implements Runnable {

    private ServletContext context;

    public RoomScanner(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        try {
            System.out.println("Bat dau quet ... " + new Date());
            MySQLConnUtils conn = new MySQLConnUtils();
            conn.openConnection();
            ResultSet rS = conn.excuteQuery("SELECT * FROM account");
            String updateSQL = "UPDATE account SET online='0', inroom=0 WHERE username='";
            String updateSQLOnline = "UPDATE account SET online='1' WHERE username='";
            String par = "";
            String parOnline ="";
            //System.out.println("-----");
            while (rS.next()) {
                
                String username = rS.getString("username");
               // System.out.print(username);
                int time = rS.getInt("timestamp");
                Date date = new Date();
                long timeDelay = Long.valueOf(date.getTime() / 1000) - time;
                if (timeDelay > 5) { //player da thoat game
                    //System.out.println(rS.getString("username") + " out");
                    par += username + "' or username='";
                    
                    if (rS.getInt("inroom") != 0) {
                        XiangqiQuery query = new XiangqiQuery();
                        Room roomObj = query.getRoom(rS.getInt("inroom"));
                        if (roomObj.player1.equals(username)) {
                            roomObj.player1 = "null";
                        } else {
                            roomObj.player2 = "null";
                        }
                        roomObj.playing = false;
                        query.setRoom(roomObj);
                        query.close();
                    }
                } else if (timeDelay < 5) {
                     parOnline += username + "' or username='";
                }
            }
            
            
            if (!par.equals("")) {
                updateSQL += par;
                updateSQL = updateSQL.substring(0, updateSQL.length() - 14);
                //System.out.println("offline: "+updateSQL);
                conn.excuteUpdate(updateSQL);
            }
             if (!parOnline.equals("")) {
                updateSQLOnline += parOnline;
                updateSQLOnline = updateSQLOnline.substring(0, updateSQLOnline.length() - 14);
                conn.excuteUpdate(updateSQLOnline);
            }
            
            conn.closeConnection();
          //  System.out.println("Xong!");
        } catch (SQLException ex) {
            Logger.getLogger(RoomScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
