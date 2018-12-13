package algorithm;

import gui.Gui;
import model.Chessboard;
import model.ChessboardElement;

import javax.swing.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class AlgorithmFirstBest {

    private Set<ChessboardElement> closedList = new HashSet<>();
    private LinkedList<ChessboardElement> openList = new LinkedList<>();
    private LinkedList<ChessboardElement> finalPath = new LinkedList<>();

    private Chessboard chessboard;
    private Gui gui;
    private int delay;

    public AlgorithmFirstBest(Chessboard chessboard, Gui gui, int delay) throws Exception {
        this.chessboard = chessboard;
        this.gui = gui;
        this.delay = delay;

        runAlgorithm();
    }

    private void runAlgorithm() throws Exception {
        int i = this.chessboard.getLength()-1;
        int numberOfCalculateOpenNodes = 0;
        int numberOfCalculateHeuristic = 0;

        while(finalPath.size() < this.chessboard.getLength()) {

            for (int j = 0; j < this.chessboard.getLength(); j++) {
                if(this.chessboard.getChessboard()[j][i].isFree()) {
                    numberOfCalculateHeuristic++;
                    calculateHeuristicForBoardElement(j, i);
                    addToOpenList(this.chessboard.getChessboard()[j][i]);
                }
            }

            if(chooseBest(i)) {
                numberOfCalculateOpenNodes++;
                if (finalPath.size() > 0 && finalPath.getLast().getY() - 1 >= 0)
                    i = finalPath.getLast().getY() - 1;
            } else {
                if (finalPath.size() > 0 && finalPath.getLast().getY() + 1 <= this.chessboard.getWidth() - 1)
                    i = finalPath.getLast().getY() + 1;
            }

            updateGui();

            if(this.openList.size() == 0) {
                JOptionPane.showMessageDialog(null, "Nie znaleziono rozwiązania.", "Błąd", 0);
                break;
            }
        }

        updateGui();

        JOptionPane.showMessageDialog(null, "Rozwinięto " + numberOfCalculateOpenNodes + " wierzchołków." +
                "\nFunkcję heurystykę obliczono: "+ numberOfCalculateHeuristic, "Znaleziono rozwiązanie", 1);
    }

    private void calculateHeuristicForBoardElement(int x, int y) throws Exception {
        //Funkcja heurystyczna sprawdza dwa rzędy do góry liczbę wolnych pól i na tej podstawie wyznacza wartość funkcji heurystycznej dla pola.
        //Co rząd wyżej bonus +(liczba pól w rzędzie)*numer poziomu
        //numberOfLevels +1 zawsze
        int numberOfLevels = 2;
        int rating = 0;
        int levelBonus = this.chessboard.getWidth()*(this.chessboard.getWidth()-y);

        setQueenWithValid(x, y);

        for (int i = y-1; i >= 0 && i >= i-numberOfLevels; i--) {
            for (int j = 0; j < this.chessboard.getLength(); j++) {
                if(this.chessboard.getChessboard()[j][i].isFree()){
                    rating++;
                }
            }
        }

        this.chessboard.getChessboard()[x][y].setRating(rating + levelBonus);
        revertLastQueen();
    }

    private boolean chooseBest(int startY) throws Exception {
        int bestX = -1;
        int bestY = -1;
        double bestRating = -1;

        int i = startY;

        for (ChessboardElement e: this.openList) {
            if(
                    e.isFree()
                    && e.getRating() > bestRating
                    && !isClosed(e.getX(), e.getY())
            ){
                bestX = e.getX();
                bestY = e.getY();
                bestRating = e.getRating();
            }
        }

        if(bestRating != -1) {
            setQueenWithValid(bestX, bestY);
            addToCloseSetElement(finalPath.getLast());
            this.removeCloseChessboardElements(finalPath.getLast(), 1);
            return true;
        } else {
            revertLastQueen();
            return false;
        }
    }

    private void addToCloseSetElement(ChessboardElement element) {
        this.removeCloseChessboardElements(element, 2);
        this.closedList.add(element);
        this.openList.remove(element);
    }

    private void removeCloseChessboardElements(ChessboardElement elementToAdd, int stage) {
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

    private void revertLastQueen() {
        finalPath.removeLast();
        this.chessboard.revertLastQueenOnChessboard(finalPath);
    }

    private void setQueenWithValid(int x, int y) throws Exception {
        if(x >= this.chessboard.getLength() || y >= this.chessboard.getWidth() || x < 0 || y < 0)
            throw new Exception("Pole nie istnieje na szachownicy");

        if(this.chessboard.isQueenOnThisField(finalPath, x, y))
            throw new Exception("Na polu (" + x + " " + y + ") znajduje się już hetman");

        //Ustaw hetmana
        finalPath.add(this.chessboard.getChessboard()[x][y]);
        this.chessboard.setQueenWithoutValid(x, y);
    }

    private void updateGui() {
        if(delay != 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gui.setActualCheesboard(finalPath, chessboard);
    }

    private void addToOpenList(ChessboardElement e) {
        boolean exists = false;

        for (ChessboardElement elem : openList) {
            if(e.getX() == elem.getX() && e.getY() == elem.getY()) {
                exists = true;
                break;
            }
        }

        if(!exists)
            openList.add(e);
    }
}
