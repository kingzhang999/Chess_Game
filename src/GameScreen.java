import BackgroundThings.ChessBoard;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JFrame{
    public GameScreen(){
        setTitle("Chess Board");
        setLayout(new BorderLayout());
        setSize(ChessBoard.COLS * ChessBoard.CELL_SIZE, ChessBoard.ROWS * ChessBoard.CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeScreen();//棋盘初始化必须放在setVisible前面，否则棋盘无法加载。

        setVisible(true);
    }
    private void initializeScreen(){
        getContentPane().add(ChessBoard.chessBoard,BorderLayout.CENTER);
    }
    public static void main(String[] args) {
        new GameScreen();
    }
}
