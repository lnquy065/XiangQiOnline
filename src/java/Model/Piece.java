package Model;

import java.awt.Point;


public class Piece {
    private int pIndex;
    private Point pPosition;
    private Point pNewPostion;
    public static int RED = 0;
    public static int BLACK = 1;
    public static int NONE = -1;
    public static int NULL = -2;
    
    public Piece(){}
    
    public Piece(int pIndex, Point pPos, Point nPos) {
        this.pIndex = pIndex;
        pPosition = pPos;
        pNewPostion = nPos;
    }
    
    public Piece(Piece p) {
        pIndex = p.getPieceIndex();
        pPosition = p.getPiecePosition();
        pNewPostion = p.getPieceNewPosition();
    }
    
    public static int getColor(int pIndex) {
            if (pIndex < 7 && pIndex >= 0)
        return RED;
    else if (pIndex >= 7)
        return BLACK;
    return pIndex;
    }
    
    public int getColor() {
            if (pIndex < 7 && pIndex >= 0)
        return RED;
    else if (pIndex >= 7)
        return BLACK;
    return pIndex;
    }
    
    public Piece invert() {
        Piece pr = new Piece();
        pr.setpIndex(InvertIndex(pIndex));
        pr.setpPosition(InvertPosition(pPosition));
        pr.setpNewPostion(InvertPosition(pNewPostion));
        return pr;
    }
    
    public static int InvertIndex(int p) {
        if (p==-1) return p;
        if (p>6) return p-7;
        return p+7;
    }
    
    public static Point InvertPosition(Point p) {
        //Chuyen vi tri tren ban co
        //x: 0 -> 8, 1 -> 7, 2->6, 3->5, 4->4:  newX = 8-X;
        //y: 0 -> 9: newY = 9-Y;
        return new Point(8-p.x, 9-p.y);
    }
    
    public void setpIndex(int pIndex) {
        this.pIndex = pIndex;
    }

    public void setpPosition(Point pPosition) {
        this.pPosition = pPosition;
    }

    public void setpNewPostion(Point pNewPostion) {
        this.pNewPostion = pNewPostion;
    }

    public int getPieceIndex() {
        return pIndex;
    }
    
    public Point getPiecePosition() {
        return pPosition;
    }
    
    public Point getPieceNewPosition() {
        return pNewPostion;
    }
}
