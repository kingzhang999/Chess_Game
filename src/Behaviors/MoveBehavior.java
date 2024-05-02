package Behaviors;

import javax.swing.*;

public interface MoveBehavior {
    void move(JButton chess_block);
    JButton[] scanChessBlock_canWalk();
}