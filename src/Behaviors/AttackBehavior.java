package Behaviors;

import javax.swing.*;

public interface AttackBehavior {
    void attack(JButton being_attacked_chess_block);
    void scan_W_canAttack();
    void scan_B_canAttack();

}