package Chesspieces;

import Behaviors.AttackBehavior;
import Behaviors.BlackPiece.AttackBehaviors.Soldiers_B_AttackBehaviors;
import Behaviors.MoveBehavior;
import Behaviors.WhitePiece.AttackBehaviors.Soldiers_W_AttackBehaviors;
import Behaviors.BlackPiece.Movement.Soldiers_B_Movement;
import Behaviors.WhitePiece.Movement.Soldiers_W_Movement;


import javax.swing.*;

public class Soldier extends AbstractChessPiece{
    private boolean isFirstMove = true;
    public Soldier(JButton chess_block, ImageIcon chess_piece) {
        super(chess_block, chess_piece);
        chess_block.setIcon(chess_piece);
        //设置棋子的移动行为一定要放在设置棋子贴图之后因为判断用WhitePiece的移动行为还是BlackPiece移动行为，依赖于棋子贴图
        setMoveBehavior(useWhiteMovementOrBlackMovement());
        //设置棋子的攻击行为一定要放在设置棋子贴图之后因为判断用WhitePiece的攻击行为还是BlackPiece攻击行为，依赖于棋子贴图
        setAttackBehavior(useWhiteAttackBehaviorOrBlackAttackBehavior());
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    public boolean getFirstMove(){
        return isFirstMove;
    }

    private MoveBehavior useWhiteMovementOrBlackMovement(){
        //useWhiteMovementOrBlackMovement方法一定要放在Soldier类中,因为他需要当前Soldier类的实例Soldier
        if (chess_piece instanceof WhitePiece){
            return new Soldiers_W_Movement(this);
        } else if(chess_piece instanceof BlackPiece){
            return new Soldiers_B_Movement(this);
        }
        throw new IllegalArgumentException("Invalid piece type");
    }

    private AttackBehavior useWhiteAttackBehaviorOrBlackAttackBehavior(){
        if(chess_piece instanceof WhitePiece){
            return new Soldiers_W_AttackBehaviors(this);
        } else if(chess_piece instanceof BlackPiece){
            return new Soldiers_B_AttackBehaviors(this);
        }
        throw new IllegalArgumentException("Invalid piece type");
    }

}
