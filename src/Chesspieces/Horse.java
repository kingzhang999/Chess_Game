package Chesspieces;

import Behaviors.AttackBehavior;
import Behaviors.BlackPiece.AttackBehaviors.Horses_B_AttackBehaviors;
import Behaviors.BlackPiece.Movement.Horses_B_Movement;
import Behaviors.MoveBehavior;
import Behaviors.WhitePiece.AttackBehaviors.Horses_W_AttackBehaviors;
import Behaviors.WhitePiece.Movement.Horses_W_Movement;

import javax.swing.*;

public class Horse extends AbstractChessPiece{
    public Horse(JButton chess_blocks, ImageIcon chess_piece) {
        super(chess_blocks, chess_piece);
        chess_block.setIcon(chess_piece);
        //设置棋子的移动行为一定要放在设置棋子贴图之后因为判断用WhitePiece的移动行为还是BlackPiece移动行为，依赖于棋子贴图
        setMoveBehavior(useWhiteMovementOrBlackMovement());//设置棋子的移动行为
        //设置棋子的攻击行为一定要放在设置棋子贴图之后因为判断用WhitePiece的攻击行为还是BlackPiece攻击行为，依赖于棋子贴图
        setAttackBehavior(useWhiteAttackBehaviorOrBlackAttackBehavior());
    }

    private MoveBehavior useWhiteMovementOrBlackMovement(){
        //useWhiteMovementOrBlackMovement方法一定要放在Soldier类中,因为他需要当前Soldier类的实例Soldier
        if (chess_piece instanceof WhitePiece){
            return new Horses_W_Movement(this);
        } else if(chess_piece instanceof BlackPiece){
            return new Horses_B_Movement(this);
        }
        throw new IllegalArgumentException("Invalid piece type");
    }

    private AttackBehavior useWhiteAttackBehaviorOrBlackAttackBehavior(){
        if(chess_piece instanceof WhitePiece){
            return new Horses_W_AttackBehaviors(this);
        } else if(chess_piece instanceof BlackPiece){
            return new Horses_B_AttackBehaviors(this);
        }
        throw new IllegalArgumentException("Invalid piece type");
    }
}
