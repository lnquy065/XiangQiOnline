/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Board;
import Model.Room;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
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
@WebServlet(name = "RoomServlet", urlPatterns = {"/RoomServlet"})
public class RoomServlet extends HttpServlet {

    private ScheduledExecutorService scheduler;

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
        String cmd = request.getParameter("cmd");
        if (request.getParameter("roomID").equals("null")) {
            return;
        }
        int roomID = Integer.valueOf(request.getParameter("roomID"));
        HttpSession session = request.getSession();
        XiangqiQuery query = null;
        Room roomObj = null;
        switch (cmd) {
            case "time":
                try (PrintWriter out = response.getWriter()) {
                    query = new XiangqiQuery();
                    out.println(query.getRoom(roomID).startTotalTime);
                    out.println("|" + query.getRoom(roomID).turnTime);
                    query.close();
                }
                break;
            case "refesh":
                try (PrintWriter out = response.getWriter()) {
                    boolean doiThuOnline = RoomGetConnection(request.getParameter("enemyName"));
                    FileReader fr = new FileReader(new File("XiangqiOnline/RoomJSON/" + roomID + ".room"));
                    BufferedReader bf = new BufferedReader(fr);
                    out.println(bf.readLine());
                    RoomSetConnection(request.getParameter("playerName"), request.getParameter("timeRequest"));
                    out.println("|" + String.valueOf(doiThuOnline));
                    String mess = ReceiveMessage(roomID, request.getParameter("playerName"));
                    Board board = new Board(roomID);
                    out.print("|" + board.CheckWhoWin());
                    if (mess != null) {
                        out.println("|" + mess);
                    }

                    MySQLConnUtils conn = new MySQLConnUtils();
                    conn.openConnection();
                    conn.excuteUpdate("UPDATE account SET timestamp='" + request.getParameter("timeRequest") + "' WHERE username='" + request.getParameter("playerName") + "'");
                    conn.closeConnection();

                    if (!doiThuOnline) {
                        HttpSession ss = request.getSession();
                        if (ss.getAttribute("p").toString().trim().equals("h")) {
                         //   System.out.println("Cong exp");
                            board.addExp(Board.HOST);
                        } else {
                          //  System.out.println("Cong exp");
                            board.addExp(Board.CLIENT);
                        }
                    }

                }
                break;
            case "getDB":
                query = new XiangqiQuery();
                roomObj = query.getRoom(roomID);
                outPrintln(response, roomObj.toJSON());
                query.close();
                break;
            case "start":
                query = new XiangqiQuery();
                roomObj = query.getRoom(roomID);
                roomObj.playing = true;
                roomObj.totalTime = 15;
                roomObj.startTotalTime = roomObj.turnTime = request.getParameter("startTotalTime");
                roomObj.win = "null";
                //roomObj.turnTime = Integer.valueOf(request.getParameter("turnTime"));
                query.setRoom(roomObj);
                query.close();
                createJSONGameState(roomID, "wait", request.getParameter("playerName"));
                createBoard(roomID);
                ClearMess(roomID);
                break;
            case "join":
                query = new XiangqiQuery();
                roomObj = query.getRoom(roomID);
                //Kiem tra full
                if (!roomObj.player1.equals("null") && !roomObj.player2.equals("null")) {
                    outPrintln(response, "full");
                    query.close();
                    return;
                }

                //Kiem tra trung ten
                if (roomObj.player1.equals(request.getParameter("playerName"))
                        || roomObj.player2.equals(request.getParameter("playerName"))) {
                    query.close();
                    outPrintln(response, "trungten");
                    return;
                }
                //     System.out.println(roomObj.player1+ " "+roomObj.player2+ " "+request.getParameter("playerName"));

                if (roomObj.player1.equals("null")) { //=> HOST
                    roomObj.player1 = request.getParameter("playerName");
                    outPrintln(response, "host");
                } else { //=> CLIENT
                    roomObj.player2 = request.getParameter("playerName");
                    outPrintln(response, "client|" + roomObj.player1);
                }
                query.setRoom(roomObj);
                query.close();

                MySQLConnUtils conn = new MySQLConnUtils();
                conn.openConnection();
                conn.excuteUpdate("UPDATE account SET inroom='" + roomID + "' WHERE username='" + request.getParameter("playerName") + "'");
                conn.closeConnection();

                break;
            case "exit":
                 query = new XiangqiQuery();
                if (roomID == 0) {
                    return;
                }
                roomObj = query.getRoom(roomID);
                System.out.println("Thoat khoi phong: " + roomID + " " + request.getParameter("playerName"));

                if (roomObj.player1.equals(request.getParameter("playerName"))) {
                    roomObj.player1 = "null";
                } else {
                    roomObj.player2 = "null";
                }
                roomObj.playing = false;
                session.setAttribute("roomID", "null");
                query.setRoom(roomObj);
                query.close();

                break;
            case "exitSafe":
                 query = new XiangqiQuery();
                if (roomID == 0) {
                    return;
                }
                String side="";
                roomObj = query.getRoom(roomID);
                 if (roomObj.player1.equals(request.getParameter("playerName"))) {
                    side="host";
                } else {
                    side="client";
                }
                
                if (roomObj.playing) {
                    outPrintln(response, String.valueOf(roomID));
                    return;
                }
                if (side.equals("host")) {
                    roomObj.player1 = "null";
                } else {
                    roomObj.player2 = "null";
                }
                
                roomObj.playing = false;
                session.setAttribute("roomID", "null");
                query.setRoom(roomObj);
                query.close();
                 outPrintln(response, "done");
                break;
            case "chat":
                SendMessage(roomID, request.getParameter("playerName"), request.getParameter("message"));
                break;
            case "createSession":

                session.setAttribute("playerName", request.getParameter("playerName"));
                session.setAttribute("roomID", request.getParameter("roomID"));
                session.setAttribute("e", request.getParameter("e"));
                session.setAttribute("p", request.getParameter("p"));
                break;
            case "getBoard":
                Board board = new Board(roomID);
                //System.out.println(request.getParameter("side"));
                if (request.getParameter("side").equals("h")) {

                    outPrintln(response, board.toArrayString(false));
                    //  System.out.println(board.toArrayString(false));
                } else {
                    // System.out.println("Get board");
                    String invert = board.toArrayString(true);
                    // System.out.println("Invert: "+invert);
                    outPrintln(response, invert);

                }

                break;
            default:
                String player = request.getParameter("playerName");
                createJSONGameState(roomID, cmd, player);
                if (cmd.equals("wait")) {

                    try (PrintWriter out = response.getWriter()) {
                        query = new XiangqiQuery();
                        Room roomObj2 = query.getRoom(roomID);
                        out.println(roomObj2.turnTime);
                        query.close();
                    }
                }
        }
    }

    public void outPrintln(HttpServletResponse response, String text) {
        try (PrintWriter out = response.getWriter()) {
            out.println(text);

        } catch (IOException ex) {
            Logger.getLogger(RoomServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void createBoard(int roomID) {
        File myfile = new File("XiangqiOnline/RoomJSON/" + roomID + "board.room");
        try {
            myfile.createNewFile();

        } catch (IOException ex) {
            Logger.getLogger(PieceMovingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter file = new FileWriter("XiangqiOnline/RoomJSON/" + roomID + "board.room")) {
            file.flush();
            file.write("11,10,9,8,7,8,9,10,11|"
                    + "-1,-1,-1,-1,-1,-1,-1,-1,-1|"
                    + "-1,12,-1,-1,-1,-1,-1,12,-1|"
                    + "13,-1,13,-1,13,-1,13,-1,13|"
                    + "-1,-1,-1,-1,-1,-1,-1,-1,-1|"
                    + "-1,-1,-1,-1,-1,-1,-1,-1,-1|"
                    + "6,-1,6,-1,6,-1,6,-1,6|"
                    + "-1,5,-1,-1,-1,-1,-1,5,-1|"
                    + "-1,-1,-1,-1,-1,-1,-1,-1,-1|"
                    + "4,3,2,1,0,1,2,3,4");
        } catch (IOException e) {
            System.out.println("Loi JSON");
        }
    }

    public void createJSONGameState(int roomID, String state, String player) {
        JSONObject json = new JSONObject();
        json.put("roomID", roomID);
        json.put("state", state);
        json.put("player", player);
        File myfile = new File("XiangqiOnline/RoomJSON/" + roomID + ".room");
        try {
            myfile.createNewFile();

        } catch (IOException ex) {
            Logger.getLogger(PieceMovingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter file = new FileWriter("XiangqiOnline/RoomJSON/" + roomID + ".room")) {
            file.flush();
            file.write(json.toJSONString());
        } catch (IOException e) {
            System.out.println("Loi JSON");
        }
    }

    public void RoomSetConnection(String player, String time) {
        JSONObject json = new JSONObject();
        json.put("player", player);
        json.put("timeRequest", time);
        File myfile = new File("XiangqiOnline/RoomJSON/" + player + ".time");
        try {
            myfile.createNewFile();

        } catch (IOException ex) {
            Logger.getLogger(PieceMovingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter file = new FileWriter("XiangqiOnline/RoomJSON/" + player + ".time")) {
            file.write(json.toJSONString());
            file.close();
        } catch (IOException e) {
            System.out.println("Loi JSON");
        }
    }

    public boolean RoomGetConnection(String player) {
        FileReader fr = null;
        try {
            fr = new FileReader(new File("XiangqiOnline/RoomJSON/" + player + ".time"));
            BufferedReader bf = new BufferedReader(fr);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(bf.readLine());
            long timeRequest = Long.valueOf(json.get("timeRequest").toString());
            Date date = new Date();
            long timeDelay = Long.valueOf(date.getTime() / 1000) - timeRequest;
            fr.close();
            if (timeDelay > 10) { //Enemy da thoat game
                return false;
            }
            return true;

        } catch (IOException | ParseException ex) {
        }

        return false;
    }

    public void SendMessage(int roomID, String player, String mess) {
        JSONObject json = new JSONObject();
        json.put("player", player);
        json.put("mess", mess);
        File myfile = new File("XiangqiOnline/RoomJSON/chat" + roomID + ".mess");
        try {
            myfile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(PieceMovingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (OutputStreamWriter fileO = new OutputStreamWriter(new FileOutputStream(myfile), StandardCharsets.UTF_8)) {
            fileO.write(json.toJSONString());
            fileO.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RoomServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RoomServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ClearMess(int roomID) {
        File file = new File("XiangqiOnline/RoomJSON/chat" + roomID + ".mess");
        if (!file.exists()) {
            return;
        }
        file.delete();
    }

    @SuppressWarnings("empty-statement")
    public String ReceiveMessage(int roomID, String player) {
        File file = new File("XiangqiOnline/RoomJSON/chat" + roomID + ".mess");
        if (!file.exists()) {
            return null;
        }
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));) {
            JSONParser parser = new JSONParser();
            String data = bf.readLine();
            if (data.equals("")) {
                return null;
            }
            JSONObject json = (JSONObject) parser.parse(data);
            String playerM = json.get("player").toString();
            String mess = json.get("mess").toString();
            bf.close();
            if (playerM.equals(player)) {
                return null;    //Tin nhan cua chinh player
            }

            file.delete();
            return mess;
        } catch (IOException | ParseException ex) {
        }
        return null;
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
