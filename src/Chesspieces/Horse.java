package Chesspieces;

import BackgroundThings.ChessBoard;
import Behaviors.BlackPiece.AttackBehaviors.Horses_B_AttackBehaviors;
import Behaviors.BlackPiece.Movement.Horses_B_Movement;
import Behaviors.WhitePiece.AttackBehaviors.Horses_W_AttackBehaviors;
import Behaviors.WhitePiece.Movement.Horses_W_Movement;

import javax.swing.*;

public class Horse extends AbstractChessPiece {

    public Horse(JButton chess_block, ImageIcon chess_piece) {
        super(chess_block, chess_piece);
        chess_block.setIcon(chess_piece);
        //装配与本棋子颜色匹配的走法与吃法行为。
        create(Horses_W_Movement::new, Horses_W_AttackBehaviors::new,
                Horses_B_Movement::new, Horses_B_AttackBehaviors::new);
    }

    @Override
    public ChessBoard.PieceType getPieceType() {
        return ChessBoard.PieceType.Horse;
    }
}
