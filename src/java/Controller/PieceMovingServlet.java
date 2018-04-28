/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Board;
import Model.Piece;
import Model.Room;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.MySQLConnUtils;
import jdbc.XiangqiQuery;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author LN Quy
 */
@WebServlet(name = "PieceMovingServlet", urlPatterns = {"/PieceMovingServlet"})
public class PieceMovingServlet extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //Lay tham so
        int roomID = Integer.valueOf(request.getParameter("roomID"));
        String playerName = request.getParameter("playerName");
        String side = request.getParameter("side");
        int pieceIndex = Integer.valueOf(request.getParameter("pieceIndex"));
        String getOldPos[] = request.getParameter("oldPos").split("-");
        String getNewPos[] = request.getParameter("newPos").split("-");;
        Point pieceOldPos = new Point(Integer.valueOf(getOldPos[0]), Integer.valueOf(getOldPos[1]));
        Point pieceNewPos = new Point(Integer.valueOf(getNewPos[0]), Integer.valueOf(getNewPos[1]));
        Piece p = new Piece(pieceIndex, pieceOldPos, pieceNewPos);
        //Chuyen doi Tham So
        Board board = new Board(roomID);
        boolean nonCheat ;
        
        if (side.equals("h")) { //Host
            nonCheat= board.isCheated(p);
            board.movePiece(p);
            
        } else {
            nonCheat= board.isCheated(p.invert());
            board.movePiece(p.invert());
        }
        if (!rightTurn(roomID, playerName)) nonCheat = false;
        board.writeBoard();
       // p.invert();
        
        //Ghi ra json
        createJSON(roomID, playerName, p.invert(), nonCheat);
        

        //Ghi database thoi gian di chuyen
        XiangqiQuery query = new XiangqiQuery();
        Room roomObj2 = query.getRoom(roomID);
        roomObj2.turnTime = request.getParameter("turnTime");
        query.setRoom(roomObj2);
        query.close();
        int winner = board.CheckWhoWin();
        if (winner!=Board.NULL && nonCheat==true) {
            System.out.println("win:"+winner);
            board.addExp(winner);
        }
        if (nonCheat==false) {
           winner = side.equals("h")? Board.CLIENT:Board.HOST;
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.println(p.getPiecePosition().x+","+p.getPiecePosition().y+","+p.getPieceNewPosition().x+","+p.getPieceNewPosition().y+"|"+roomObj2.turnTime+"|"+board.CheckWhoWin()+"|"+nonCheat);
        }
        if (nonCheat==false) {
               //ban 3 ph
               HttpSession session = request.getSession();
                MySQLConnUtils conn2 = new MySQLConnUtils();
                conn2.openConnection();
                long dT = (new Date().getTime() / 1000) + 3 * 60;
                conn2.excuteUpdate("UPDATE account SET ban=" + dT + " WHERE username='" + session.getAttribute("loginName")+"'");
                conn2.closeConnection();
                
                //Dang xuat
                session.removeAttribute("loginName");
                session.invalidate();
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
//                rd.forward(request, response);
        }      
    }
    
    public boolean rightTurn(int roomID, String playerName) {
        FileReader fr = null;
        try {
            fr = new FileReader(new File("XiangqiOnline/RoomJSON/" + roomID + ".room"));
            BufferedReader bf = new BufferedReader(fr);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(bf.readLine());
            boolean check = json.get("player").equals(playerName);
            fr.close();
            if (check) return true;
            return false;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PieceMovingServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PieceMovingServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PieceMovingServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(PieceMovingServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public void createJSON(int roomID, String player, Piece p, boolean nonCheat) {
        JSONObject json = new JSONObject();
        json.put("roomID", roomID);
        json.put("state", "moving");
        json.put("player", player);
        json.put("pIndex", p.getPieceIndex());
        json.put("oldPosition", p.getPiecePosition().x + "-" + p.getPiecePosition().y);
        json.put("newPosition", p.getPieceNewPosition().x + "-" + p.getPieceNewPosition().y);
        json.put("nonCheat", nonCheat);
        File myfile = new File("XiangqiOnline/RoomJSON/" + roomID + ".room");
        try {
            myfile.createNewFile();

        } catch (IOException ex) {
            Logger.getLogger(PieceMovingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(myfile.getAbsolutePath());
        try (FileWriter file = new FileWriter("XiangqiOnline/RoomJSON/" + roomID + ".room")) {
            file.flush();
            file.write(json.toJSONString());
        } catch (IOException e) {
            System.out.println("Loi JSON");
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
        processRequest(request, response);
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
        processRequest(request, response);
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
