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

import Model.User;
import tool.MD5;

/**
 *
 * @author nguye
 */
@WebServlet(name = "CheckSignupServlet", urlPatterns = {"/CheckSignupServlet"})
public class CheckSignupServlet extends HttpServlet {

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

            String user = request.getParameter("user");
            String pass = request.getParameter("pass");
            String repass = request.getParameter("repass");

            response.setContentType("text/html;charset=UTF-8");
            if (userDAO.checkUsername(user)) {
                out.print("USER");
            } else {    
                if(!pass.equals(repass)){
                    out.print("REPASS");
                }else{
                    User u = new User();
                    u.setUsername(user);
                    u.setPassword(MD5.Encrytp(pass));
                    if(userDAO.insertUser(u))
                        out.print("OK");
                    else{
                        out.print("ERROR");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CheckSignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
}
