/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.User;
import jdbc.XiangqiQuery;

/**
 *
 * @author nguye
 */
public class UserDAO {

    public boolean checkUsername(String username) throws SQLException, ClassNotFoundException {

         XiangqiQuery query = new XiangqiQuery();
         return query.checkUsername(username);

    }

    public boolean insertUser(User u) throws SQLException, ClassNotFoundException {
        XiangqiQuery query = new XiangqiQuery();
         return query.insertUser(u);
    }

    public boolean checkLogin(String username, String password) throws SQLException, ClassNotFoundException {
      XiangqiQuery query = new XiangqiQuery();
         return query.checkLogin(username, password);

    }
    
    public boolean checkBan(String username) throws SQLException, ClassNotFoundException {
      XiangqiQuery query = new XiangqiQuery();
         return query.checkBan(username);

    }
}
