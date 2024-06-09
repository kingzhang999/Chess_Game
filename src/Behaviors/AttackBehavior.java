package Behaviors;

import Chesspieces.AbstractChessPiece;

import javax.swing.*;

public interface AttackBehavior {
    boolean attack(JButton being_attacked_chess_block);
    void scan_W_canAttack();
    void scan_B_canAttack();

    void deletePieces(AbstractChessPiece abstractChessPiece);

}