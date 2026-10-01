package Behaviors;

import javax.swing.*;

/**
 * 走子行为。实现类只负责判断目标格是否可走，并完成棋盘外观与棋子位置的更新。
 */
public interface MoveBehavior {
    boolean move(JButton chess_block);
}
