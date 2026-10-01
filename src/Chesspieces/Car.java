package Chesspieces;

import BackgroundThings.ChessBoard;
import Behaviors.BlackPiece.AttackBehaviors.Cars_B_AttackBehaviors;
import Behaviors.BlackPiece.Movement.Cars_B_Movement;
import Behaviors.WhitePiece.AttackBehaviors.Cars_W_AttackBehaviors;
import Behaviors.WhitePiece.Movement.Cars_W_Movement;

import javax.swing.*;

public class Car extends AbstractChessPiece {

    public Car(JButton chess_block, ImageIcon chess_piece) {
        super(chess_block, chess_piece);
        chess_block.setIcon(chess_piece);
        //装配与本棋子颜色匹配的走法与吃法行为。
        create(Cars_W_Movement::new, Cars_W_AttackBehaviors::new,
                Cars_B_Movement::new, Cars_B_AttackBehaviors::new);
    }

    @Override
    public ChessBoard.PieceType getPieceType() {
        return ChessBoard.PieceType.Car;
    }
}
