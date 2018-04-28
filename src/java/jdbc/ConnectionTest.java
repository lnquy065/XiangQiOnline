package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author LN Quy
 */
public class ConnectionTest {


    public static void main(String[] args) throws SQLException {
        MySQLConnUtils mysql = new MySQLConnUtils();
        System.out.println(mysql.openConnection());
        ResultSet rs = mysql.excuteQuery("SELECT * FROM rooms");
        while(rs.next()) {
            int roomID = rs.getInt("id");
            String player1 = rs.getString("player1");
            String player2 = rs.getString("player2");
            System.out.println(roomID+" "+player1+" "+player2);
        }
        mysql.closeConnection();
        
    }

}
