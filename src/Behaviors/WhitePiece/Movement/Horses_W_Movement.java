package Behaviors.WhitePiece.Movement;

import Behaviors.common.PieceMoveBehavior;
import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** 白方马的走法：八个“日”字形落点。 */
public class Horses_W_Movement extends PieceMoveBehavior {

    private static final int[][] HOPS = {
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2},
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1}
    };

    public Horses_W_Movement(AbstractChessPiece piece) {
        super(piece);
    }

    @Override
    protected List<JButton> scanTargets() {
        List<JButton> targets = new ArrayList<>();
        for (int[] hop : HOPS) {
            int row = row() + hop[0];
            int col = col() + hop[1];
            if (canReach(row, col) && isEmpty(row, col)) {
                targets.add(blockAt(row, col));
            }
        }
        return targets;
    }
}
