package Behaviors.BlackPiece.AttackBehaviors;

import BackgroundThings.ChessBoard;
import Behaviors.common.PieceAttackBehavior;
import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** 黑方王的吃法：周围八格上若有棋子则可吃。 */
public class Kings_B_AttackBehaviors extends PieceAttackBehavior {

    private static final int[][] STEPS = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    public Kings_B_AttackBehaviors(AbstractChessPiece piece) {
        super(piece);
    }

    @Override
    protected List<JButton> scanTargets() {
        List<JButton> targets = new ArrayList<>();
        for (int[] step : STEPS) {
            int row = row() + step[0];
            int col = col() + step[1];
            if (canReach(row, col) && ChessBoard.hasPiece(blockAt(row, col))) {
                targets.add(blockAt(row, col));
            }
        }
        return targets;
    }
}
