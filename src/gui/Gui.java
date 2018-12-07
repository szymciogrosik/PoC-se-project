package gui;

import model.Chessboard;
import model.ChessboardElement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Gui {
    private final JPanel gui = new JPanel(new BorderLayout(5, 5));
    private final int defaultBorderWidth = 500;
    private JButton[][] chessBoardSquares;
    private int widthBorderElement;
    private int heightBorderElement;
    private int width;
    private int height;
    private ImageIcon queenIcon;
    private ImageIcon defaultIcon;

    public Gui(int width, int height) {
        this.width = width;
        this.height = height;

        this.widthBorderElement = this.heightBorderElement = this.defaultBorderWidth/this.width;
        this.defaultIcon = new ImageIcon(new BufferedImage(this.widthBorderElement, this.heightBorderElement, BufferedImage.TYPE_INT_ARGB));
        this.queenIcon = new ImageIcon("C:\\Users\\Szymon\\_PROJEKTY\\SE_PROJEKT\\src\\resources\\queen.png");
        this.queenIcon = this.resizeIcon(this.queenIcon);

        initializeGui(width, height);
    }

    private void initializeGui(int width, int height) {
        chessBoardSquares = new JButton[width][height];
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        tools.add(new JButton("Start"));
        tools.add(new JButton("Reset"));
        tools.addSeparator();

        JPanel chessBoard = new JPanel(new GridLayout(width, height));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);

        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < height; ii++) {
            for (int jj = 0; jj < width; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                b.setIcon(defaultIcon);
                if ((jj % 2 == 1 && ii % 2 == 1) || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                b.setEnabled(false);
                chessBoardSquares[jj][ii] = b;
            }
        }

        for (int ii = 0; ii < height; ii++) {
            for (int jj = 0; jj < width; jj++) {
                chessBoard.add(chessBoardSquares[jj][ii]);
            }
        }
    }

    public final JComponent getGui() {
        return gui;
    }

    public void setActualCheesboard(Chessboard cheesboard) {
        clearBoard();
        for (int ii = 0; ii < height; ii++) {
            for (int jj = 0; jj < width; jj++) {
                if (!cheesboard.getChessboard()[jj][ii].isFree()) {
                    chessBoardSquares[jj][ii].setBackground(Color.PINK);
                }
            }
        }

        for (ChessboardElement e : cheesboard.getChessboardQueensList()) {
            chessBoardSquares[e.getX()][e.getY()].setBackground(Color.RED);
            chessBoardSquares[e.getX()][e.getY()].setIcon(this.queenIcon);
        }
    }

    private void clearBoard() {
        for (int ii = 0; ii < height; ii++) {
            for (int jj = 0; jj < width; jj++) {
                chessBoardSquares[jj][ii].setIcon(defaultIcon);
                if ((jj % 2 == 1 && ii % 2 == 1) || (jj % 2 == 0 && ii % 2 == 0)) {
                    chessBoardSquares[jj][ii].setBackground(Color.WHITE);
                } else {
                    chessBoardSquares[jj][ii].setBackground(Color.BLACK);
                }
            }
        }
    }

    private ImageIcon resizeIcon(ImageIcon imageIcon) {
        Image img = imageIcon.getImage();
        Image newImg = img.getScaledInstance(this.widthBorderElement, this.heightBorderElement,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}
