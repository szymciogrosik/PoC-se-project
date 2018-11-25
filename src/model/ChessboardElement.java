package model;

public class ChessboardElement {
    private int x;
    private int y;
    private double rating = -1;

    public ChessboardElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
