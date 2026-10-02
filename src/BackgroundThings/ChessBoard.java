package BackgroundThings;

import Chesspieces.AbstractChessPiece;
import Chesspieces.King;
import Chesspieces.PieceFactory;
import Chesspieces.PieceImageIcon;
import Chesspieces.Soldier;
import Players.BlackPlayer;
import Players.WhitePlayer;
import Utilities.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChessBoard extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int ROWS = 8;
    public static final int COLS = 8;
    public static final int CELL_SIZE = 50;
    public static final int CHESS_PIECE_NUMBER = 32;
    public static final Font WINNER_FONT = new Font("Arial", Font.BOLD, 50);
    public static final ImageIcon WHITE = Resources.readIcon("/resource/white.jpg");
    public static final ImageIcon BLACK = Resources.readIcon("/resource/black.jpg");

    public static volatile ChessBoard chessBoard = null;
    private static GameTurn gameTurn = GameTurn.WHITE_TURN;
    private static JButton[][] board = new JButton[ROWS][COLS];
    private static final AbstractChessPiece[] all_chess_piece_list =
            new AbstractChessPiece[CHESS_PIECE_NUMBER];

    private ChessBoard() {
        initializeBoard((File) null);
    }

    /** 单例：整个程序共用一块棋盘。 */
    public static ChessBoard getChessBoard() {
        if (chessBoard == null) {
            synchronized (ChessBoard.class) {
                if (chessBoard == null) {
                    chessBoard = new ChessBoard();
                }
            }
        }
        return chessBoard;
    }

    public static JButton[][] getBoard() {
        return board;
    }

    public static GameTurn getGameTurn() {
        return gameTurn;
    }

    public static void setGameTurn(GameTurn turn) {
        gameTurn = turn;
    }

    public static JButton getChessBoardElement(int row, int col) {
        return board[row][col];
    }

    public static void changeChessBoard(int row, int col, ImageIcon things) {
        board[row][col].setIcon(things);
    }

    /** 棋格上是否有棋子。 */
    public static boolean hasPiece(JButton testedBlock) {
        return testedBlock.getIcon() instanceof PieceImageIcon;
    }

    /** 棋格上是否站着指定阵营的棋子。 */
    public static boolean hasPieceOf(JButton testedBlock, boolean whitePiece) {
        return testedBlock.getIcon() instanceof PieceImageIcon pieceImage
                && pieceImage.isWhite() == whitePiece;
    }    /** 找出占据某个格子的棋子。 */
    public static Optional<AbstractChessPiece> findPieceOn(JButton block) {
        for (AbstractChessPiece piece : all_chess_piece_list) {
            if (piece != null && piece.getChess_block() == block) {
                return Optional.of(piece);
            }
        }
        return Optional.empty();
    }

    public static int[] findElement(JButton[][] array, JButton target) {
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
                if (array[row][col] == target) {
                    return new int[]{row, col};
                }
            }
        }
        throw new IllegalArgumentException("元素 " + target + " 不在数组中");
    }

    /** 查找某个棋格在棋盘上的行列。 */
    public static int[] findElement(JButton target) {
        return findElement(board, target);
    }

    private static void changeSide() {
        gameTurn = gameTurn == GameTurn.WHITE_TURN ? GameTurn.BLACK_TURN : GameTurn.WHITE_TURN;
    }

    /** 把棋子登记进全局名册，统一管理。 */
    public static void addChessPiece(AbstractChessPiece chessPiece) {
        for (int i = 0; i < CHESS_PIECE_NUMBER; i++) {
            if (all_chess_piece_list[i] == null) {
                all_chess_piece_list[i] = chessPiece;
                return;
            }
        }
        throw new IllegalStateException("棋子数量超过棋盘上限：" + CHESS_PIECE_NUMBER);
    }

    /** 把已被吃掉的棋子从名册里移除。 */
    public static void removeChessPiece(AbstractChessPiece piece) {
        for (int i = 0; i < CHESS_PIECE_NUMBER; i++) {
            if (all_chess_piece_list[i] == piece) {
                all_chess_piece_list[i] = null;
                return;
            }
        }
    }

    public static boolean isWhiteWin() {
        return kingIsGone(BlackPlayer.pieces());
    }

    public static boolean isBlackWin() {
        return kingIsGone(WhitePlayer.pieces());
    }

    private static boolean kingIsGone(List<AbstractChessPiece> pieces) {
        return pieces.stream().noneMatch(piece -> piece instanceof King);
    }

    /** 按内置的初始摆法重建棋盘（读不到内置资源时退回空盘）。 */
    public void initializeBoard() {
        initializeBoard((File) null);
    }

    /**
     * 按存档重建棋盘：先清空格子，再按存档数字码放子。
     * 存档不存在或读不到时，退回内置的初始摆法。
     */
    public void initializeBoard(File file) {
        try (BufferedReader reader = Resources.openManual(file)) {
            List<String> manual = readManual(reader);
            if (manual.isEmpty()) {
                //用户在文件选择框里取消了（file 为 null），或存档是空文件。
                return;
            }
            initializeBoard(manual);
        } catch (IOException e) {
            System.err.println("读取存档失败，改用内置初始摆法：" + file + " -> " + e.getMessage());
            initializeBoard();
        }
    }

    private void initializeBoard(List<String> manual) {
        removeAll();
        WhitePlayer.clear();
        BlackPlayer.clear();
        clearChessPieces();
        //默认白先行；存档里的手方行（White/Black）会覆盖它。
        //否则恰好只有 64 格的存档会把上一局的回合残留下来。
        gameTurn = GameTurn.WHITE_TURN;

        setLayout(new GridLayout(ROWS, COLS));
        board = new JButton[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = chessBlockMaker(isWhiteBlock(row, col));
                add(board[row][col]);
            }
        }
        initializeChessPieces(manual);
        revalidate();
        repaint();
    }

    /**
     * 棋盘格底色固定由行列决定：偶数行从白格开始，奇数行从黑格开始，(row+col) 为奇数即白底。
     *
     * <p>底色是能从坐标直接算出来的，不能靠比对贴图对象来判断——格子上站着棋子时贴图是
     * 棋子贴图，按贴图判断会一律判成“非白底”，吃子时就出现白格变黑、黑格变白的错乱。</p>
     */
    public static boolean isWhiteBlock(int row, int col) {
        return (row + col) % 2 == 1;
    }

    /** 某一格的空底色贴图。 */
    public static ImageIcon emptyBlockIcon(int row, int col) {
        return isWhiteBlock(row, col) ? WHITE : BLACK;
    }

    /** 空格编码。 */
    private static final int EMPTY_CODE = PieceType.EMPTY_CODE;
    /** 存档中无法识别的格子内容。 */
    private static final int INVALID_CODE = -1;

    private void initializeChessPieces(List<String> manual) {
        int index = 0;
        for (String code : manual) {
            if (index >= ROWS * COLS) {
                break;
            }
            switch (code) {
                case "White" -> setGameTurn(GameTurn.WHITE_TURN);
                case "Black" -> setGameTurn(GameTurn.BLACK_TURN);
                default -> {
                    int archiveCode = parseArchiveCode(code);
                    //只有真正的棋子码才建子，空格的码与识别失败的码都跳过。
                    if (archiveCode >= 0 && archiveCode != EMPTY_CODE) {
                        int row = index / COLS;
                        int col = index % COLS;
                        PieceFactory.create(archiveCode, board[row][col], isWhiteBlock(row, col));
                    }
                    index++;
                }
            }
        }
    }

    /** 解析存档中的一个格子：返回棋子编码，空格返回 {@link #EMPTY_CODE}。 */
    private static int parseArchiveCode(String code) {
        try {
            int archiveCode = Integer.parseInt(code.trim());
            if (archiveCode == EMPTY_CODE) {
                return EMPTY_CODE;
            }
            return PieceType.isKnownCode(archiveCode) ? archiveCode : INVALID_CODE;
        } catch (NumberFormatException e) {
            System.err.println("存档中有无法识别的格子内容：" + code);
            return INVALID_CODE;
        }
    }

    private void clearChessPieces() {
        for (int i = 0; i < CHESS_PIECE_NUMBER; i++) {
            all_chess_piece_list[i] = null;
        }
    }

    /** 把存档读成一串格子编码。 */
    private List<String> readManual(BufferedReader reader) throws IOException {
        ArrayList<String> codes = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            codes.addAll(List.of(line.split(",")));
        }
        return codes;
    }

    private JButton chessBlockMaker(boolean whiteBlock) {
        JButton button = new JButton(whiteBlock ? WHITE : BLACK);
        button.addActionListener(new ClickButtonEvent());
        return button;
    }

    /**
     * 一次点击的完整流程：选中本方棋子、走子、或吃子。
     */
    class ClickButtonEvent implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() instanceof JButton trigger) {
                handleClick(trigger);
            }
        }
    }

    private void handleClick(JButton trigger) {
        boolean currentIsWhite = gameTurn == GameTurn.WHITE_TURN;
        GameScreen.addNotice(currentIsWhite ? "White turn" : "Black turn", null);

        //点到自己（这一回合的）棋子就不尝试走子，直接改为选中它。
        //否则一旦选中了一枚无路可走的棋子，就再也切不走了。
        if (hasPieceOf(trigger, currentIsWhite)) {
            selectPiece(trigger, currentIsWhite);
            return;
        }

        java.util.Deque<AbstractChessPiece> readyToMove = currentIsWhite
                ? WhitePlayer.readyToMove() : BlackPlayer.readyToMove();

        AbstractChessPiece piece = readyToMove.peekFirst();
        if (piece == null || piece.isWhitePiece() != currentIsWhite) {
            //没有选中任何棋子（或栈里残留着对方/已失效的棋子）：清掉后什么都不做。
            readyToMove.clear();
            return;
        }

        boolean moved = hasPiece(trigger)
                ? piece.attack(trigger)
                : piece.move(trigger);

        if (moved) {
            finishTurn(piece);
        }
    }

    private void selectPiece(JButton trigger, boolean currentIsWhite) {
        Optional<AbstractChessPiece> selected = findPieceOn(trigger);
        if (selected.isEmpty()) {
            return;
        }
        AbstractChessPiece piece = selected.get();

        //把本轮已经选中的棋子改回未选中状态，再选中新的这枚：支持随时换子。
        java.util.Deque<AbstractChessPiece> readyToMove = currentIsWhite
                ? WhitePlayer.readyToMove() : BlackPlayer.readyToMove();
        AbstractChessPiece previous = readyToMove.peekFirst();
        if (previous != null && previous != piece) {
            previous.setChoiceState(AbstractChessPiece.ChoiceState.UN_CHOICE);
        }
        readyToMove.clear();

        piece.setChoiceState(AbstractChessPiece.ChoiceState.CHOICE_ABLE);
        if (currentIsWhite) {
            //先把对方残留的待走棋子清掉，保证栈内棋子与当前回合一致。
            BlackPlayer.readyToMove().clear();
            WhitePlayer.add_W_ReadyToMove(piece);
        } else {
            WhitePlayer.readyToMove().clear();
            BlackPlayer.add_B_ReadyToMove(piece);
        }
    }

    private void finishTurn(AbstractChessPiece piece) {
        //走完或吃完后，棋子恢复未选中状态，待走栈也要清空，否则会残留到对方的回合。
        piece.setChoiceState(AbstractChessPiece.ChoiceState.UN_CHOICE);
        WhitePlayer.readyToMove().clear();
        BlackPlayer.readyToMove().clear();
        //只有成功移动之后，兵/卒的首步标记才失效。
        if (piece instanceof Soldier soldier) {
            soldier.setFirstMove(false);
        }
        if (isWhiteWin()) {
            GameScreen.addNotice("White Win!", WINNER_FONT);
        } else if (isBlackWin()) {
            GameScreen.addNotice("Black Win!", WINNER_FONT);
        }
        changeSide();
    }

    public enum GameTurn {
        WHITE_TURN, BLACK_TURN;

        @Override
        public String toString() {
            return switch (this) {
                case WHITE_TURN -> "White";
                case BLACK_TURN -> "Black";
            };
        }
    }

    /**
     * 棋种与它在存档中的数字码。白方占用 0-5，黑方占用 7-12，6 表示空格。
     */
    public enum PieceType {
        Soldier(5, 7),
        Car(0, 8),
        Horse(1, 9),
        Elephant(2, 10),
        Queen(3, 11),
        King(4, 12);

        /** 空格。 */
        public static final int EMPTY_CODE = 6;

        private final int whiteCode;
        private final int blackCode;

        PieceType(int whiteCode, int blackCode) {
            this.whiteCode = whiteCode;
            this.blackCode = blackCode;
        }

        public int archiveCode(boolean whitePiece) {
            return whitePiece ? whiteCode : blackCode;
        }

        public static boolean isKnownCode(int code) {
            if (code == EMPTY_CODE) {
                return true;
            }
            for (PieceType pieceType : values()) {
                if (pieceType.whiteCode == code || pieceType.blackCode == code) {
                    return true;
                }
            }
            return false;
        }

        public static PieceType fromArchiveCode(int code) {
            for (PieceType pieceType : values()) {
                if (pieceType.whiteCode == code || pieceType.blackCode == code) {
                    return pieceType;
                }
            }
            throw new IllegalArgumentException("存档中没有这个棋子编码：" + code);
        }

        public static boolean isWhiteCode(int code) {
            return fromArchiveCode(code).whiteCode == code;
        }
    }

    public enum BackGroundType {
        WhiteBack("white"),
        BlackBack("black");

        private final String fileName;

        BackGroundType(String fileName) {
            this.fileName = fileName;
        }

        /** 资源文件命名中使用的底色名。 */
        public String fileName() {
            return fileName;
        }
    }
}
