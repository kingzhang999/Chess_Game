package Behaviors.BlackPiece;

import BackgroundThings.ChessBoard;
import Behaviors.AttackBehavior;
import Chesspieces.BlackPiece;
import Chesspieces.Soldier;

import javax.swing.*;
import java.util.ArrayList;

public class Soldiers_B_AttackBehaviors implements AttackBehavior {
    private int x;
    private int y;
    private JButton nowPosition;
    Soldier soldier;
    private final ArrayList<JButton> attack_able_B_blocks = new ArrayList<>();

    public Soldiers_B_AttackBehaviors(Soldier soldier){
        this.soldier = soldier;
        updateLocation();
    }

    @Override
    public void attack(JButton being_attacked_chess_block) {

    }

    @Override
    public void scan_W_canAttack() {
        throw new IllegalArgumentException("This is Black Piece attack behavior, can not scan White Piece can attacked blocks.");
    }

    @Override
    public void scan_B_canAttack() {
        attack_able_B_blocks.clear();//清空之前的可攻击位置
        updateLocation();//更新位置信息

        int x_copy = (x - 1 < 0) ? x : x - 1;//x_copy不能等于或小于0
        int y_copy_right = (y + 1 >= 8) ? y : y + 1;
        int y_copy_left = (y - 1 < 0) ? y : y - 1;

        //System.out.println("scan_B_canAttack: "+ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy,y_copy_left)));
        ArrayList<JButton> attack_able_B_blocks_temp = new ArrayList<>();//用于临时存储可攻击的位置
        if(x_copy >= 0 && ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy,y_copy_right)) &&
                !(ChessBoard.getChessBoardElement(x_copy,y_copy_right).getIcon() instanceof BlackPiece)){//检查右侧是否有敌方兵
            //System.out.println("here");
            //test
            ChessBoard.findElement_test(ChessBoard.board,
                    ChessBoard.getChessBoardElement(x_copy,y_copy_right),"BlackCanAttack: ");

            attack_able_B_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy,y_copy_right));
        }

        if(x_copy >= 0 && ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy,y_copy_left)) &&
                !(ChessBoard.getChessBoardElement(x_copy,y_copy_left).getIcon() instanceof BlackPiece)){//检查左侧是否有敌方兵
            //test
            ChessBoard.findElement_test(ChessBoard.board,
                    ChessBoard.getChessBoardElement(x_copy,y_copy_left),"BlackCanAttack: ");

            attack_able_B_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy,y_copy_left));
        }

        attack_able_B_blocks.addAll(attack_able_B_blocks_temp);
    }
    private void updateLocation(){
        //实时获取现在所处在的格子和位置。
        nowPosition = soldier.getChess_block();
        x = ChessBoard.findElement(ChessBoard.board,nowPosition)[0];
        y = ChessBoard.findElement(ChessBoard.board,nowPosition)[1];
    }

}
