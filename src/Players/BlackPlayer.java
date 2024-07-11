package Players;

import Chesspieces.AbstractChessPiece;

public class BlackPlayer {
    public static int black_pieces_count = 0;
    public static final int CHESS_PIECE_NUMBER = 16;
    public static AbstractChessPiece[] black_pieces_list = new AbstractChessPiece[CHESS_PIECE_NUMBER];
    public static ArrayStack<AbstractChessPiece> b_readyToMove = new ArrayStack<>(16);

    public static boolean isInBlackPieceList(AbstractChessPiece piece){
        for (AbstractChessPiece chessPiece : black_pieces_list) {
            //System.out.println("B_ChessPiece: "+chessPiece);//test
            //System.out.println("B_target_Found: "+piece);//test
            if(chessPiece == piece){
                //System.out.println("B_Piece_Found: "+chessPiece);//test
                return true;
            }
        }
        return false;
    }

    public static void add_B_Piece(AbstractChessPiece piece){
        black_pieces_list[black_pieces_count] = piece;
        black_pieces_count++;
    }

    public static void remove_B_Piece(AbstractChessPiece piece){
        for (int i = 0; i < CHESS_PIECE_NUMBER; i++) {
            //在for循环中使用CHESS_PIECE_NUMBER作为循环次数是为了避免删除了一个在数组靠后位置的元素时，
            //black_pieces_count自动减一，如果后面需要删除更靠后的元素时，无法循环到那个元素。以至于导致错误。
            if (black_pieces_list[i] == piece) {
                //System.out.println("B_Piece_Removed: "+piece);//test
                black_pieces_list[i] = null;
                System.gc();//垃圾回收
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

    public static void removeAll_B_ChessPieces() {
        for(int i = 0; i < CHESS_PIECE_NUMBER; i++){
            black_pieces_list[i] = null;
            System.gc();
            black_pieces_count = 0;
        }
    }//此方法暂时废弃
}
