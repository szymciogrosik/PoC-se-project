package model;

public class ChessboardElement {
    private int x;
    private int y;
    private double rating;
    private boolean isFree = true;
//    private boolean isQueen = false;

    public ChessboardElement(int x, int y, double rating) {
        this.x = x;
        this.y = y;
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) { this.rating = rating; }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

//    public boolean isQueen() {
//        return isQueen;
//    }
//
//    public void setQueen(boolean queen) {
//        isQueen = queen;
//    }
}
