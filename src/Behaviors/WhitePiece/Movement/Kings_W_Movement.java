package Behaviors.WhitePiece.Movement;

import Behaviors.common.PieceMoveBehavior;
import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** 白方王的走法：周围八格各走一步。 */
public class Kings_W_Movement extends PieceMoveBehavior {

    private static final int[][] STEPS = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    public Kings_W_Movement(AbstractChessPiece piece) {
        super(piece);
    }

    @Override
    protected List<JButton> scanTargets() {
        List<JButton> targets = new ArrayList<>();
        for (int[] step : STEPS) {
            int row = row() + step[0];
            int col = col() + step[1];
            if (canReach(row, col) && isEmpty(row, col)) {
                targets.add(blockAt(row, col));
            }
        }
        return targets;
    }
}
