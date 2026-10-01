package Behaviors.BlackPiece.AttackBehaviors;

import BackgroundThings.ChessBoard;
import Behaviors.common.PieceAttackBehavior;
import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** 黑方卒的吃法：斜前方两格。 */
public class Soldiers_B_AttackBehaviors extends PieceAttackBehavior {

    private static final int[][] STRIKES = {{-1, 1}, {-1, -1}};

    public Soldiers_B_AttackBehaviors(AbstractChessPiece piece) {
        super(piece);
    }

    @Override
    protected List<JButton> scanTargets() {
        List<JButton> targets = new ArrayList<>();
        for (int[] strike : STRIKES) {
            int row = row() + strike[0];
            int col = col() + strike[1];
            if (canReach(row, col) && ChessBoard.hasPiece(blockAt(row, col))) {
                targets.add(blockAt(row, col));
            }
        }
        return targets;
    }
}
