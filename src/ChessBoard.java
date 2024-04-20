import javax.swing.*;
import java.awt.*;

public class ChessBoard extends JPanel {
    public static final int ROWS = 8;
    public static final int COLS = 8;
    public static final int CELL_SIZE = 50;
    public static final int WHITE = 1;
    public static final int BLACK = 2;

    private JButton[][] board;

    public ChessBoard() {
        setLayout(new GridLayout(COLS,ROWS));
        board = new JButton[ROWS][COLS];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int cols=0;cols<COLS;cols++){
            for (int rows=0;rows<ROWS;rows++){
                if (cols % 2 == 0){
                    if(rows % 2 == 0){
                        board[cols][rows] = chessBlockMaker(BLACK);
                    }else {
                        board[cols][rows] = chessBlockMaker(WHITE);
                    }
                }else {
                    if(rows % 2 == 0){
                        board[cols][rows] = chessBlockMaker(WHITE);
                    }else {
                        board[cols][rows] = chessBlockMaker(BLACK);
                    }
                }
                add(board[cols][rows]);
            }
        }

    }

    private JButton chessBlockMaker(int color) {
        if(color == WHITE){
            return new JButton(new ImageIcon("resource/white.jpg"));
        }else if (color == BLACK){
            return new JButton(new ImageIcon("resource/black.jpg"));
        }
        throw new IllegalArgumentException("Error Color!");
    }
}
