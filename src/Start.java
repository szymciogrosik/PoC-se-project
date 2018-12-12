import algorithm.AlgorithmFirstBest;
import gui.Gui;
import model.Chessboard;

import javax.swing.*;

public class Start {
    public static int boardSideLength;
    public static int delay;
    private static Chessboard chessboard;
    private static Gui gui;

    private Start() {
        boardSideLength = Integer.parseInt(JOptionPane.showInputDialog(null, "Podaj długość krawędzi planszy"));
        delay = Integer.parseInt(JOptionPane.showInputDialog(null, "Podaj opóźnienie (milisekundy)"));

        chessboard = new Chessboard(boardSideLength, boardSideLength);
        gui = new Gui(chessboard.getLength(), chessboard.getWidth());
        JFrame f = new JFrame("Projekt systemy eksperckie");
        f.add(gui.getGui());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationByPlatform(true);
        f.pack();
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        f.setSize(800, 800);

        try {
            new AlgorithmFirstBest(chessboard, gui, delay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Start();
    }
}
