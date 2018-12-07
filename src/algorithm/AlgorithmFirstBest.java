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

    public AlgorithmFirstBest(Chessboard chessboard, Gui gui) throws Exception {
        this.chessboard = chessboard;
        this.gui = gui;

        runAlgorithm();
    }

    private void runAlgorithm() throws Exception {
        int i = this.chessboard.getLength()-1;

        while(this.chessboard.getChessboardQueensList().size() < this.chessboard.getLength()) {
            for (int j = 0; j < this.chessboard.getLength(); j++) {
                if(this.chessboard.getChessboard()[j][i].isFree()) {
                    //Todo: Tutaj liczymy liczbę wywołań funkcji
                    calculateHeuristicForBoardElement(j, i);
                }
            }

            if(chooseBest(i)){
                i--;
            } else {
                i++;
            }

            updateGui();

            if(this.openList.size() == 0) {
                System.out.println("Brak rozwiazania");
                break;
            }
        }
    }

    private void calculateHeuristicForBoardElement(int x, int y) throws Exception {
        //Funkcja heurystyczna sprawdza dwa rzędy do góry liczbę wolnych pól i na tej podstawie wyznacza wartość funkcji heurystycznej dla pola.
        //Co rząd wyżej bonus +(liczba pól w rzędzie)*numer poziomu
        //numberOfLevels +1 zawsze
        int numberOfLevels = 2;
        int rating = 0;

        this.chessboard.setQueenWithValid(x, y);

//        for (int i = this.chessboard.getWidth()-1; i >= 0; i--) {
            for (int j = 0; j < this.chessboard.getLength(); j++) {
                if(y-1 >= 0 && this.chessboard.getChessboard()[j][y-1].isFree()){
                    rating++;
                }
            }
//        }

        this.chessboard.getChessboard()[x][y].setRating(rating);
        this.chessboard.revertLastQueen();
    }

    private boolean chooseBest(int startY) throws Exception {
        int bestX = -1;
        int bestY = -1;
        double bestRating = -1;

        int i = startY;
        for (int j = 0; j < this.chessboard.getLength(); j++) {
            if(
                    this.chessboard.getChessboard()[j][i].isFree()
                            && this.chessboard.getChessboard()[j][i].getRating() > bestRating
                            && !isClosed(j, i)
            ){
                bestX = this.chessboard.getChessboard()[j][i].getX();
                bestY = this.chessboard.getChessboard()[j][i].getY();
                bestRating = this.chessboard.getChessboard()[j][i].getRating();
            }
        }
        System.out.println("Best: " + bestX + ", " + bestY + ", " + bestRating);

        if(bestRating != -1) {
            this.chessboard.setQueenWithValid(bestX, bestY);
            this.removeCloseCheesboardElements(this.chessboard.getChessboardQueensList().getLast(), 1);
            this.openList.add(this.chessboard.getChessboardQueensList().getLast());
            return true;
        } else {
            this.openList.remove(this.chessboard.getChessboardQueensList().getLast());
            addToCloseSetElement(this.chessboard.getChessboardQueensList().getLast());
            this.chessboard.revertLastQueen();
            return false;
        }
    }

    private void addToCloseSetElement(ChessboardElement element) {
        this.removeCloseCheesboardElements(element, 2);
        this.closedList.add(element);
    }

    private void removeCloseCheesboardElements(ChessboardElement elementToAdd, int stage) {
        Set<ChessboardElement> setToRemove = new HashSet<>();

        for (ChessboardElement e : this.closedList) {
            if(e.getY() <= elementToAdd.getY()-stage)
                setToRemove.add(e);
        }

        for (ChessboardElement e : setToRemove)
            this.closedList.remove(e);
    }

    private boolean isClosed(int x, int y) {
        for (ChessboardElement e : this.closedList) {
            if(e.getX() == x && e.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private void updateGui() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gui.setActualCheesboard(chessboard);
    }
}
