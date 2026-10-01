package Chesspieces;

import BackgroundThings.ChessBoard;
import Behaviors.BlackPiece.AttackBehaviors.Elephants_B_AttackBehaviors;
import Behaviors.BlackPiece.Movement.Elephants_B_Movement;
import Behaviors.WhitePiece.AttackBehaviors.Elephants_W_AttackBehaviors;
import Behaviors.WhitePiece.Movement.Elephants_W_Movement;

import javax.swing.*;

public class Elephant extends AbstractChessPiece {

    public Elephant(JButton chess_block, ImageIcon chess_piece) {
        super(chess_block, chess_piece);
        chess_block.setIcon(chess_piece);
        //装配与本棋子颜色匹配的走法与吃法行为。
        create(Elephants_W_Movement::new, Elephants_W_AttackBehaviors::new,
                Elephants_B_Movement::new, Elephants_B_AttackBehaviors::new);
    }

    @Override
    public ChessBoard.PieceType getPieceType() {
        return ChessBoard.PieceType.Elephant;
    }
}
