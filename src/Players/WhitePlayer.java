package Players;

import Chesspieces.AbstractChessPiece;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 白方：棋子名册与“已选中待走”的棋子。
 *
 * <p>名册改用 List 管理，取代原先“定长数组 + 手工计数”的写法：
 * 删除棋子后不再需要手工维护计数，也不会出现计数与数组内容不一致的问题。</p>
 */
public final class WhitePlayer {
    public static final int CHESS_PIECE_NUMBER = 16;
    private static final List<AbstractChessPiece> PIECES = new ArrayList<>(CHESS_PIECE_NUMBER);
    private static final Deque<AbstractChessPiece> READY_TO_MOVE = new ArrayDeque<>(CHESS_PIECE_NUMBER);

    private WhitePlayer() {
    }

    public static List<AbstractChessPiece> pieces() {
        return PIECES;
    }

    public static boolean contains(AbstractChessPiece piece) {
        return PIECES.contains(piece);
    }

    public static void add_W_Piece(AbstractChessPiece piece) {
        PIECES.add(piece);
    }

    public static void remove_W_Piece(AbstractChessPiece piece) {
        PIECES.remove(piece);
    }

    public static void add_W_ReadyToMove(AbstractChessPiece piece) {
        READY_TO_MOVE.push(piece);
    }

    public static AbstractChessPiece getNext_W_ReadyToMove() {
        return READY_TO_MOVE.pop();
    }

    public static Deque<AbstractChessPiece> readyToMove() {
        return READY_TO_MOVE;
    }

    /** 重新开局时清空白方状态。 */
    public static void clear() {
        PIECES.clear();
        READY_TO_MOVE.clear();
    }
}
