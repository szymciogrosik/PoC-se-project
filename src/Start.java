import gui.Gui;
import algorithm.Algorithm;
import model.Chessboard;

import javax.swing.*;

public class Start {
    private final static int boardSideLength = 10;
    private static Chessboard chessboard;
    private static Gui gui;

    public Start() {
        chessboard = new Chessboard(boardSideLength, boardSideLength);
        gui = new Gui(chessboard.getLength(), chessboard.getWidth());
        gui.setActualCheesboard(chessboard);
        JFrame f = new JFrame("Projekt systemy eksperckie");
        f.add(gui.getGui());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);
        f.pack();
        f.setMinimumSize(f.getSize());
        f.setVisible(true);

        Algorithm algorithm = new Algorithm(chessboard, gui);
    }

    public static void main(String[] args) {
        new Start();
    }
}
