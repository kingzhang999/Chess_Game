package Chesspieces;

import Behaviors.WhitePiece.AttackBehaviors.Cars_W_AttackBehaviors;
import Behaviors.WhitePiece.Movement.Cars_W_Movement;

import javax.swing.*;

public class Car extends AbstractChessPiece{
    public Car(JButton chess_blocks, ImageIcon chess_piece) {
        super(chess_blocks, chess_piece);
        chess_block.setIcon(chess_piece);
        //设置棋子的移动行为一定要放在设置棋子贴图之后因为判断用WhitePiece的移动行为还是BlackPiece移动行为，依赖于棋子贴图
        setMoveBehavior(new Cars_W_Movement(this));//test
        setAttackBehavior(new Cars_W_AttackBehaviors(this));//test
        //setMoveBehavior(useWhiteMovementOrBlackMovement());//设置棋子的移动行为//还没写
        //设置棋子的攻击行为一定要放在设置棋子贴图之后因为判断用WhitePiece的攻击行为还是BlackPiece攻击行为，依赖于棋子贴图
        //setAttackBehavior(useWhiteAttackBehaviorOrBlackAttackBehavior());//还没写
    }

    /*private MoveBehavior useWhiteMovementOrBlackMovement(){
        //useWhiteMovementOrBlackMovement方法一定要放在Soldier类中,因为他需要当前Soldier类的实例Soldier
        if (chess_piece instanceof WhitePiece){
            return new Cars_W_Movement(this);
        } else if(chess_piece instanceof BlackPiece){
            return new Cars_B_Movement(this);
        }
        throw new IllegalArgumentException("Invalid piece type");
    }

    private AttackBehavior useWhiteAttackBehaviorOrBlackAttackBehavior(){
        if(chess_piece instanceof WhitePiece){
            return new Cars_W_AttackBehaviors(this);
        } else if(chess_piece instanceof BlackPiece){
            return new Cars_B_AttackBehaviors(this);
        }
        throw new IllegalArgumentException("Invalid piece type");
    }*///暂时不用
}
