package algorithm;

import gui.Gui;
import model.Chessboard;
import model.ChessboardElement;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class AlgorithmFirstBest {

    private Set<ChessboardElement> closedList = new HashSet<>();
    private Set<ChessboardElement> openList = new HashSet<>();

    private Chessboard chessboard;
    private Gui gui;

    public AlgorithmFirstBest(Chessboard chessboard, Gui gui) {
        this.chessboard = chessboard;
        this.gui = gui;

        runAlgorithm();
    }

    private void runAlgorithm() {
        openList.add(chessboard.getChessboard()[chessboard.getLength()-1][chessboard.getWidth()-1]);
    }
}
