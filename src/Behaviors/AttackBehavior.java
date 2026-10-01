package Behaviors;

import javax.swing.*;

/**
 * 吃子行为。实现类只负责判断目标格上的敌方棋子是否可吃，并完成棋盘外观与棋子位置的更新。
 */
public interface AttackBehavior {
    boolean attack(JButton being_attacked_chess_block);
}
