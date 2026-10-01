package Behaviors.WhitePiece.Movement;

import Behaviors.common.PieceMoveBehavior;
import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** 白方兵的走法：向前一格，首步可走两格。 */
public class Soldiers_W_Movement extends PieceMoveBehavior {

    public Soldiers_W_Movement(AbstractChessPiece piece) {
        super(piece);
    }

    @Override
    protected List<JButton> scanTargets() {
        List<JButton> targets = new ArrayList<>();
        scanDirection(targets, 1, 0);
        return targets;
    }
}
