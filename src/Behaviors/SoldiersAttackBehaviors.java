package Behaviors;

import BackgroundThings.ChessBoard;
import Chesspieces.Soldier;
import Chesspieces.WhitePiece;

import javax.swing.*;
import java.util.ArrayList;

public class SoldiersAttackBehaviors implements AttackBehavior{
    private int x;
    private int y;
    private JButton nowPosition;
    Soldier soldier;
    private ArrayList<JButton> attack_able_W_blocks = new ArrayList<>();

    private ArrayList<JButton> attack_able_B_blocks = new ArrayList<>();
    public SoldiersAttackBehaviors(Soldier soldier){
        this.soldier = soldier;
        updateLocation();
    }
    @Override
    public void attack(JButton being_attacked_chess_block) {

    }

    @Override
    public void scan_W_canAttack() {//此方法的调用放在SoldiersMovement类的updatePieceList()方法中，用于更新可攻击的位置。
        attack_able_W_blocks.clear();//清空之前的可攻击位置
        updateLocation();//更新位置信息

        int x_copy = (x + 1 >= 8)? x : x +1;
        int y_copy_right = (y + 1 >= 8)? y : y + 1;
        int y_copy_left = (y - 1 < 0)? y : y - 1;

        //System.out.println("scan_W_canAttack: "+ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x+1,y+1)));
        ArrayList<JButton> attack_able_W_blocks_temp = new ArrayList<>();//用于临时存储可攻击的位置
        if(ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy,y_copy_right)) &&
                !(ChessBoard.getChessBoardElement(x_copy,y_copy_right).getIcon() instanceof WhitePiece)){
            //System.out.println("here");
            ChessBoard.findElement_test(ChessBoard.board,ChessBoard.getChessBoardElement(x_copy,y_copy_right),"WhiteCanAttack: ");//test
            attack_able_W_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy,y_copy_right));
        }

        if(ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy,y_copy_left)) &&
                !(ChessBoard.getChessBoardElement(x_copy,y_copy_left).getIcon() instanceof WhitePiece)){
            ChessBoard.findElement_test(ChessBoard.board,ChessBoard.getChessBoardElement(x_copy,y_copy_left),"WhiteCanAttack: ");//test
            attack_able_W_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy,y_copy_left));
        }

        attack_able_W_blocks.addAll(attack_able_W_blocks_temp);
    }

    @Override
    public void scan_B_canAttack() {

    }

    private void updateLocation(){
        //实时获取现在所处在的格子和位置。
        nowPosition = soldier.getChess_block();
        x = ChessBoard.findElement(ChessBoard.board,nowPosition)[0];
        y = ChessBoard.findElement(ChessBoard.board,nowPosition)[1];

    }

}
