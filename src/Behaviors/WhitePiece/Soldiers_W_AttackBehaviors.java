package Behaviors.WhitePiece;

import BackgroundThings.ChessBoard;
import Behaviors.AttackBehavior;
import Chesspieces.AbstractChessPiece;
import Chesspieces.BlackPiece;
import Chesspieces.Soldier;
import Chesspieces.WhitePiece;
import Players.BlackPlayer;

import javax.swing.*;
import java.util.ArrayList;

public class Soldiers_W_AttackBehaviors implements AttackBehavior {
    private int x;
    private int y;
    private JButton nowPosition;
    Soldier soldier;
    private final ArrayList<JButton> attack_able_W_blocks = new ArrayList<>();

    public Soldiers_W_AttackBehaviors(Soldier soldier){
        this.soldier = soldier;
        updateLocation();
    }

    @Override
    public boolean attack(JButton being_attacked_chess_block) {
        scan_W_canAttack();//刷新可攻击的位置。
        int x_future = ChessBoard.findElement(ChessBoard.board,being_attacked_chess_block)[0];
        int y_future = ChessBoard.findElement(ChessBoard.board,being_attacked_chess_block)[1];

        if(attack_able_W_blocks.contains(being_attacked_chess_block)){

            //获取当前所处方块此时的贴图。
            ImageIcon nowIcon = (ImageIcon) nowPosition.getIcon();//我在JButton的构造函数中传入的就是ImageIcon所以这里可以强转

            //获取要去的方块此时的贴图。
            BlackPiece targetImageIcon = (BlackPiece) being_attacked_chess_block.getIcon();

            //士兵走后便可以把要去的这个方块还原成没有士兵在这里时的样子。
            ImageIcon realImageIcon;
            if(targetImageIcon.getBackground() == ChessBoard.BackGroundType.WhiteBack){
                realImageIcon = ChessBoard.WHITE;
            }else {
                realImageIcon = ChessBoard.BLACK;
            }

            ChessBoard.changeChessBoard(x,y,soldier.getChess_block_iconImage());//将士兵目前待在这儿的方块还原成士兵没来这时的样子。
            soldier.setChess_block_iconImage(realImageIcon);//将士兵将要去的方块目前的状况记录下来。

            for(AbstractChessPiece soldier1 : ChessBoard.all_chess_piece_list){
                if(soldier1 != null && soldier1.getChess_block() == being_attacked_chess_block){
                    deletePieces(soldier1);//将要去的方块上的敌方棋子删除。
                    //System.out.println("delete: "+soldier1);
                }
            }

            //将棋子所绑定的格子替换为目标格子。
            soldier.setChess_block(being_attacked_chess_block);

            //System.out.println("targetImageIcon instanceof WhiteBackGround: "+(targetImageIcon instanceof WhiteBackGround));
            if(targetImageIcon.getBackground() == ChessBoard.BackGroundType.WhiteBack){//如要去的格子是白格子，则把要去的格子的背景换成相应棋子在白格子下的背景。

                ChessBoard.changeChessBoard(x_future,y_future,ChessBoard.WHITE_SOLDIER_W);

            }else {//不然则把要去的格子的背景换成相应棋子在黑格子下的背景。

                ChessBoard.changeChessBoard(x_future,y_future,ChessBoard.WHITE_SOLDIER_B);

            }
            ChessBoard.getChessBoardElement(x_future,y_future).repaint();//重绘移动完后格子的贴图。
            ((Soldiers_W_Movement)soldier.getMoveBehavior()).updateLocation();//更新移动行为中棋子的所在位置。
            scan_W_canAttack();//刷新可攻击的位置。
            return true;
        }
        return false;
    }

    @Override
    public void scan_W_canAttack() {
        //此方法会在移动之后，攻击之前和攻击之后被调用。
        attack_able_W_blocks.clear();//清空之前的可攻击位置
        updateLocation();//更新位置信息

        int x_copy = (x + 1 >= 8) ? x : x + 1;//x_copy不能等于或超过7
        int y_copy_right = (y + 1 >= 8) ? y : y + 1;
        int y_copy_left = (y - 1 < 0) ? y : y - 1;

        //System.out.println("x_copy: "+x_copy);//test
        ArrayList<JButton> attack_able_W_blocks_temp = new ArrayList<>();//用于临时存储可攻击的位置
        if(x_copy < 7 && ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy,y_copy_right)) &&
                !(ChessBoard.getChessBoardElement(x_copy,y_copy_right).getIcon() instanceof WhitePiece)){//检测右侧是否有敌方兵。
            //System.out.println("here");
            /*//test
            ChessBoard.findElement_test(ChessBoard.board,
                    ChessBoard.getChessBoardElement(x_copy,y_copy_right),"WhiteCanAttack: ");*/

            attack_able_W_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy,y_copy_right));
        }

        if(x_copy < 7 && ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy,y_copy_left)) &&
                !(ChessBoard.getChessBoardElement(x_copy,y_copy_left).getIcon() instanceof WhitePiece)){
            /*//test
            ChessBoard.findElement_test(
                    ChessBoard.board,ChessBoard.getChessBoardElement(x_copy,y_copy_left),"WhiteCanAttack: ");*/

            attack_able_W_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy,y_copy_left));
        }

        attack_able_W_blocks.addAll(attack_able_W_blocks_temp);
    }

    @Override
    public void scan_B_canAttack() {
        throw new IllegalArgumentException("This is White Piece attack behavior, can not scan Black Piece can attacked blocks.");
    }

    @Override
    public void deletePieces(AbstractChessPiece abstractChessPiece) {
        BlackPlayer.remove_B_Piece(abstractChessPiece);
        ChessBoard.removeChessPiece(abstractChessPiece);
    }

    public void updateLocation(){
        //实时获取现在所处在的格子和位置。
        nowPosition = soldier.getChess_block();
        x = ChessBoard.findElement(ChessBoard.board,nowPosition)[0];
        y = ChessBoard.findElement(ChessBoard.board,nowPosition)[1];
    }

}
