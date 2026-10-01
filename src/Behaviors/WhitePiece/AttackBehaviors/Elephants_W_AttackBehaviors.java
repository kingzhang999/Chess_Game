package Behaviors.WhitePiece.AttackBehaviors;

import Behaviors.common.PieceAttackBehavior;
import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** 白方象的吃法：斜向四个方向，取遇到的第一个棋子。 */
public class Elephants_W_AttackBehaviors extends PieceAttackBehavior {

    public Elephants_W_AttackBehaviors(AbstractChessPiece piece) {
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
