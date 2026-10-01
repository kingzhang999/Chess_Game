package Behaviors.common;

import BackgroundThings.ChessBoard;
import Behaviors.MoveBehavior;
import Chesspieces.AbstractChessPiece;
import Chesspieces.PieceAppearance;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 走子行为的公共实现：白方与黑方、各棋种原本各自复制了一整套“扫描可走格 + 落子”的代码，
 * 这里统一收拢。子类只需要实现 {@link #scanTargets()} 说明本方棋子能到达哪些格子。
 *
 * <p>可走格的定义：沿该棋子的走法前进，遇到第一个棋子就停下（该格不可走），
 * 途中所有空格都可以走。</p>
 */
public abstract class PieceMoveBehavior implements MoveBehavior {
    protected final AbstractChessPiece piece;
    private final List<JButton> walkableBlocks = new ArrayList<>();
    private int x;
    private int y;

    protected PieceMoveBehavior(AbstractChessPiece piece) {
        this.piece = piece;
        updateLocation();
    }

    @Override
    public boolean move(JButton target_chess_block) {
        updateWalkableBlocks();

        if (piece.getChoiceState() != AbstractChessPiece.ChoiceState.CHOICE_ABLE
                || !piece.isOwnPiece()) {
            return false;
        }
        return walkableBlocks.contains(target_chess_block) && executeMove(target_chess_block);
    }

    /** 棋子重新定位后，由棋子统一通知，刷新缓存的行列坐标。 */
    public final void updateLocation() {
        int[] position = ChessBoard.findElement(piece.getChess_block());
        x = position[0];
        y = position[1];
    }

    /** 扫描该棋子当前能到达的所有格子（只包含空格）。 */
    protected abstract List<JButton> scanTargets();

    private void updateWalkableBlocks() {
        if (piece.isOwnPiece()) {
            //每次走子前重新扫描，避免上一次的扫描结果（棋子被挡住后）继续生效。
            walkableBlocks.clear();
            walkableBlocks.addAll(scanTargets());
        }
    }

    private boolean executeMove(JButton target_chess_block) {
        int[] target = ChessBoard.findElement(target_chess_block);
        int target_x = target[0];
        int target_y = target[1];

        if (target_x < 0 || target_x >= ChessBoard.ROWS
                || target_y < 0 || target_y >= ChessBoard.COLS) {
            return false;
        }

        ImageIcon targetImageIcon = (ImageIcon) target_chess_block.getIcon();
        boolean targetIsWhiteBlock = targetImageIcon == ChessBoard.WHITE;

        //把棋子离开的格子还原成它来之前的样子。
        ChessBoard.changeChessBoard(x, y, piece.getChess_block_iconImage());
        //记下目标格当前的贴图，将来棋子离开这里时据此还原。
        piece.setChess_block_iconImage(targetImageIcon);
        piece.setChess_block(target_chess_block);

        ChessBoard.changeChessBoard(target_x, target_y,
                PieceAppearance.imageOf(piece.getPieceType(), piece.isWhitePiece(), targetIsWhiteBlock));
        ChessBoard.getChessBoardElement(target_x, target_y).repaint();

        updateLocation();
        return true;
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

    protected static boolean isEmpty(int row, int col) {
        return !ChessBoard.hasPiece(blockAt(row, col));
    }

    /**
     * 沿一个方向一直前进，把途中空格加入结果，遇到棋子就停止。
     * 直线与斜线的滑行棋子共用这一段扫描。
     */
    protected void scanDirection(List<JButton> blocks, int rowStep, int colStep) {
        int row = row() + rowStep;
        int col = col() + colStep;
        while (canReach(row, col)) {
            if (!isEmpty(row, col)) {
                break;
            }
            blocks.add(blockAt(row, col));
            row += rowStep;
            col += colStep;
        }
    }
}
