package Behaviors.WhitePiece.AttackBehaviors;

import BackgroundThings.ChessBoard;
import Behaviors.AttackBehavior;
import Behaviors.WhitePiece.Movement.Horses_W_Movement;
import Chesspieces.AbstractChessPiece;
import Chesspieces.BlackPiece;
import Chesspieces.Horse;
import Chesspieces.WhitePiece;
import Players.BlackPlayer;

import javax.swing.*;
import java.util.ArrayList;

public class Houses_W_AttackBehaviors implements AttackBehavior {
    private int x;
    private int y;
    private JButton nowPosition;
    Horse horse;
    private final ArrayList<JButton> attack_able_W_blocks = new ArrayList<>();

    public Houses_W_AttackBehaviors(Horse horse) {
        this.horse = horse;
        updateLocation();
    }

    @Override
    public boolean attack(JButton being_attacked_chess_block) {
        scan_W_canAttack();//刷新可攻击的位置。
        int x_future = ChessBoard.findElement(ChessBoard.board,being_attacked_chess_block)[0];
        int y_future = ChessBoard.findElement(ChessBoard.board,being_attacked_chess_block)[1];

        if(attack_able_W_blocks.contains(being_attacked_chess_block)){

            //获取要去的方块此时的贴图。
            BlackPiece targetImageIcon = (BlackPiece) being_attacked_chess_block.getIcon();

            //马走后便可以把要去的这个方块还原成没有马在这里时的样子。
            ImageIcon realImageIcon;
            if(targetImageIcon.getBackground() == ChessBoard.BackGroundType.WhiteBack){
                realImageIcon = ChessBoard.WHITE;
            }else {
                realImageIcon = ChessBoard.BLACK;
            }

            ChessBoard.changeChessBoard(x,y,horse.getChess_block_iconImage());//将马目前待在这儿的方块还原成士兵没来这时的样子。
            horse.setChess_block_iconImage(realImageIcon);//将马将要去的方块目前的状况记录下来。

            for(AbstractChessPiece horse1 : ChessBoard.all_chess_piece_list){
                if(horse1 != null && horse1.getChess_block() == being_attacked_chess_block){
                    deletePieces(horse1);//将要去的方块上的敌方棋子删除。
                    //System.out.println("delete: "+soldier1);
                }
            }

            //将棋子所绑定的格子替换为目标格子。
            horse.setChess_block(being_attacked_chess_block);

            //System.out.println("targetImageIcon instanceof WhiteBackGround: "+(targetImageIcon instanceof WhiteBackGround));
            if(targetImageIcon.getBackground() == ChessBoard.BackGroundType.WhiteBack){//如要去的格子是白格子，则把要去的格子的背景换成相应棋子在白格子下的背景。

                ChessBoard.changeChessBoard(x_future,y_future,ChessBoard.WHITE_HORSE_W);

            }else {//不然则把要去的格子的背景换成相应棋子在黑格子下的背景。

                ChessBoard.changeChessBoard(x_future,y_future,ChessBoard.WHITE_HORSE_B);

            }
            ChessBoard.getChessBoardElement(x_future,y_future).repaint();//重绘移动完后格子的贴图。
            ((Horses_W_Movement)horse.getMoveBehavior()).updateLocation();//更新移动行为中棋子的所在位置。
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

        int x_copy = x;//复制x的值，以防止在循环中修改原值。
        int y_copy = y;//复制y的值，以防止在循环中修改原值。、

        ArrayList<JButton> attack_able_W_blocks_temp = new ArrayList<>();

        //找旗子右边两格处的上面和下面可以攻击的格子。
        if(y_copy + 2 < ChessBoard.COLS){
            if(x_copy - 1 >= 0){
                if(ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy - 1,y_copy + 2)) &&
                        !(ChessBoard.getChessBoardElement(x_copy - 1,y_copy + 2).getIcon() instanceof WhitePiece)){
                    attack_able_W_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy - 1,y_copy + 2));
                }
            }

            if(x_copy + 1 < ChessBoard.ROWS){
                if(ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy + 1,y_copy + 2)) &&
                        !(ChessBoard.getChessBoardElement(x_copy + 1,y_copy + 2).getIcon() instanceof WhitePiece)){
                    attack_able_W_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy + 1,y_copy + 2));
                }
            }
        }

        //找旗子左边两格处的上面和下面可以攻击的格子。
        if(y_copy - 2 >= 0){
            if(x_copy - 1 >= 0){
                if(ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy - 1,y_copy - 2 )) &&
                        !(ChessBoard.getChessBoardElement(x_copy - 1,y_copy - 2).getIcon() instanceof WhitePiece)){
                    attack_able_W_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy - 1,y_copy - 2));
                }
            }

            if(x_copy + 1 < ChessBoard.ROWS){
                if(ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy + 1,y_copy - 2)) &&
                        !(ChessBoard.getChessBoardElement(x_copy + 1,y_copy - 2).getIcon() instanceof WhitePiece)){
                    attack_able_W_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy + 1,y_copy - 2));
                }
            }
        }

        //找旗子右边一格处的上面和下面可以攻击的格子。
        if(y_copy + 1 < ChessBoard.COLS){
            if(x_copy - 2 >= 0){
                if(ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy - 2,y_copy + 1)) &&
                        !(ChessBoard.getChessBoardElement(x_copy - 2,y_copy + 1).getIcon() instanceof WhitePiece)){
                    attack_able_W_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy - 2,y_copy + 1));
                }
            }

            if(x_copy + 2 < ChessBoard.ROWS){
                if(ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy + 2,y_copy + 1)) &&
                        !(ChessBoard.getChessBoardElement(x_copy + 2,y_copy + 1).getIcon() instanceof WhitePiece)){
                    attack_able_W_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy + 2,y_copy + 1));
                }
            }
        }

        //找旗子左边一格处的上面和下面可以攻击的格子。
        if(y_copy - 1 >= 0){
            if(x_copy - 2 >= 0){
                if(ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy - 2,y_copy - 1)) &&
                        !(ChessBoard.getChessBoardElement(x_copy - 2,y_copy - 1).getIcon() instanceof WhitePiece)){
                    attack_able_W_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy - 2,y_copy - 1));
                }
            }

            if(x_copy + 2 < ChessBoard.ROWS){
                if(ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy + 2,y_copy - 1)) &&
                        !(ChessBoard.getChessBoardElement(x_copy + 2,y_copy - 1).getIcon() instanceof WhitePiece)){
                    attack_able_W_blocks_temp.add(ChessBoard.getChessBoardElement(x_copy + 2,y_copy - 1));
                }
            }
        }

        attack_able_W_blocks.addAll(attack_able_W_blocks_temp);

        /*for (JButton chess_blocks : attack_able_W_blocks_temp){
            ChessBoard.findElement_test(ChessBoard.board,chess_blocks,"house_W_attack");//测试能否找到元素。
        }*/
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
        nowPosition = horse.getChess_block();
        x = ChessBoard.findElement(ChessBoard.board,nowPosition)[0];
        y = ChessBoard.findElement(ChessBoard.board,nowPosition)[1];
    }
}
