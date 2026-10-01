package Behaviors.WhitePiece.AttackBehaviors;

import Behaviors.common.PieceAttackBehavior;
import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** 白方后的吃法：横竖与斜向共八个方向，取遇到的第一个棋子。 */
public class Queens_W_AttackBehaviors extends PieceAttackBehavior {

    public Queens_W_AttackBehaviors(AbstractChessPiece piece) {
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
