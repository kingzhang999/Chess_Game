package Chesspieces;

import BackgroundThings.ChessBoard;
import Behaviors.BlackPiece.AttackBehaviors.Queens_B_AttackBehaviors;
import Behaviors.BlackPiece.Movement.Queens_B_Movement;
import Behaviors.WhitePiece.AttackBehaviors.Queens_W_AttackBehaviors;
import Behaviors.WhitePiece.Movement.Queens_W_Movement;

import javax.swing.*;

public class Queen extends AbstractChessPiece {

    public Queen(JButton chess_block, ImageIcon chess_piece) {
        super(chess_block, chess_piece);
        chess_block.setIcon(chess_piece);
        //装配与本棋子颜色匹配的走法与吃法行为。
        create(Queens_W_Movement::new, Queens_W_AttackBehaviors::new,
                Queens_B_Movement::new, Queens_B_AttackBehaviors::new);
    }

    @Override
    public ChessBoard.PieceType getPieceType() {
        return ChessBoard.PieceType.Queen;
    }
}
