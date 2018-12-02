package algorithm;

import gui.Gui;
import model.Chessboard;
import model.ChessboardElement;

import java.util.LinkedList;
import java.util.List;

public class Algorithm {
    private Chessboard chessboard;
    private Gui gui;
    private LinkedList<LinkedList<ChessboardElement>> chessboardBadPath = new LinkedList<>();

    public Algorithm(Chessboard chessboard, Gui gui) throws Exception {
        this.chessboard = chessboard;
        this.gui = gui;

        int startValue = 0;
        int i = this.chessboard.getWidth()-1;

        while(this.chessboard.getChessboardQueensList().size() < 20) {
            for (int j = 0; j < this.chessboard.getLength(); j++) {
                if(this.chessboard.getChessboard()[j][i].isFree())
                    calculateHeuristicForBoardElement(j, i);
            }

            this.chessboard.printMatrix();
            System.out.println("Wybrane Y: " + i);
            if(!chooseBest(i)) {
                this.chessboardBadPath.add(new LinkedList<>(this.chessboard.getChessboardQueensList()));
                i = this.chessboard.getChessboardQueensList().getLast().getY();
                this.chessboard.revertLastQueen();
                System.out.println("szukane Y: " + i);
            } else {
                if(i > 0) i--;
            }
            updateGui();
        }
    }

    private void updateGui() {
        try {
            Thread.sleep(500);
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

    private boolean chooseBest(int startY) throws Exception {
        int bestX = -1;
        int bestY = -1;
        double bestRating = -1;

//        for (int i = startY; i >= 0; i--) {
        int i = startY;
        this.chessboard.printMatrixFree();
            for (int j = 0; j < this.chessboard.getLength(); j++) {
                if(
                    this.chessboard.getChessboard()[j][i].isFree()
                    && this.chessboard.getChessboard()[j][i].getRating() > bestRating
                    && !patchExists(j, i)
                ){
                    bestX = this.chessboard.getChessboard()[j][i].getX();
                    bestY = this.chessboard.getChessboard()[j][i].getY();
                    bestRating = this.chessboard.getChessboard()[j][i].getRating();
                }
            }
        System.out.println("Best: " + bestX + ", " + bestY + ", " + bestRating);
//        }

        System.out.println(bestX + " " + bestY);

        if(bestRating != -1) {
            this.chessboard.setQueenWithValid(bestX, bestY);
            return true;
        } else {
            return false;
        }
    }

    private boolean patchExists(int x, int y){
        boolean returnedVal = false;

        for (List<ChessboardElement> badPath : this.chessboardBadPath) {
            boolean checkPossibleSolution = true;
            int lastBadElemIndex = -1;
            if(this.chessboard.getChessboardQueensList().size()+1 == badPath.size()) {
                for (ChessboardElement e : this.chessboard.getChessboardQueensList()) {
                    ChessboardElement tempBadElem = badPath.get(this.chessboard.getChessboardQueensList().indexOf(e));

                    lastBadElemIndex = badPath.indexOf(tempBadElem);
                    if (!e.isTheSameXY(tempBadElem.getX(), tempBadElem.getY())) {
                        checkPossibleSolution = false;
                        break;
                    }
                }
            }

            if(checkPossibleSolution && lastBadElemIndex != -1) {

                if(badPath.size() == (lastBadElemIndex+2)) {
                    ChessboardElement tempBadElem = badPath.get(lastBadElemIndex+1);

                    if(tempBadElem.isTheSameXY(x, y))
                        returnedVal = true;
                    else
                        returnedVal = false;
                }
            }
        }

        return returnedVal;
    }
}
