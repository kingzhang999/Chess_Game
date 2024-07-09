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
        getContentPane().add(ChessBoard.getChessBoard(),BorderLayout.CENTER);
        //setJMenuBar(createMenuBar());//没想好怎么写菜单栏，先注释掉
    }

    /*public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu;
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
    }*/

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameScreen::new);
    }

    /*private class SaveGame implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showSaveDialog(ChessBoard.getChessBoard());
            saveFile(fileChooser.getSelectedFile());
        }

        private void saveFile(File file) {

        }
    }*/
}
