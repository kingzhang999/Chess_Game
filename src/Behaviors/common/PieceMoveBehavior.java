package Behaviors.common;

import BackgroundThings.ChessBoard;
import Behaviors.MoveBehavior;
import Chesspieces.AbstractChessPiece;
import Chesspieces.PieceAppearance;
import Chesspieces.PieceImageIcon;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 走子行为的公共实现：所有棋种共用的“定位 + 走子落格 + 还原格子底色”。
 * 子类只需要实现 {@link #scanTargets()} 说明本枚棋子当前能走到哪些空格。
 */
public abstract class PieceMoveBehavior extends DirectionalScanner implements MoveBehavior {
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
            //扫描前必须先按棋子当前所在格刷新坐标：走子与吃子是两套行为对象，
            //吃子只更新了自己那份坐标，走法这里不重新定位就会从旧位置开始扫描。
            updateLocation();
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

        //底色由坐标算出来，不能按贴图判断（目标格平时是空底色贴图，但也可能已被别的棋子占用）。
        boolean targetIsWhiteBlock = ChessBoard.isWhiteBlock(target_x, target_y);
        //这里同时更新“格子的贴图”和“棋子自身的贴图”：只换格子会让棋子带着旧底色的贴图继续走，
        //后续进出黑白格时贴图与格子就对不上了。
        PieceImageIcon appearance =
                PieceAppearance.imageOf(piece.getPieceType(), piece.isWhitePiece(), targetIsWhiteBlock);

        //把棋子离开的格子还原成它本来的底色。
        ChessBoard.changeChessBoard(x, y, ChessBoard.emptyBlockIcon(x, y));
        piece.setChess_piece(appearance);
        piece.setChess_block(target_chess_block);
        ChessBoard.changeChessBoard(target_x, target_y, appearance);
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

    /** 沿一个方向一直前进，把途中空格加入结果，遇到棋子就停止。 */
    protected void scanDirection(List<JButton> blocks, int rowStep, int colStep) {
        scanEmptySquares(blocks, row(), col(), rowStep, colStep);
    }
}
