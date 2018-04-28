package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.MySQLConnUtils;
import org.json.simple.JSONObject;


public class Room {
    public int roomID;
    public String player1;
    public String player2;
    public boolean playing;
    public String turnTime;
    public int totalTime;
    public String startTotalTime;
    public String win;
    
    public Room(int rID) {
        MySQLConnUtils conn = new MySQLConnUtils();
        conn.openConnection();
        InitRS(conn.excuteQuery("select * from rooms where id='"+rID+"'"));
        conn.closeConnection();
    }
    
    public Room(ResultSet rs) {
        InitRS(rs);
    }
    
    public void update() {
        MySQLConnUtils conn = new MySQLConnUtils();
        conn.openConnection();
        conn.excuteUpdate(toUpdateQuery("rooms", "WHERE id="+roomID));
        conn.closeConnection();
    }
    
    public void InitRS(ResultSet rs) {
                if (rs==null) return;
        try {
            rs.next();
            roomID = rs.getInt("id");
            player1 = rs.getString("player1");
            player2 = rs.getString("player2");
            playing = rs.getBoolean("playing");
            turnTime = rs.getString("turnTime");
            totalTime = rs.getInt("totalTime");
            startTotalTime = rs.getString("startTotalTime");
            win =  rs.getString("win");
        } catch (SQLException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String toUpdateQuery(String table, String where) {
        String sql = String.format("UPDATE rooms SET `player1`='%s',`player2`='%s',`turnTime`='%s',`totalTime`='%d',`playing`=%b,`startTotalTime`='%s',`win`='%s' "+where, player1, player2, turnTime, totalTime, playing, startTotalTime, win);
       // String sql = "UPDATE "+table+" SET `player1`='"+player1+"',`player2`='"+player2+"',`turnTime`="+turnTime+",`totalTime`="+totalTime+",`playing`="+playing+",`startTotalTime`='"+startTotalTime+"' "+where;
      // System.out.println(sql);
        return sql;
    }
    
    
    public String toJSON() {
        JSONObject json = new JSONObject();
        json.put("roomID", roomID);
        json.put("player1", player1);
        json.put("player2", player2);
        json.put("turnTime", turnTime);
        json.put("startTotalTime", startTotalTime);
        json.put("playing", playing);
        return json.toJSONString();
    }
}
