package Players;

import Chesspieces.AbstractChessPiece;

public class BlackPlayer {
    public static int black_pieces_count = 0;
    public static AbstractChessPiece[] black_pieces_list = new AbstractChessPiece[16];
    public static ArrayStack<AbstractChessPiece> b_readyToMove = new ArrayStack<>(16);
    public static final BlackPlayer blackPlayer = new BlackPlayer();
    public static boolean isInBlackPieceList(AbstractChessPiece piece){
        for (AbstractChessPiece chessPiece : black_pieces_list) {
            return chessPiece == piece;
        }
        return false;
    }
    public static void add_B_Piece(AbstractChessPiece piece){
        black_pieces_list[black_pieces_count] = piece;
        black_pieces_count++;
    }
    public static void remove_B_Piece(AbstractChessPiece piece){
        for (int i = 0; i < black_pieces_count; i++) {
            if (black_pieces_list[i] == piece) {
                black_pieces_list[i] = null;
                black_pieces_count--;
                break;
            }
        }
    }
    public static void add_B_ReadyToMove(AbstractChessPiece piece){
        b_readyToMove.push(piece);
    }
    public static AbstractChessPiece getNext_B_ReadyToMove(){
        return b_readyToMove.pop();
    }
}
