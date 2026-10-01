package Chesspieces;

import BackgroundThings.ChessBoard;
import Behaviors.BlackPiece.AttackBehaviors.Kings_B_AttackBehaviors;
import Behaviors.BlackPiece.Movement.Kings_B_Movement;
import Behaviors.WhitePiece.AttackBehaviors.Kings_W_AttackBehaviors;
import Behaviors.WhitePiece.Movement.Kings_W_Movement;

import javax.swing.*;

public class King extends AbstractChessPiece {

    public King(JButton chess_block, ImageIcon chess_piece) {
        super(chess_block, chess_piece);
        chess_block.setIcon(chess_piece);
        //装配与本棋子颜色匹配的走法与吃法行为。
        create(Kings_W_Movement::new, Kings_W_AttackBehaviors::new,
                Kings_B_Movement::new, Kings_B_AttackBehaviors::new);
    }

    @Override
    public ChessBoard.PieceType getPieceType() {
        return ChessBoard.PieceType.King;
    }
}
