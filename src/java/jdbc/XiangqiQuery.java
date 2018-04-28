package jdbc;

import Model.Room;
import Model.User;
import dataAccessObject.UserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XiangqiQuery {

    private MySQLConnUtils mysql = null;

    public XiangqiQuery() {
        this.mysql = new MySQLConnUtils();
        mysql.openConnection();
    }

    public Room getRoom(int id) {
        return new Room(mysql.excuteQuery("SELECT * FROM rooms WHERE id=" + id));
    }

    public void setRoom(Room r) {

        mysql.excuteUpdate(r.toUpdateQuery("rooms", "WHERE id=" + r.roomID));
    }

    public void addTime(int id, int unit, String field) {
        Room tmp = new Room(mysql.excuteQuery("SELECT * FROM rooms WHERE id=" + id));
        if (field.equals("totalTime")) {
            tmp.totalTime += unit;
        } else if (field.equals("turnTime")) {
            tmp.turnTime += unit;
        } else {
            tmp.totalTime += unit;
            tmp.turnTime += unit;
        }
        setRoom(tmp);
    }

    public static Room getRoom(MySQLConnUtils mysql, int id) {
        //  System.out.println("SELECT * FROM rooms WHERE id="+id);
        return new Room(mysql.excuteQuery("SELECT * FROM rooms WHERE id=" + id));
    }

    public static void setRoom(MySQLConnUtils mysql, Room r) {
        mysql.excuteUpdate(r.toUpdateQuery("rooms", "WHERE id=" + r.roomID));
    }

    public static void addTime(MySQLConnUtils mysql, int id, int unit, String field) {
        Room tmp = new Room(mysql.excuteQuery("SELECT * FROM rooms WHERE id=" + id));
        if (field.equals("totalTime")) {
            tmp.totalTime += unit;
        } else if (field.equals("turnTime")) {
            tmp.turnTime += unit;
        } else {
            tmp.totalTime += unit;
            tmp.turnTime += unit;
        }
        setRoom(mysql, tmp);
    }

    public boolean checkUsername(String username) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM account WHERE username = '" + username + "'";

        ResultSet rs = mysql.excuteQuery(sql);
        while (rs.next()) {
            this.close();
            return true;
        }

        return false;
    }

    public boolean checkBan(String username) throws SQLException, ClassNotFoundException {

        String sql = "SELECT ban FROM account WHERE username = '" + username + "'";

        ResultSet rS = mysql.excuteQuery(sql);
        rS.next();
        if (rS.getInt("ban") != 0) {  //Dang bi ban nick
            Date date = new Date();
            int timeDelay = (int) (date.getTime() / 1000) - rS.getInt("ban");
            if (timeDelay < (3 * 60)) {
                return true;
            }
            
        }
        return false;
    }
    
    
        

    public boolean insertUser(User u) throws SQLException, ClassNotFoundException {
        Connection connection = this.mysql.getConnection();
        String sql = "INSERT INTO account (username,password) VALUES ('" + u.getUsername() + "','" + u.getPassword() + "')";
        try {
            Statement ps = connection.createStatement();
            return ps.executeUpdate(sql) == 1;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean checkLogin(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = this.mysql.getConnection();
        String sql = "SELECT * FROM account WHERE username='" + username + "' and password='" + password + "'";
        PreparedStatement ps;
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.close();
                return true;
            }
        } catch (SQLException e) {
        }
        return false;

    }

    public void close() {
        mysql.closeConnection();
    }
}
