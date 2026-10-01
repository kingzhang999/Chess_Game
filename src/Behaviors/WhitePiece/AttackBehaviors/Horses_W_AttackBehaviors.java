package Behaviors.WhitePiece.AttackBehaviors;

import BackgroundThings.ChessBoard;
import Behaviors.common.PieceAttackBehavior;
import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** 白方马的吃法：八个“日”字形落点上若有棋子则可吃。 */
public class Horses_W_AttackBehaviors extends PieceAttackBehavior {

    private static final int[][] HOPS = {
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2},
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1}
    };

    public Horses_W_AttackBehaviors(AbstractChessPiece piece) {
        super(piece);
    }

    @Override
    protected List<JButton> scanTargets() {
        List<JButton> targets = new ArrayList<>();
        for (int[] hop : HOPS) {
            int row = row() + hop[0];
            int col = col() + hop[1];
            if (canReach(row, col) && ChessBoard.hasPiece(blockAt(row, col))) {
                targets.add(blockAt(row, col));
            }
        }
        return targets;
    }
}
