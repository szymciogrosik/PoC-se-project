package algorithm;

import gui.Gui;
import model.Chessboard;
import model.ChessboardElement;

import java.util.LinkedList;

public class Algorithm {
    private Chessboard chessboard;
    private Gui gui;
    private LinkedList<LinkedList<ChessboardElement>> chessboardBadPath = new LinkedList<>();

    public Algorithm(Chessboard chessboard, Gui gui) throws Exception {
        this.chessboard = chessboard;
        this.gui = gui;

        for (int i = this.chessboard.getWidth()-1; i >= 0; i--) {
            for (int j = 0; j < this.chessboard.getLength(); j++) {
                if(this.chessboard.getChessboard()[j][i].isFree())
                    calculateHeuristicForBoardElement(j, i);
            }
            this.chessboard.printMatrix();
            chooseBest();
            updateGui();
        }
    }

    private void updateGui() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gui.setActualCheesboard(chessboard);
    }

    private void calculateHeuristicForBoardElement(int x, int y) throws Exception {
        //Funkcja heurystyczna sprawdza dwa rzędy do góry liczbę wolnych pól i na tej podstawie wyznacza wartość funkcji heurystycznej dla pola.
        //Co rząd wyżej bonus +(liczba pól w rzędzie)*numer poziomu
        //numberOfLevels +1 zawsze
        int numberOfLevels = 2;
        int rating = 0;

        this.chessboard.setQueenWithValid(x, y);
        for (int i = this.chessboard.getWidth()-1; i >= 0; i--) {
            for (int j = 0; j < this.chessboard.getLength(); j++) {
                if(this.chessboard.getChessboard()[j][i].isFree()){
                    rating++;
                }
            }
        }

        this.chessboard.getChessboard()[x][y].setRating(rating);
        this.chessboard.revertLastQueen();
    }

    private void chooseBest() throws Exception {
        int bestX = -1;
        int bestY = -1;
        double bestRating = -1;

        for (int i = this.chessboard.getWidth()-1; i >= 0; i--) {
            for (int j = 0; j < this.chessboard.getLength(); j++) {
                if(this.chessboard.getChessboard()[j][i].isFree() && this.chessboard.getChessboard()[j][i].getRating() > bestRating){
                    bestX = this.chessboard.getChessboard()[j][i].getX();
                    bestY = this.chessboard.getChessboard()[j][i].getY();
                    bestRating = this.chessboard.getChessboard()[j][i].getRating();
                }
            }
        }

        System.out.println(bestX + " " + bestY);

        if(bestRating != -1)
            this.chessboard.setQueenWithValid(bestX, bestY);
        else
            System.out.println("brak");
    }
}
