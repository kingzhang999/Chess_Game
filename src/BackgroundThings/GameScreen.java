package BackgroundThings;

import Chesspieces.BlackPiece;
import Chesspieces.WhitePiece;
import Players.BlackPlayer;
import Players.WhitePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static BackgroundThings.ChessBoard.COLS;
import static BackgroundThings.ChessBoard.ROWS;

public class GameScreen extends JFrame{

    private static JTextArea notice_board;
    public GameScreen(){
        setTitle("Chess Board");
        setLayout(new BorderLayout());
        setSize(COLS * ChessBoard.CELL_SIZE+180, ROWS * ChessBoard.CELL_SIZE+60);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeScreen();//棋盘初始化必须放在setVisible前面，否则棋盘无法加载。

        setVisible(true);
    }

    private void initializeScreen(){
        getContentPane().add(ChessBoard.getChessBoard(),BorderLayout.CENTER);
        setJMenuBar(createMenuBar());
        getContentPane().add(createEventPanel(), BorderLayout.WEST);
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        JMenuItem menuItem2;

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

        //Add a menu item.
        menuItem2 = new JMenuItem("load", KeyEvent.VK_O);
        menuItem2.addActionListener(new LoadGame());
        menu.add(menuItem2);

        return menuBar;
    }

    public JScrollPane createEventPanel() {
        notice_board = new JTextArea(10,14);
        JScrollPane event_scroll_pane = new JScrollPane(notice_board);
        //设置文本框不可编辑和自动换行
        notice_board.setEditable(false);
        notice_board.setLineWrap(true);
        notice_board.setWrapStyleWord(true);

        //设置滚动面板
        event_scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        event_scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return event_scroll_pane;
    }

    public static void addNotice(String notice,Font font){
        // 获取当前日期和时间
        LocalDateTime now = LocalDateTime.now();
        // 创建一个日期时间格式器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分ss秒");
        // 格式化当前日期和时间
        String time = now.format(formatter);
        notice_board.setFont(font);
        notice_board.append(time+": \n");
        notice_board.append(notice);
        //将字体恢复成默认字体。
        notice_board.setFont(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameScreen::new);
    }

    private static class SaveGame implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser("resource/manuals");
            fileChooser.showSaveDialog(ChessBoard.chessBoard);
            File files = fileChooser.getSelectedFile();
            saveFile(files);
            //打印通知
            addNotice("Save game %s okay!\n".formatted(files.getName()),null);
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

    private static class LoadGame implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //打开文件选取窗口
            JFileChooser fileChooser = new JFileChooser("resource/manuals");
            fileChooser.showOpenDialog(ChessBoard.getChessBoard());

            //清除之前的棋子
            ChessBoard.getChessBoard().removeAllChessPiecesAndBlocks();
            WhitePlayer.removeAll_W_ChessPieces();
            BlackPlayer.removeAll_B_ChessPieces();
            ChessBoard.getChessBoard().removeAll();

            //加载新棋子
            File manual = fileChooser.getSelectedFile();
            ChessBoard.getChessBoard().initializeBoard(manual);
            ChessBoard.getChessBoard().revalidate();
            ChessBoard.getChessBoard().repaint();
            //打印通知
            addNotice("Load game %s okay!\n".formatted(manual.getName()),null);
        }
    }
}
