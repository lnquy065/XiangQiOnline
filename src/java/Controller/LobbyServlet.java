/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nguye
 */
@WebServlet(name = "LobbyServlet", urlPatterns = {"/LobbyServlet"})
public class LobbyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);

        if (loginSession == null || loginSession.getAttribute("loginName") == null || loginSession.getAttribute("loginName").equals("")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {            
            request.setAttribute("loginName", loginSession.getAttribute("loginName"));

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/lobby.jsp");
            rd.forward(request, response);

        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
