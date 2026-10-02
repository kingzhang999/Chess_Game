package Behaviors.BlackPiece.Movement;

import Behaviors.common.PawnMoveBehavior;
import Chesspieces.AbstractChessPiece;

/**
 * 黑方卒的走法：向前一格；首步且两格都为空时可走两格。
 * 不能后退，也不能像车那样一直前进。
 */
public class Soldiers_B_Movement extends PawnMoveBehavior {

    public Soldiers_B_Movement(AbstractChessPiece piece) {
        super(piece, -1);
    }
}
