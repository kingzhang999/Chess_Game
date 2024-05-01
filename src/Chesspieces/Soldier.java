package Chesspieces;

import Behaviors.SoldiersMovement;

import javax.swing.*;

public class Soldier extends AbstractChessPiece{
    public Soldier(JButton chess_block, ImageIcon chess_piece) {
        super(chess_block, chess_piece);
        chess_block.setIcon(chess_piece);
        setMoveBehavior(new SoldiersMovement(this));
        //这只是让chessBlockList_canWalk这个数组型变量指向了scanChessBlock_canWalk这个方法所返回的数组而已。
        // 并不是把scanChessBlock_canWalk这个方法所返回的数组的内容复制给了chessBlockList_canWalk这个数组型变量。
    }
}
