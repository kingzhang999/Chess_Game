package Behaviors.BlackPiece.Movement;

import Behaviors.common.PieceMoveBehavior;
import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** 黑方卒的走法：向前一格，首步可走两格。 */
public class Soldiers_B_Movement extends PieceMoveBehavior {

    public Soldiers_B_Movement(AbstractChessPiece piece) {
        super(piece);
    }

    @Override
    protected List<JButton> scanTargets() {
        List<JButton> targets = new ArrayList<>();
        scanDirection(targets, -1, 0);
        return targets;
    }
}
