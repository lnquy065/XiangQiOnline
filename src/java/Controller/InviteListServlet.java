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
@WebServlet(name = "InviteListServlet", urlPatterns = {"/InviteListServlet"})
public class InviteListServlet extends HttpServlet {

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
            String playerName = request.getParameter("playerName");
            String cmd = request.getParameter("cmd");
            
            switch (cmd) {
                case "invite":
                    MySQLConnUtils conn = new MySQLConnUtils();
                    conn.openConnection();
                    int roomID = Integer.valueOf(request.getParameter("roomID"));
                    String inviteName = request.getParameter("inviteName");
                    String sql = "UPDATE account SET invitePlayer='" + playerName + "', inviteRoom='" + roomID + "' WHERE username='" + inviteName + "'";
                   // System.out.println(sql);
                    conn.excuteUpdate(sql);
                    conn.closeConnection();
                    break;
                case "getInvite":
                    MySQLConnUtils conn2 = new MySQLConnUtils();
                    conn2.openConnection();
                    ResultSet rS = conn2.excuteQuery("SELECT invitePlayer, inviteRoom FROM account WHERE username='" + playerName + "'");
                    if(rS.next())
                    out.print(rS.getString("invitePlayer") + "-" + rS.getInt("inviteRoom"));
                    else out.print("-");
                    conn2.closeConnection();
                    break;
                case "resetInvite":
                    MySQLConnUtils conn3 = new MySQLConnUtils();
                    conn3.openConnection();
                    String sql2 = "UPDATE account SET invitePlayer='null', inviteRoom='0' WHERE username='" + playerName + "'";
                   // System.out.println(sql);
                    conn3.excuteUpdate(sql2);
                    conn3.closeConnection();
                    
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
            Logger.getLogger(InviteListServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(InviteListServlet.class.getName()).log(Level.SEVERE, null, ex);
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
