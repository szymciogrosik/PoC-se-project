package model;

import java.util.LinkedList;

public class Chessboard {

    private int length;
    private int width;
    private final double defaultRating = 0.0;

    private ChessboardElement[][] chessboard;

    public Chessboard(int length, int width) {
        this.length = length;
        this.width = width;

        buildChessboard();
    }

    private void buildChessboard() {
        this.chessboard = new ChessboardElement[this.length][this.width];

        for (int i = 0; i < this.width; i++)
            for (int j = 0; j < this.length; j++)
                this.chessboard[j][i] = new ChessboardElement(j, i, this.defaultRating);
    }

    public void setQueenWithoutValid(int x, int y) {
        //Ustaw na liniach poziomych
        for (int j = 0; j < this.length; j++)
            this.chessboard[j][y].setFree(false);

        //Ustaw na liniach pionowych
        for (int i = 0; i < this.width; i++)
            this.chessboard[x][i].setFree(false);

        //Ustaw na przekątnych
        int xTemp = x;
        int yTemp = y;
        while (xTemp < this.length && yTemp < this.width) {
            this.chessboard[xTemp][yTemp].setFree(false);
            xTemp++;
            yTemp++;
        }

        xTemp = x;
        yTemp = y;
        while (xTemp >= 0 && yTemp >= 0) {
            this.chessboard[xTemp][yTemp].setFree(false);
            xTemp--;
            yTemp--;
        }

        xTemp = x;
        yTemp = y;
        while (xTemp >= 0 && yTemp < this.width) {
            this.chessboard[xTemp][yTemp].setFree(false);
            xTemp--;
            yTemp++;
        }

        xTemp = x;
        yTemp = y;
        while (xTemp < this.length && yTemp >= 0) {
            this.chessboard[xTemp][yTemp].setFree(false);
            xTemp++;
            yTemp--;
        }
    }

    public boolean isQueenOnThisField(LinkedList<ChessboardElement> openList, int x, int y) {
        boolean isQueen = false;
        for (ChessboardElement e : openList) {
            if(e.getX() == x && e.getY() == y) {
                isQueen = true;
                break;
            }
        }
        return isQueen;
    }

    public void revertLastQueenOnChessboard(LinkedList<ChessboardElement> openList) {
        this.clearChessboard();
        for (ChessboardElement e: openList) {
            try {
                this.setQueenWithoutValid(e.getX(), e.getY());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearChessboard() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.length; j++) {
                this.chessboard[j][i].setFree(true);
            }
        }
    }

    public void printMatrix() {
        for (int i = 0; i < this.width; i++) {
            System.out.print("[");
            for (int j = 0; j < this.length; j++)
                System.out.print(chessboard[j][i].getRating()+" ");
            System.out.print("]\n");
        }
    }

    public void printMatrixFree() {
        for (int i = 0; i < this.width; i++) {
            System.out.print("[ ");
            for (int j = 0; j < this.length; j++) {
                if(chessboard[j][i].isFree())
                    System.out.print(1 + " ");
                else
                    System.out.print(0 + " ");
            }
            System.out.print(" ]\n");
        }
    }

    public ChessboardElement[][] getChessboard() {
        return chessboard;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }
}
