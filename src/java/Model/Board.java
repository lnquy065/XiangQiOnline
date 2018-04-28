package Model;

import Controller.PieceMovingServlet;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import jdbc.MySQLConnUtils;

public class Board {

    private int array[][] = new int[10][9];
    private int roomID;
    public static int HOST = 0;
    public static int CLIENT = 7;
    public static int NULL = -1;
    public Room roomObj;

    public Board(int id) {
        array = readBoard(id);
        this.roomID = id;
        roomObj = new Room(id);
    }

    public void movePiece(Piece p) {
        int pIndex = p.getPieceIndex();

        Point newPos = p.getPiecePosition();
        Point oldPos = p.getPieceNewPosition();
        System.out.println("NewPos: " + newPos.x + " " + newPos.y);
        System.out.println("OldPos: " + oldPos.x + " " + oldPos.y);
        array[newPos.y][newPos.x] = -1;
        array[oldPos.y][oldPos.x] = p.getPieceIndex();
    }

    public void writeBoard() {
        int id = this.roomID;
        File myfile = new File("XiangqiOnline/RoomJSON/" + id + "board.room");
        try {
            myfile.createNewFile();

        } catch (IOException ex) {
            Logger.getLogger(PieceMovingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter file = new FileWriter("XiangqiOnline/RoomJSON/" + id + "board.room")) {
            String array = "";
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    array += String.valueOf(this.array[i][j]);
                    if (j < 8) {
                        array += ",";
                    }
                }
                if (i < 9) {
                    array += "|";
                }
            }
            file.write(array);
            file.close();
        } catch (IOException e) {
            System.out.println("Loi JSON");
        }
    }

    public int[][] readBoard(int id) {
        int array[][] = new int[10][9];
        FileReader fr = null;
        try {
            fr = new FileReader(new File("XiangqiOnline/RoomJSON/" + id + "board.room"));
            BufferedReader bf = new BufferedReader(fr);

            String arrayS = bf.readLine();
            String[] rows = arrayS.split(Pattern.quote("|"));
            for (int i = 0; i < rows.length; i++) {
                String[] cels = rows[i].split(",");
                for (int j = 0; j < cels.length; j++) {
                    array[i][j] = Integer.valueOf(cels[j]);
                }
            }

            fr.close();
            return array;
        } catch (IOException ex) {
        }
        return null;
    }

    public int[][] invert() {
        int[][] invertA = new int[10][9];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                //   System.out.println(i+" "+j + "=" + (9-i)+ " "+(8-j));
                invertA[i][j] = Piece.InvertIndex(this.array[9 - i][8 - j]);
            }
            //System.out.println("");
        }

        return invertA;
    }

    public String toArrayString(boolean invert) {
        String array = "";
        if (invert) {
            int[][] arrayI = invert();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    array += String.valueOf(arrayI[i][j]);
                    if (j < 8) {
                        array += ",";
                    }
                }
                if (i < 9) {
                    array += "|";
                }
            }
        } else {
            // printArray();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    array += String.valueOf(this.array[i][j]);
                    if (j < 8) {
                        array += ",";
                    }
                }
                if (i < 9) {
                    array += "|";
                }
            }

        }
        return array;
    }

    public void printArray() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(String.valueOf(this.array[i][j]) + " ");

            }
            System.out.println("");
        }
    }

    public int CheckWhoWin() {
        boolean host = false, client = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (array[i][j] == this.HOST) {
                    host = true;
                }
                if (array[i][j] == this.CLIENT) {
                    client = true;
                }
            }
        }
        if (host & client) {
            return this.NULL;
        }
        if (host) {

            return this.HOST;
        }
        return this.CLIENT;
    }

    public int addExp(int color) {
        try {
            MySQLConnUtils conn = new MySQLConnUtils();
            conn.openConnection();
            ResultSet rS = conn.excuteQuery("select * from rooms where id='" + roomID + "'");
            rS.next();
            roomObj = new Room(roomID);
            if (rS.getString("win").equals("null")) {
                String sqlH, sqlC, winner;
                if (color == this.HOST) {
                    sqlH = "update account set countwin=countwin+1 where username = '" + roomObj.player1 + "'";
                    sqlC = "update account set countlose=countlose+1 where username = '" + roomObj.player2 + "'";
                    winner = roomObj.player1;
                } else {
                    sqlH = "update account set countwin=countwin+1 where username = '" + roomObj.player2 + "'";
                    sqlC = "update account set countlose=countlose+1 where username = '" + roomObj.player1 + "'";
                    winner = roomObj.player2;
                }
                System.out.println(sqlH + "\n" + sqlC);
                conn.excuteUpdate(sqlH);
                conn.excuteUpdate(sqlC);
                conn.excuteUpdate("update rooms set win='" + winner + "' where id='" + roomID + "'");
            }
            conn.closeConnection();
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public boolean checkInRange(Piece p, int nX, int nY) {
        int pIndex = p.getPieceIndex();
        int x = p.getPieceNewPosition().x;
        int y = p.getPieceNewPosition().y;
        System.out.println("new Pos: "+x+"-"+y+"|match pos: "+nX+"-"+nY);
        if (nX != x || nY != y) {
            return false;
        }
        

        boolean checkOk = false;
        //Kiem tra vung di chuyen
        switch (pIndex) {
            case 0: //Tuong, Si
            case 1:
                if (x >= 3 && x <= 5 && y >= 7 && y <= 9) {
                    checkOk = true;
                }
                break;
            case 2:
                if (y >= 5 && y <= 9) {
                    checkOk = true;
                }
                break;
            case 7:
            case 8:
                if (x >= 3 && x <= 5 && y >= 0 && y <= 2) {
                    checkOk = true;
                }
                break;
            case 9:
                if (y >= 0 && y <= 4) {
                    checkOk = true;
                }
                break;
            default:
                checkOk = true;
        }
        System.out.println("checkInRange: "+checkOk);
        if (checkOk) {
            if (p.getColor()==Piece.getColor(array[y][x])) return false;
        }
        return checkOk;
    }
    
    public boolean isInBoard(int x, int y) {
            if (x < 0 || x > 8 || y < 0 || y > 9)
        return false;
    return true;
    }
    
    public int getPieceColorAtPos(int x, int y) {
         if (!isInBoard(x, y)) return -1;
        // System.out.println("Color At: "+x+"-"+y+">"+Piece.getColor(array[y][x]));
        return Piece.getColor(array[y][x]);
    }
    
    public int getPiecesIndex(int x, int y) {
    //Ra ngoai
    if (x < 0 || x > 8 || y < 0 || y > 9)
        return -2;   //Ra bien
    else
        return array[y][x];
}
    public int getGOfPiece(int pIndex) {
        if (pIndex>= 0 && pIndex <7) return 0;
        else if (pIndex>=7) return 7;
        return pIndex;
    }
        public int getGOfEnemy(int pIndex) {
        if (pIndex>= 0 && pIndex <7) return 7;
        else if (pIndex>=7) return 0;
        return pIndex;
    }

    public boolean isCheated(Piece p) {
        int pIndex = p.getPieceIndex();
        int x = p.getPiecePosition().x;
        int y = p.getPiecePosition().y;
        int i=0;
        boolean ret =false;
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return false;
        }
        System.out.println("Anti cheat p: "+pIndex);
        switch (pIndex) {
            case 7:
            case 0: //Tuong
                ret= checkInRange(p, x, y - 1) |
                        checkInRange(p, x, y + 1) |
                        checkInRange(p, x + 1, y) |
                        checkInRange(p, x - 1, y);
                int xN = p.getPieceNewPosition().x;
                int yN = p.getPieceNewPosition().y;
                if (getPiecesIndex(xN, yN)==getGOfEnemy(pIndex)) ret |=true;
                break;
            case 8:
            case 1: //Si
                ret = checkInRange(p, x - 1, y - 1) |
                        checkInRange(p, x + 1, y + 1) |
                        checkInRange(p, x + 1, y - 1) |
                        checkInRange(p, x - 1, y + 1);
                break;
            case 9:
            case 2: //Bo
                if (getPiecesIndex( x - 1, y - 1) < 0) {
                    ret |= checkInRange(p, x - 2, y - 2);
                }
                if (getPiecesIndex(x + 1,y + 1 ) < 0) {
                    ret |= checkInRange(p, x + 2, y + 2);
                }
                if (getPiecesIndex(x - 1 , y + 1 ) < 0) {
                    ret |= checkInRange(p, x - 2, y + 2);
                }
                if (getPiecesIndex(x + 1, y - 1 ) < 0) {
                    ret |= checkInRange(p, x + 2, y - 2);
                }
                break;
            case 10:
            case 3: //Ma
                if (getPiecesIndex(x ,y - 1) < 0) {
                    ret |= checkInRange(p, x + 1, y - 2);
                }
                if (getPiecesIndex(x,y - 1) < 0) {
                    ret |= checkInRange(p, x - 1, y - 2);
                }

                if (getPiecesIndex( x, y + 1) < 0) {
                    ret |= checkInRange(p, x + 1, y + 2);
                }
                if (getPiecesIndex(x, y + 1) < 0) {
                    ret |= checkInRange(p, x - 1, y + 2);
                }

                if (getPiecesIndex(x + 1,y) < 0) {
                    ret |= checkInRange(p, x + 2, y - 1);
                }
                if (getPiecesIndex(x + 1,y)< 0) {
                    ret |= checkInRange(p, x + 2, y + 1);
                }

                if (getPiecesIndex(x - 1,y) < 0) {
                    ret |= checkInRange(p, x - 2, y - 1);
                }
                if (getPiecesIndex(x - 1,y) < 0) {
                    ret |= checkInRange(p, x - 2, y + 1);
                }
                break;
            case 12:
            case 5: //Phao
                //Tren
                System.out.println("CHeck phao tren");
                i = y - 1;
                int stepScan = 0;
                while (stepScan < 2) {
                    if (!isInBoard(x, i)) {
                        break;
                    }
                    if (getPieceColorAtPos(x, i) != -1) {
                        stepScan++;
                    }
                    if (stepScan==0) ret |=checkInRange(p, x, i);
                    i--;
                }
                if (stepScan==2) ret |=checkInRange(p, x, i+1);
                 System.out.println("CHeck phao duoi");
                //Duoi
                i = y + 1;
                stepScan = 0;
                while (stepScan < 2) {
                    if (!isInBoard(x, i)) {
                        break;
                    }
                    if (getPieceColorAtPos(x, i) !=  -1) {
                        stepScan++;
                    }
                    if (stepScan==0) ret |=checkInRange(p, x, i);
                    i++;
                }
                if (stepScan==2) ret |=checkInRange(p, x, i-1);
                 System.out.println("CHeck phao trai");
                //Trai
                i = x - 1;
                stepScan = 0;
                while (stepScan < 2) {
                    if (!isInBoard(i, y)) {
                        break;
                    }
                    if (getPieceColorAtPos(i, y) != -1) {
                        stepScan++;
                    }
               if (stepScan==0) ret |=checkInRange(p, i, y);
                    i--;
                }
                if (stepScan==2) ret |=checkInRange(p, i+1, y);
                //Phai
                 System.out.println("CHeck phao phai");
                i = x + 1;
                stepScan = 0;
                while (stepScan < 2) {
                    if (!isInBoard(i, y)) {
                        break;
                    }
                    if (getPieceColorAtPos(i, y) != -1) {
                        stepScan++;
                    }
                  if (stepScan==0) ret |=checkInRange(p, i, y);
                    i++;
                }
                if (stepScan==2) ret |=checkInRange(p, i-1, y);
                break;
            case 11:
            case 4: //Xe
                //Tren
                i = y - 1;
                System.out.println("Check tren");
                while (getPiecesIndex(x,i) == -1) {
               
                    ret |= checkInRange(p, x, i);
                    i--;
                }
                ret |= checkInRange(p, x, i);
                //Duoi
                 System.out.println("Check duoi");
                i = y + 1;
                while (getPiecesIndex(x,i)  == -1) {
                   
                    ret |= checkInRange(p, x, i);
                    i++;
                }
                ret |= checkInRange(p, x, i);
                //Trai
                 System.out.println("Check trai");
                i = x - 1;
                while (getPiecesIndex(i,y)  == -1) {
                
                    ret |= checkInRange(p, i, y);
                    i--;
                }
                ret |= checkInRange(p, i, y);
                //Phai
                 System.out.println("Check phai");
                i = x + 1;
                while (getPiecesIndex(i,y)  == -1) {
                 
                    ret |= checkInRange(p, i, y);
                    i++;
                }
                ret |= checkInRange(p, i, y);
                break;
            case 13: //Tot
                ret |= checkInRange(p, x, y + 1);
                if (y <= 4) {
                    ret |= checkInRange(p, x + 1, y);
                    ret |= checkInRange(p, x - 1, y);
                }
                break;
            case 6: //Tot
                ret |= checkInRange(p, x, y - 1);
                if (y <= 4) {
                    ret |= checkInRange(p, x + 1, y);
                    ret |= checkInRange(p, x - 1, y);
                }
                break;
        }
         return ret;
    }
   
}
