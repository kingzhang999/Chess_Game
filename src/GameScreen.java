import BackgroundThings.ChessBoard;
import Chesspieces.BlackPiece;
import Chesspieces.WhitePiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static BackgroundThings.ChessBoard.*;

public class GameScreen extends JFrame{
    public GameScreen(File startGameFile){
        setTitle("Chess Board");
        setLayout(new BorderLayout());
        setSize(COLS * ChessBoard.CELL_SIZE, ROWS * ChessBoard.CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeScreen(startGameFile);//棋盘初始化必须放在setVisible前面，否则棋盘无法加载。

        setVisible(true);
    }

    private void initializeScreen(File transfer_file){
        getContentPane().add(ChessBoard.getChessBoard(transfer_file),BorderLayout.CENTER);
        setJMenuBar(createMenuBar());//没想好怎么写菜单栏，先注释掉
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        //Add a menu item.
        menuItem = new JMenuItem("save", KeyEvent.VK_N);
        menuItem.addActionListener(new SaveGame());
        menu.add(menuItem);

        return menuBar;
    }

    public static void main(String[] args) {
        if(args.length == 0){
            SwingUtilities.invokeLater(() -> new GameScreen(DEFAULT_MANUAL_FILE));
        }else{
            SwingUtilities.invokeLater(() -> new GameScreen(new File(args[0])));
        }
    }

    private static class SaveGame implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser("resource/manuals");
            fileChooser.showSaveDialog(ChessBoard.chessBoard);
            saveFile(fileChooser.getSelectedFile());
        }

        private void saveFile(File file) {
            ArrayList<String> data = new ArrayList<>();
            for (JButton[] chess_blocks : ChessBoard.board){
                for (JButton chess_block : chess_blocks){
                    if (chess_block.getIcon() instanceof WhitePiece whitePiece){
                        switch (whitePiece.getPieceType()) {
                            case Soldier -> data.add("5");
                            case Car -> data.add("0");
                            case Horse -> data.add("1");
                            case Elephant -> data.add("2");
                            case King -> data.add("4");
                            case Queen -> data.add("3");
                        }
                    }else if(chess_block.getIcon() instanceof BlackPiece blackPiece){
                        switch (blackPiece.getPieceType()) {
                            case Soldier -> data.add("7");
                            case Car -> data.add("8");
                            case Horse -> data.add("9");
                            case Elephant -> data.add("10");
                            case King -> data.add("12");
                            case Queen -> data.add("11");
                        }
                    }else{
                        data.add("6");
                    }
                }
            }
            writeToFile(file, data);
        }

        private void writeToFile(File file, ArrayList<String> data) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (int i = 1; i < data.size() +1; i++) {
                    writer.write(data.get(i - 1));
                    if (i < data.size() && i % 8 != 0) {
                        writer.write(",");
                    }
                    if (i % 8 == 0){
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }
        }

    }
}
