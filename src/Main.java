import model.Chessboard;

public class Main {

    public static void main(String[] args) {
        Chessboard chessboard = new Chessboard(11, 11);
        try {
            chessboard.setQueen(5,5);
            chessboard.setQueen(8,8);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        chessboard.revertLastQueen();
        chessboard.printMatrixFree();
    }
}
