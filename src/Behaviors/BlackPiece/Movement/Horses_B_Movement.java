package Behaviors.BlackPiece.Movement;

import BackgroundThings.ChessBoard;
import Behaviors.MoveBehavior;
import Chesspieces.AbstractChessPiece;
import Chesspieces.Horse;
import Players.BlackPlayer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Horses_B_Movement implements MoveBehavior {
    private int x;
    private int y;
    private JButton nowPosition;
    private final ArrayList<JButton> chess_B_BlockList_moment_horse = new ArrayList<>();
    Horse horse;

    public Horses_B_Movement(Horse horse){
        this.horse = horse;
        updateLocation();
    }

    @Override
    public boolean move(JButton target_chess_block) {
        updatePieceList();

        if(horse.getChoiceState() == AbstractChessPiece.ChoiceState.CHOICE_ABLE){
            if(BlackPlayer.isInBlackPieceList(horse)){
                return real_B_move(target_chess_block);
            }
        }
        return false;
    }

    private boolean real_B_move(JButton target_chess_block) {
        //获取目标格子的坐标。
        int target_x = ChessBoard.findElement(ChessBoard.board,target_chess_block)[0];
        int target_y = ChessBoard.findElement(ChessBoard.board,target_chess_block)[1];
        //判断是否可以移动到目标格子。
        if(//防止棋子出界并判断下一个格子是否可以移动。
                chess_B_BlockList_moment_horse.contains(ChessBoard.getChessBoardElement(target_x,target_y))){

            //获取要去的方块此时的贴图。
            ImageIcon targetImageIcon = (ImageIcon) target_chess_block.getIcon();

            ChessBoard.changeChessBoard(x,y,horse.getChess_block_iconImage());//将马目前待在这儿的方块还原成马没来这时的样子。
            horse.setChess_block_iconImage(targetImageIcon);//将马将要去的方块目前的状况记录下来。
            //将棋子所绑定的格子替换为目标格子。
            horse.setChess_block(target_chess_block);


            if(targetImageIcon == ChessBoard.WHITE){//如要去的格子是白格子，则把要去的格子的背景换成相应棋子在白格子下的背景。

                ChessBoard.changeChessBoard(target_x,target_y,ChessBoard.BLACK_HORSE_W);

            }else {//不然则把要去的格子的背景换成相应棋子在黑格子下的背景。

                    ChessBoard.changeChessBoard(target_x,target_y,ChessBoard.BLACK_HORSE_B);

            }
            ChessBoard.getChessBoardElement(target_x,target_y).repaint();//重绘移动完后格子的贴图。
            updateLocation();//实时获取现在所处在的格子和位置。
            updatePieceList();//更新棋子可以移动的格子列表。

            return true;//移动成功。
        }
        return false;//移动失败。
    }

    @Override
    public JButton[] scan_B_ChessBlock_canWalk() {
        ArrayList<JButton> chess_B_BlockList_moments = new ArrayList<>();
        int x_copy = x;//复制x的值，以防止在循环中修改原值。
        int y_copy = y;//复制y的值，以防止在循环中修改原值。

        //找旗子右边两格处的上面和下面可以走动的格子。
        if(y_copy + 2 < ChessBoard.COLS){
            if(x_copy - 1 >= 0){
                if(!ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy - 1,y_copy + 2))){
                    chess_B_BlockList_moments.add(ChessBoard.getChessBoardElement(x_copy - 1,y_copy + 2));
                }
            }

            if(x_copy + 1 < ChessBoard.ROWS){
                if(!ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy + 1,y_copy + 2))){
                    chess_B_BlockList_moments.add(ChessBoard.getChessBoardElement(x_copy + 1,y_copy + 2));
                }
            }
        }

        //找旗子左边两格处的上面和下面可以走动的格子。
        if(y_copy - 2 >= 0){
            if(x_copy - 1 >= 0){
                if(!ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy - 1,y_copy - 2))){
                    chess_B_BlockList_moments.add(ChessBoard.getChessBoardElement(x_copy - 1,y_copy - 2));
                }
            }

            if(x_copy + 1 < ChessBoard.ROWS){
                if(!ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy + 1,y_copy - 2))){
                    chess_B_BlockList_moments.add(ChessBoard.getChessBoardElement(x_copy + 1,y_copy - 2));
                }
            }
        }

        //找旗子右边一格处的上面和下面可以走动的格子。
        if(y_copy + 1 < ChessBoard.COLS){
            if(x_copy - 2 >= 0){
                if(!ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy - 2,y_copy + 1))){
                    chess_B_BlockList_moments.add(ChessBoard.getChessBoardElement(x_copy - 2,y_copy + 1));
                }
            }

            if(x_copy + 2 < ChessBoard.ROWS){
                if(!ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy + 2,y_copy + 1))){
                    chess_B_BlockList_moments.add(ChessBoard.getChessBoardElement(x_copy + 2,y_copy + 1));
                }
            }
        }

        //找旗子左边一格处的上面和下面可以走动的格子。
        if(y_copy - 1 >= 0){
            if(x_copy - 2 >= 0){
                if(!ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy - 2,y_copy - 1))){
                    chess_B_BlockList_moments.add(ChessBoard.getChessBoardElement(x_copy - 2,y_copy - 1));
                }
            }

            if(x_copy + 2 < ChessBoard.ROWS){
                if(!ChessBoard.hasPiece(ChessBoard.getChessBoardElement(x_copy + 2,y_copy - 1))){
                    chess_B_BlockList_moments.add(ChessBoard.getChessBoardElement(x_copy + 2,y_copy - 1));
                }
            }
        }

        JButton[] chessBlockList_official = new JButton[chess_B_BlockList_moments.size()];//将ArrayList转换为JButton数组。
        chessBlockList_official = chess_B_BlockList_moments.toArray(chessBlockList_official);

        /*for (JButton chess_blocks : chessBlockList_official){
            ChessBoard.findElement_test(ChessBoard.board,chess_blocks,"house_B_move");//测试能否找到元素。
        }*/

        return chessBlockList_official;
    }

    @Override
    public JButton[] scan_W_ChessBlock_canWalk() {
        throw new IllegalArgumentException("This is Black movement behavior, can not scan white piece can walked blocks.");
    }

    public void updateLocation(){
        //实时获取现在所处在的格子和位置。
        nowPosition = horse.getChess_block();
        x = ChessBoard.findElement(ChessBoard.board,nowPosition)[0];
        y = ChessBoard.findElement(ChessBoard.board,nowPosition)[1];
    }

    private void updatePieceList(){
        if(BlackPlayer.isInBlackPieceList(horse)){
            //每次执行移动操作的时候，都需要重新判断可以移动的格子，以防上一次遇到不可移动的格子之后，将棋子卡在原地无法行动。
            chess_B_BlockList_moment_horse.clear();//在每一次排查完合适的格子之后，删除原来的列表。以防止之前过时的格子还能生效。
            chess_B_BlockList_moment_horse.addAll(List.of(scan_B_ChessBlock_canWalk()));//更新白棋可以移动的格子列表。
            horse.getAttackBehavior().scan_B_canAttack();//更新白棋可以攻击的格子列表。
        }
    }
}
