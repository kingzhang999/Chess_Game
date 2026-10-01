package BackgroundThings;

import Chesspieces.PieceImageIcon;
import Utilities.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static BackgroundThings.ChessBoard.CELL_SIZE;
import static BackgroundThings.ChessBoard.COLS;
import static BackgroundThings.ChessBoard.ROWS;

public class GameScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    //存档与截图写在 jar 旁边的这两个目录里；初始摆法是打进 jar 的只读资源。
    private static final String MANUAL_DIRECTORY = "saves";
    private static final String PHOTO_DIRECTORY = "photos";
    private static final DateTimeFormatter NOTICE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分ss秒");

    private static JTextArea notice_board;

    public GameScreen() {
        setTitle("Chess Board");
        setLayout(new BorderLayout());
        setSize(COLS * CELL_SIZE + 180, ROWS * CELL_SIZE + 60);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeScreen();//棋盘初始化必须放在setVisible前面，否则棋盘无法加载。

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameScreen::new);
    }

    public static void addNotice(String notice, Font font) {
        if (notice_board == null) {
            //事件面板还没创建（例如棋盘脱离窗口单独使用时），只跳过日志，不影响行棋。
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        notice_board.setFont(font);
        notice_board.append(now.format(NOTICE_TIME_FORMAT) + ": \n");
        notice_board.append(notice + "\n");
        //将字体恢复成默认字体。
        notice_board.setFont(null);
    }

    private void initializeScreen() {
        getContentPane().add(ChessBoard.getChessBoard(), BorderLayout.CENTER);
        setJMenuBar(createMenuBar());
        getContentPane().add(createEventPanel(), BorderLayout.WEST);
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenu photoMenu = new JMenu("photo");
        photoMenu.setMnemonic(KeyEvent.VK_P);
        menuBar.add(photoMenu);

        JMenuItem saveItem = new JMenuItem("save", KeyEvent.VK_N);
        saveItem.addActionListener(new SaveGame());
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("load", KeyEvent.VK_O);
        loadItem.addActionListener(new LoadGame());
        fileMenu.add(loadItem);

        JMenuItem photoItem = new JMenuItem("take photo", KeyEvent.VK_X);
        photoItem.addActionListener(new TakePhoto());
        photoMenu.add(photoItem);

        return menuBar;
    }

    public JPanel createEventPanel() {
        notice_board = new JTextArea(10, 14);
        JScrollPane event_scroll_pane = new JScrollPane(notice_board);
        JPanel event_panel = new JPanel(new BorderLayout());
        JButton clear_button = new JButton("Clear");

        //设置按钮事件
        clear_button.addActionListener(e -> notice_board.setText(""));

        //设置文本框不可编辑和自动换行
        notice_board.setEditable(false);
        notice_board.setLineWrap(true);
        notice_board.setWrapStyleWord(true);

        //设置滚动面板
        event_scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        event_scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //设置事件面板
        event_panel.add(event_scroll_pane, BorderLayout.CENTER);
        event_panel.add(clear_button, BorderLayout.SOUTH);

        return event_panel;
    }

    private static class SaveGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(Resources.writableDirectory(MANUAL_DIRECTORY));
            if (fileChooser.showSaveDialog(ChessBoard.getChessBoard()) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File file = fileChooser.getSelectedFile();
            if (file == null) {
                //用户取消了选择，直接结束。
                return;
            }
            saveFile(file);
            addNotice("Save game %s okay!".formatted(file.getName()), null);
        }

        private void saveFile(File file) {
            List<String> data = new ArrayList<>();
            for (JButton[] chess_blocks : ChessBoard.getBoard()) {
                for (JButton chess_block : chess_blocks) {
                    if (chess_block.getIcon() instanceof PieceImageIcon pieceImage) {
                        data.add(String.valueOf(
                                pieceImage.getPieceType().archiveCode(pieceImage.isWhite())));
                    } else {
                        data.add(String.valueOf(ChessBoard.PieceType.EMPTY_CODE));
                    }
                }
            }
            //保存当前轮到谁下
            data.add(ChessBoard.getGameTurn().toString());
            writeToFile(file, data);
        }

        private void writeToFile(File file, List<String> data) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Resources.outputFile(file.getPath())))) {
                for (int i = 1; i < data.size() + 1; i++) {
                    writer.write(data.get(i - 1));
                    if (i < data.size() && i % 8 != 0) {
                        writer.write(",");
                    }
                    if (i % 8 == 0) {
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
            JFileChooser fileChooser = new JFileChooser(Resources.writableDirectory(MANUAL_DIRECTORY));
            if (fileChooser.showOpenDialog(ChessBoard.getChessBoard()) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File manual = fileChooser.getSelectedFile();
            if (manual == null) {
                //用户取消了选择，直接结束。
                return;
            }

            //按存档重建棋盘：清空旧棋子、重新摆放。
            ChessBoard.getChessBoard().initializeBoard(manual);
            //打印通知
            addNotice("Load game %s okay!".formatted(manual.getName()), null);
        }
    }

    private class TakePhoto implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (askCustomSavePath()) {
                savePhotoToChosenPath();
            } else {
                savePhotoToDefaultPath();
            }
        }

        private boolean askCustomSavePath() {
            //弹出确认对话框，询问用户是否要自定义保存路径
            return JOptionPane.showConfirmDialog(GameScreen.this, "是否要自定义保存路径？", "保存图片",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        }

        private void savePhotoToChosenPath() {
            JFileChooser fileChooser = new JFileChooser(Resources.writableDirectory(PHOTO_DIRECTORY));
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Images", "png"));
            if (fileChooser.showSaveDialog(GameScreen.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile == null) {
                //用户取消了选择，直接结束。
                return;
            }
            savePhoto(ensurePngSuffix(selectedFile.getPath()));
        }

        private void savePhotoToDefaultPath() {
            savePhoto(String.format("%s/%d.png", PHOTO_DIRECTORY, System.currentTimeMillis()));
        }

        private String ensurePngSuffix(String filePath) {
            return filePath.endsWith(".png") ? filePath : filePath + ".png";
        }

        private void savePhoto(String filePath) {
            //创建BufferedImage对象，并将窗口内容绘制到其中。
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            getContentPane().printAll(g2d);
            g2d.dispose();
            try {
                //截图是运行时产物，父目录不存在时自动创建。
                ImageIO.write(image, "png", Resources.outputFile(filePath));
                JOptionPane.showMessageDialog(GameScreen.this, "图片已保存到: " + filePath, "保存成功",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(GameScreen.this, "图片保存失败: " + ex.getMessage(), "保存失败",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
