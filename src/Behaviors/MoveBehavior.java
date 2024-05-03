package Behaviors;

import javax.swing.*;

public interface MoveBehavior {
    boolean move(JButton chess_block);
    JButton[] scanChessBlock_canWalk();
}