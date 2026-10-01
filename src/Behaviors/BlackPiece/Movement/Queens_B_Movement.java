package Behaviors.BlackPiece.Movement;

import Behaviors.common.PieceMoveBehavior;
import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** 黑方后的走法：横竖与斜向共八个方向直行。 */
public class Queens_B_Movement extends PieceMoveBehavior {

    public Queens_B_Movement(AbstractChessPiece piece) {
        super(piece);
    }

    @Override
    protected List<JButton> scanTargets() {
        List<JButton> targets = new ArrayList<>();
        scanDirection(targets, 1, 0);
        scanDirection(targets, -1, 0);
        scanDirection(targets, 0, 1);
        scanDirection(targets, 0, -1);
        scanDirection(targets, 1, 1);
        scanDirection(targets, 1, -1);
        scanDirection(targets, -1, 1);
        scanDirection(targets, -1, -1);
        return targets;
    }
}
