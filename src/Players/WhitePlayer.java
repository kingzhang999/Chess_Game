package Players;

import Chesspieces.AbstractChessPiece;

public class WhitePlayer {
    public static int white_pieces_count = 0;
    public static final int CHESS_PIECE_NUMBER = 16;
    public static AbstractChessPiece[] white_pieces_list = new AbstractChessPiece[CHESS_PIECE_NUMBER];
    public static ArrayStack<AbstractChessPiece> w_readyToMove = new ArrayStack<>(16);

    public static boolean isInWhitePiecesList(AbstractChessPiece piece) {
        for (AbstractChessPiece p : white_pieces_list) {
            //System.out.println("W_ChessPiece: "+p);//test
            //System.out.println("W_target_Found: "+piece);//test
            if(p == piece){
                //System.out.println("W_Piece_Found: "+p);//test
                return true;
            }
        }
        return false;
    }

    public static void add_W_Piece(AbstractChessPiece piece) {
        white_pieces_list[white_pieces_count] = piece;
        white_pieces_count++;
    }

    public static void remove_W_Piece(AbstractChessPiece piece) {
        for (int i = 0; i < CHESS_PIECE_NUMBER; i++) {
            //在for循环中使用CHESS_PIECE_NUMBER作为循环次数是为了避免删除了一个在数组靠后位置的元素时，
            //white_pieces_count自动减一，如果后面需要删除更靠后的元素时，无法循环到那个元素。以至于导致错误。
            if (white_pieces_list[i] == piece) {
                //System.out.println("W_Piece_Removed: "+piece);//test
                white_pieces_list[i] = null;
                System.gc();//垃圾回收
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
