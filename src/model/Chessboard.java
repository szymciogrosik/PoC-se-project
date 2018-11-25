package model;

import java.util.LinkedList;
import java.util.List;

public class Chessboard {

    private int length;
    private int width;

    private ChessboardElement[][] chessboard;

    public Chessboard(int length, int width) {
        this.length = length;
        this.width = width;

        buildChessboard();
    }

    private void buildChessboard() {
        this.chessboard = new ChessboardElement[this.length][this.width];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.length; j++) {
                this.chessboard[j][i] = new ChessboardElement(j, i);
            }
        }
    }
}
