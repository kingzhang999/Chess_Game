package Chesspieces;

import Behaviors.SoldiersAttackBehaviors;
import Behaviors.SoldiersMovement;

import javax.swing.*;

public class Soldier extends AbstractChessPiece{
    private boolean isFirstMove = true;
    public Soldier(JButton chess_block, ImageIcon chess_piece) {
        super(chess_block, chess_piece);
        chess_block.setIcon(chess_piece);
        setMoveBehavior(new SoldiersMovement(this));
        setAttackBehavior(new SoldiersAttackBehaviors(this));
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }
    public boolean getFirstMove(){
        return isFirstMove;
    }
}
