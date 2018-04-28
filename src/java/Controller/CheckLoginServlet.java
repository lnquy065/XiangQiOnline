/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import dataAccessObject.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.MySQLConnUtils;
import tool.MD5;

/**
 *
 * @author nguye
 */
@WebServlet(name = "CheckLoginServlet", urlPatterns = {"/CheckLoginServlet"})
public class CheckLoginServlet extends HttpServlet {

    UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            String username = request.getParameter("username");
            String password = request.getParameter("password");          

            response.setContentType("text/html;charset=UTF-8");
            if (!userDAO.checkUsername(username)) {
                out.print("NONE");  //Tai khoan ko ton tai
            } else {    
                if (!userDAO.checkLogin(username, MD5.Encrytp(password))) {
                    out.print("WRONGPASS");                       //Sai password     
                } else if (userDAO.checkBan(username)) {
                    out.print("BANNED");  
                }else {

                    MySQLConnUtils conn = new MySQLConnUtils();
                    conn.openConnection();
                    ResultSet rS = conn.excuteQuery("SELECT online, role FROM account WHERE username='"+username+"'");
                    rS.next();
                    int role = rS.getInt("role");

                    if (rS.getInt("online")==1) {
                        out.print("ONLINE");  //Dang online may khac
                    } else {
                    
                    HttpSession session = request.getSession();
                    session.setAttribute("loginName", username);
                    session.setAttribute("role", role);
                    out.print("OK");   
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CheckLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
}
