/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import dataAccessObject.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nguye
 */
@WebServlet(name = "CheckUserServlet", urlPatterns = {"/CheckUserServlet"})
public class CheckUserServlet extends HttpServlet {

    UserDAO userDAO = new UserDAO();

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String user = request.getParameter("user");
            System.out.println(user);
            if (userDAO.checkUsername(user) || (user.length() < 6)) {
                response.getWriter().write("<img src=\"images/login/not-available.png\" />");
            } else {
                response.getWriter().write("<img src=\"images/login/available.png\" />");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CheckUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CheckUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

}
