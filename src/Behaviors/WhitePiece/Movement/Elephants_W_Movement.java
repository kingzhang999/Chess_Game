package Behaviors.WhitePiece.Movement;

import Behaviors.common.PieceMoveBehavior;
import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** 白方象的走法：斜向四个方向直行。 */
public class Elephants_W_Movement extends PieceMoveBehavior {

    public Elephants_W_Movement(AbstractChessPiece piece) {
        super(piece);
    }

    @Override
    protected List<JButton> scanTargets() {
        List<JButton> targets = new ArrayList<>();
        scanDirection(targets, 1, 1);
        scanDirection(targets, 1, -1);
        scanDirection(targets, -1, 1);
        scanDirection(targets, -1, -1);
        return targets;
    }
}
