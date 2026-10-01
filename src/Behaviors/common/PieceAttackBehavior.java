package Behaviors.common;

import BackgroundThings.ChessBoard;
import Behaviors.AttackBehavior;
import Chesspieces.AbstractChessPiece;
import Chesspieces.PieceAppearance;
import Players.BlackPlayer;
import Players.WhitePlayer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 吃子行为的公共实现。与 {@link PieceMoveBehavior} 对称：
 * 子类复用 {@link PieceMoveBehavior#scanDirection} 那套扫描，说明本方棋子能到达哪些格子，
 * 基类再筛出其中被敌方棋子占据的格子，并完成吃子。
 */
public abstract class PieceAttackBehavior implements AttackBehavior {
    protected final AbstractChessPiece piece;
    private final List<JButton> attackableBlocks = new ArrayList<>();
    private int x;
    private int y;

    protected PieceAttackBehavior(AbstractChessPiece piece) {
        this.piece = piece;
        updateLocation();
    }

    @Override
    public boolean attack(JButton being_attacked_chess_block) {
        updateAttackableBlocks();

        if (attackableBlocks.contains(being_attacked_chess_block)) {
            return executeCapture(being_attacked_chess_block);
        }
        return false;
    }

    /** 棋子重新定位后，由棋子统一通知，刷新缓存的行列坐标。 */
    public final void updateLocation() {
        int[] position = ChessBoard.findElement(piece.getChess_block());
        x = position[0];
        y = position[1];
    }

    /** 扫描该棋子按走法能到达的所有格子（空格与敌方棋子所在格都算）。 */
    protected abstract List<JButton> scanTargets();

    private void updateAttackableBlocks() {
        if (piece.isOwnPiece()) {
            attackableBlocks.clear();
            for (JButton block : scanTargets()) {
                //只有被对方棋子占据的格子才能吃。
                if (isOccupiedByOpponent(block)) {
                    attackableBlocks.add(block);
                }
            }
        }
    }

    /** 该格上是否站着与当前棋子不同颜色的棋子。 */
    public final boolean isOccupiedByOpponent(JButton block) {
        return ChessBoard.findPieceOn(block).map(this::isOpponent).orElse(false);
    }

    private boolean isOpponent(AbstractChessPiece other) {
        return other.isWhitePiece() != piece.isWhitePiece();
    }

    private boolean executeCapture(JButton target_chess_block) {
        int[] target = ChessBoard.findElement(target_chess_block);
        int target_x = target[0];
        int target_y = target[1];
        boolean targetIsWhiteBlock = (ImageIcon) target_chess_block.getIcon() == ChessBoard.WHITE;

        //被吃掉的敌方棋子所在格将来要还原成空格，先按底色算出来。
        ImageIcon emptyBlockIcon = targetIsWhiteBlock ? ChessBoard.WHITE : ChessBoard.BLACK;

        //把棋子离开的格子还原成它来之前的样子。
        ChessBoard.changeChessBoard(x, y, piece.getChess_block_iconImage());
        piece.setChess_block_iconImage(emptyBlockIcon);

        //移除目标格上的敌方棋子。
        ChessBoard.findPieceOn(target_chess_block).ifPresent(this::deletePiece);

        piece.setChess_block(target_chess_block);
        ChessBoard.changeChessBoard(target_x, target_y,
                PieceAppearance.imageOf(piece.getPieceType(), piece.isWhitePiece(), targetIsWhiteBlock));
        ChessBoard.getChessBoardElement(target_x, target_y).repaint();

        updateLocation();
        return true;
    }

    private void deletePiece(AbstractChessPiece captured) {
        if (captured.isWhitePiece()) {
            WhitePlayer.remove_W_Piece(captured);
        } else {
            BlackPlayer.remove_B_Piece(captured);
        }
        ChessBoard.removeChessPiece(captured);
    }

    protected int row() {
        return x;
    }

    protected int col() {
        return y;
    }

    protected static boolean canReach(int row, int col) {
        return row >= 0 && row < ChessBoard.ROWS && col >= 0 && col < ChessBoard.COLS;
    }

    protected static JButton blockAt(int row, int col) {
        return ChessBoard.getChessBoardElement(row, col);
    }

    /**
     * 沿一个方向一直前进：途中空格只是通道，遇到棋子时，若是敌方棋子则该格可吃，
     * 无论如何都在此停止。直线与斜线的滑行棋子共用这一段扫描。
     */
    protected void scanDirection(List<JButton> blocks, int rowStep, int colStep) {
        int row = row() + rowStep;
        int col = col() + colStep;
        while (canReach(row, col)) {
            if (ChessBoard.hasPiece(blockAt(row, col))) {
                blocks.add(blockAt(row, col));
                break;
            }
            row += rowStep;
            col += colStep;
        }
    }
}
