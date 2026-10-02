package Behaviors.common;

import BackgroundThings.ChessBoard;
import Behaviors.AttackBehavior;
import Chesspieces.AbstractChessPiece;
import Chesspieces.PieceAppearance;
import Chesspieces.PieceImageIcon;
import Players.BlackPlayer;
import Players.WhitePlayer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 吃子行为的公共实现：所有棋种共用的“定位 + 吃子落格 + 移除被吃棋子”。
 * 子类只需要实现 {@link #scanTargets()} 说明本枚棋子按走法能到达哪些格子
 * （空格与敌方棋子所在格都算），基类再筛出其中被对方棋子占据的格子。
 */
public abstract class PieceAttackBehavior extends DirectionalScanner implements AttackBehavior {
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
            //与走法同理：扫描前先按棋子当前所在格刷新坐标。
            updateLocation();
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

        //底色由坐标算出来。目标格上站着敌方棋子，贴图是棋子贴图，
        //按贴图判断会一律判成“非白底”，于是白格上的吃子会显示成黑格。
        boolean targetIsWhiteBlock = ChessBoard.isWhiteBlock(target_x, target_y);
        //这里同时更新“格子的贴图”和“棋子自身的贴图”，两者必须一致：
        //只换格子会让棋子带着旧底色的贴图继续走，之后再进出黑白格就全乱了。
        PieceImageIcon appearance =
                PieceAppearance.imageOf(piece.getPieceType(), piece.isWhitePiece(), targetIsWhiteBlock);

        //把棋子离开的格子还原成它本来的底色。
        ChessBoard.changeChessBoard(x, y, ChessBoard.emptyBlockIcon(x, y));

        //移除目标格上的敌方棋子。
        ChessBoard.findPieceOn(target_chess_block).ifPresent(this::deletePiece);

        piece.setChess_piece(appearance);
        piece.setChess_block(target_chess_block);
        ChessBoard.changeChessBoard(target_x, target_y, appearance);
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

    /**
     * 沿一个方向一直前进：途中空格只是通道，遇到棋子时把该格加入结果，然后停止。
     */
    protected void scanDirection(List<JButton> blocks, int rowStep, int colStep) {
        scanUntilPiece(blocks, row(), col(), rowStep, colStep);
    }
}
