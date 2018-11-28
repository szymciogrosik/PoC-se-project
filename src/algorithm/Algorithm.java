package algorithm;

import gui.Gui;
import model.Chessboard;

public class Algorithm {
    private Chessboard chessboard;
    private Gui gui;

    public Algorithm(Chessboard chessboard, Gui gui) {
        this.chessboard = chessboard;
        this.gui = gui;

        try {
            this.chessboard.setQueenWithValid(5,3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateGui();
    }

    private void updateGui() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gui.setActualCheesboard(chessboard);
    }
}
