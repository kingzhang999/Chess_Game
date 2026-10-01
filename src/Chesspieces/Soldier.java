package Chesspieces;

import BackgroundThings.ChessBoard;
import Behaviors.BlackPiece.AttackBehaviors.Soldiers_B_AttackBehaviors;
import Behaviors.BlackPiece.Movement.Soldiers_B_Movement;
import Behaviors.WhitePiece.AttackBehaviors.Soldiers_W_AttackBehaviors;
import Behaviors.WhitePiece.Movement.Soldiers_W_Movement;

import javax.swing.*;

public class Soldier extends AbstractChessPiece {
    private boolean isFirstMove = true;

    public Soldier(JButton chess_block, ImageIcon chess_piece) {
        super(chess_block, chess_piece);
        chess_block.setIcon(chess_piece);
        //装配与本棋子颜色匹配的走法与吃法行为。
        create(Soldiers_W_Movement::new, Soldiers_W_AttackBehaviors::new,
                Soldiers_B_Movement::new, Soldiers_B_AttackBehaviors::new);
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    public boolean getFirstMove() {
        return isFirstMove;
    }

    @Override
    public ChessBoard.PieceType getPieceType() {
        return ChessBoard.PieceType.Soldier;
    }
}
