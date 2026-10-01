package Behaviors.WhitePiece.Movement;

import Behaviors.common.PieceMoveBehavior;
import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** 白方车的走法：横竖四个方向直行。 */
public class Cars_W_Movement extends PieceMoveBehavior {

    public Cars_W_Movement(AbstractChessPiece piece) {
        super(piece);
    }

    @Override
    protected List<JButton> scanTargets() {
        List<JButton> targets = new ArrayList<>();
        scanDirection(targets, 1, 0);
        scanDirection(targets, -1, 0);
        scanDirection(targets, 0, 1);
        scanDirection(targets, 0, -1);
        return targets;
    }
}
