package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MySQLConnUtils {
    private String hostName = "localhost";
    private String dbName = "xiangqi";
    
    private String userName = "root";
    private String passWord = "root";
    
    private Connection cn = null;
    private Statement statement = null;
    
    public Connection getConnection() {
        return cn;
    }
    
    public boolean openConnection() {
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName+"?useSSL=false";
 
        try {
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            cn = DriverManager.getConnection(connectionURL, userName, passWord);
            statement = cn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnUtils.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(MySQLConnUtils.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            Logger.getLogger(MySQLConnUtils.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(MySQLConnUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQLConnUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MySQLConnUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MySQLConnUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cn!=null;
    }
    
    public void excuteUpdate(String query) {
        if (statement==null) return;
        try {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public ResultSet excuteQuery(String query) {
        try {
            
            return statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public void closeConnection() {
        try {
            if (cn!=null) cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
}
