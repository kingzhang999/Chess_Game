package Chesspieces;

import Behaviors.WhitePiece.AttackBehaviors.Elephants_W_AttackBehaviors;
import Behaviors.WhitePiece.Movement.Elephants_W_Movement;

import javax.swing.*;

public class Elephant extends AbstractChessPiece{
    public Elephant(JButton chess_blocks, ImageIcon chess_piece) {
        super(chess_blocks, chess_piece);
        chess_block.setIcon(chess_piece);
        setMoveBehavior(new Elephants_W_Movement(this));//test
        setAttackBehavior(new Elephants_W_AttackBehaviors(this));//test
        //设置棋子的移动行为一定要放在设置棋子贴图之后因为判断用WhitePiece的移动行为还是BlackPiece移动行为，依赖于棋子贴图
        //setMoveBehavior(useWhiteMovementOrBlackMovement());//设置棋子的移动行为
        //设置棋子的攻击行为一定要放在设置棋子贴图之后因为判断用WhitePiece的攻击行为还是BlackPiece攻击行为，依赖于棋子贴图
        //setAttackBehavior(useWhiteAttackBehaviorOrBlackAttackBehavior());
    }

    /*private MoveBehavior useWhiteMovementOrBlackMovement(){
        //useWhiteMovementOrBlackMovement方法一定要放在Soldier类中,因为他需要当前Soldier类的实例Soldier
        if (chess_piece instanceof WhitePiece){
            return new Elephants_W_Movement(this);
        } else if(chess_piece instanceof BlackPiece){
            return new Elephants_B_Movement(this);
        }
        throw new IllegalArgumentException("Invalid piece type");
    }

    private AttackBehavior useWhiteAttackBehaviorOrBlackAttackBehavior(){
        if(chess_piece instanceof WhitePiece){
            return new Elephants_W_AttackBehaviors(this);
        } else if(chess_piece instanceof BlackPiece){
            return new Elephants_B_AttackBehaviors(this);
        }
        throw new IllegalArgumentException("Invalid piece type");
    }*/
}
