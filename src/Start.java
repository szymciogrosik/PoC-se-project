import algorithm.AlgorithmFirstBest;
import gui.Gui;
import model.Chessboard;

import javax.swing.*;

public class Start {
    private final static int boardSideLength = 20;
    private static Chessboard chessboard;
    private static Gui gui;

    private Start() {
        chessboard = new Chessboard(boardSideLength, boardSideLength);
        gui = new Gui(chessboard.getLength(), chessboard.getWidth());
        JFrame f = new JFrame("Projekt systemy eksperckie");
        f.add(gui.getGui());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);
        f.pack();
        f.setMinimumSize(f.getSize());
        f.setVisible(true);

        try {
            new AlgorithmFirstBest(chessboard, gui);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Start();
    }
}
