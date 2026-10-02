package Behaviors.common;

import Chesspieces.AbstractChessPiece;
import Chesspieces.Soldier;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 兵/卒的走法：向前一格；只有在首步、且前方两格都为空时才允许一次走两格。
 *
 * <p>兵的走法不能用“沿方向一直滑行”的扫描——那会把整条直线都算成可走格，
 * 于是兵能一次走三格甚至更多。</p>
 */
public abstract class PawnMoveBehavior extends PieceMoveBehavior {
    /** 前进方向：白方 +1（行号增大），黑方 -1。 */
    private final int forward;

    protected PawnMoveBehavior(AbstractChessPiece piece, int forward) {
        super(piece);
        this.forward = forward;
    }

    @Override
    protected List<JButton> scanTargets() {
        List<JButton> targets = new ArrayList<>();

        int oneStep = row() + forward;
        if (canReach(oneStep, col()) && isEmpty(oneStep, col())) {
            targets.add(blockAt(oneStep, col()));

            //首步可以走两格，但中间那格必须也是空的。
            boolean firstMove = piece instanceof Soldier soldier && soldier.getFirstMove();
            int twoSteps = row() + 2 * forward;
            if (firstMove && canReach(twoSteps, col()) && isEmpty(twoSteps, col())) {
                targets.add(blockAt(twoSteps, col()));
            }
        }
        return targets;
    }
}
