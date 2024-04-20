import javax.swing.*;
import java.awt.*;

public class GameScreen extends JFrame{
    public GameScreen(){
        setTitle("Chess Board");
        setLayout(new BorderLayout());
        setSize(ChessBoard.COLS * ChessBoard.CELL_SIZE, ChessBoard.ROWS * ChessBoard.CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeScreen();

        setVisible(true);
    }
    private void initializeScreen(){
        getContentPane().add(new ChessBoard(),BorderLayout.CENTER);
    }
    public static void main(String[] args) {
        new GameScreen();
    }
}
