package Players;

import Chesspieces.AbstractChessPiece;

public class WhitePlayer {
    public static int white_pieces_count = 0;
    public static AbstractChessPiece[] white_pieces_list = new AbstractChessPiece[16];
    public static ArrayStack<AbstractChessPiece> w_readyToMove = new ArrayStack<>(16);
    public static final WhitePlayer whitePlayer = new WhitePlayer();
    public static boolean isInWhitePiecesList(AbstractChessPiece piece) {
        for (AbstractChessPiece p : white_pieces_list) {
            return p == piece;
        }
        return false;
    }
    public static void add_W_Piece(AbstractChessPiece piece) {
        white_pieces_list[white_pieces_count] = piece;
        white_pieces_count++;
    }
    public static void remove_W_Piece(AbstractChessPiece piece) {
        for (int i = 0; i < white_pieces_count; i++) {
            if (white_pieces_list[i] == piece) {
                white_pieces_list[i] = null;
                white_pieces_count--;
                break;
            }
        }
    }
    public static void add_W_ReadyToMove(AbstractChessPiece piece) {
        w_readyToMove.push(piece);
    }
    public static AbstractChessPiece getNext_W_ReadyToMove() {
        return w_readyToMove.pop();
    }
}
