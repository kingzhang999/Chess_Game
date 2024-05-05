package Behaviors;

import javax.swing.*;

public interface MoveBehavior {
    boolean move(JButton chess_block);
    JButton[] scan_W_ChessBlock_canWalk();
    JButton[] scan_B_ChessBlock_canWalk();
}