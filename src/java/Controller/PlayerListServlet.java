/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdbc.MySQLConnUtils;

/**
 *
 * @author LN Quy
 */
@WebServlet(name = "PlayerListServlet", urlPatterns = {"/PlayerListServlet"})
public class PlayerListServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String playerList = "";
            MySQLConnUtils conn = new MySQLConnUtils();
            conn.openConnection();
            String cmd = request.getParameter("cmd");

            switch (cmd) {
                case "playerList":
                    ResultSet rS = conn.excuteQuery("SELECT * FROM account WHERE online='1'");
                    if (rS==null) return;
                    while (rS.next()) {
                        if (rS.getInt("inroom")!=0) {
                            MySQLConnUtils conn2 = new MySQLConnUtils();
                            conn2.openConnection();
                            ResultSet rS2 = conn2.excuteQuery("SELECT * FROM rooms WHERE id='"+rS.getInt("inroom")+"'");
                            rS2.next();
                            if (rS2.getBoolean("playing")==true) playerList+="?";
                            rS2.close();
                            conn2.closeConnection();
                        }
                        playerList += rS.getString("username") + "|";
                    }
                    rS.close();
                    if (playerList.length()>0)
                    out.print(playerList.substring(0, playerList.length() - 1));
                    break;
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
