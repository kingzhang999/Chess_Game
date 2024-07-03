package Behaviors.WhitePiece.Movement;

import BackgroundThings.ChessBoard;
import Behaviors.MoveBehavior;
import Chesspieces.AbstractChessPiece;
import Chesspieces.Car;
import Players.WhitePlayer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Cars_W_Movement implements MoveBehavior {
    private int x;
    private int y;
    private JButton nowPosition;
    private final ArrayList<JButton> chess_W_BlockList_moment_car = new ArrayList<>();
    Car car;

    public Cars_W_Movement(Car car) {
        this.car = car;
        updateLocation();
    }

    @Override
    public boolean move(JButton target_chess_block) {

        updatePieceList();

        if(car.getChoiceState() == AbstractChessPiece.ChoiceState.CHOICE_ABLE){
            if(WhitePlayer.isInWhitePiecesList(car)){
                return real_W_move(target_chess_block);
            }
        }
        return false;
    }

    @Override
    public JButton[] scan_W_ChessBlock_canWalk() {
        ArrayList<JButton> chess_W_BlockList_moments = new ArrayList<>();
        int x_copy = x;//复制x的值，以防止在循环中修改原值。
        int y_copy = y;//复制y的值，以防止在循环中修改原值。

        for (;x_copy + 1 < ChessBoard.ROWS;x_copy++){//把x_copy放在这里的原因是每一次检查完之后，无论格子是否符合要求，都要把x_copy加一以检查下一个格子。
            if (!ChessBoard.hasPiece(ChessBoard.board[x_copy+1][y])){
                chess_W_BlockList_moments.add(ChessBoard.board[x_copy+1][y]);
            }else if(ChessBoard.hasPiece(ChessBoard.board[x_copy+1][y])){
                break;//如果遇到棋子，则停止检查。
            }
        }

        for (;y_copy + 1 < ChessBoard.COLS;y_copy++){
            if (!ChessBoard.hasPiece(ChessBoard.board[x][y_copy+1])){
                chess_W_BlockList_moments.add(ChessBoard.board[x][y_copy+1]);
            }else if(ChessBoard.hasPiece(ChessBoard.board[x][y_copy+1])){
                break;//如果遇到棋子，则停止检查。
            }
        }

        x_copy = x;//还原x_copy的值。
        y_copy = y;//还原y_copy的值。

        for (;x_copy > 0;x_copy--){//把x_copy放在这里的原因是每一次检查完之后，无论格子是否符合要求，都要把x_copy减一以检查下一个格子。
            if (!ChessBoard.hasPiece(ChessBoard.board[x_copy-1][y])){
                chess_W_BlockList_moments.add(ChessBoard.board[x_copy-1][y]);
            }else if(ChessBoard.hasPiece(ChessBoard.board[x_copy-1][y])){
                break;//如果遇到棋子，则停止检查。
            }
        }

        for (;y_copy > 0;y_copy--){
            if (!ChessBoard.hasPiece(ChessBoard.board[x][y_copy-1])){
                chess_W_BlockList_moments.add(ChessBoard.board[x][y_copy-1]);
            }else if(ChessBoard.hasPiece(ChessBoard.board[x][y_copy-1])){
                break;//如果遇到棋子，则停止检查。
            }
        }

        JButton[] chessBlockList_official = new JButton[chess_W_BlockList_moments.size()];//将ArrayList转换为JButton数组。
        chessBlockList_official = chess_W_BlockList_moments.toArray(chessBlockList_official);

        /*for (JButton chess_blocks : chessBlockList_official){
            ChessBoard.findElement_test(ChessBoard.board,chess_blocks,"car_W_move");//测试能否找到元素。
        }*/

        return chessBlockList_official;
    }

    @Override
    public JButton[] scan_B_ChessBlock_canWalk() {
        throw new IllegalArgumentException("This is White movement behavior, can not scan black piece can walked blocks.");
    }

    private boolean real_W_move(JButton target_chess_block){
        //获取目标格子的坐标。
        int target_x = ChessBoard.findElement(ChessBoard.board,target_chess_block)[0];
        int target_y = ChessBoard.findElement(ChessBoard.board,target_chess_block)[1];
        //判断是否可以移动到目标格子。
        if(target_x < ChessBoard.ROWS &&//防止棋子出界并判断下一个格子是否可以移动。
                chess_W_BlockList_moment_car.contains(ChessBoard.getChessBoardElement(target_x,target_y))
                /*&& target_y < ChessBoard.COLS && target_y >= 0 && target_x >= 0*/){//可能没用，先注释掉。

            //获取要去的方块此时的贴图。
            ImageIcon targetImageIcon = (ImageIcon) target_chess_block.getIcon();

            ChessBoard.changeChessBoard(x,y,car.getChess_block_iconImage());//将车目前待在这儿的方块还原成士兵没来这时的样子。
            car.setChess_block_iconImage(targetImageIcon);//将车将要去的方块目前的状况记录下来。
            //将棋子所绑定的格子替换为目标格子。
            car.setChess_block(target_chess_block);


            if(targetImageIcon == ChessBoard.WHITE){//如要去的格子是白格子，则把要去的格子的背景换成相应棋子在白格子下的背景。

                ChessBoard.changeChessBoard(target_x,target_y,ChessBoard.WHITE_CAR_W);

            }else {//不然则把要去的格子的背景换成相应棋子在黑格子下的背景。

                ChessBoard.changeChessBoard(target_x,target_y,ChessBoard.WHITE_CAR_B);

            }
            ChessBoard.getChessBoardElement(target_x,target_y).repaint();//重绘移动完后格子的贴图。
            updateLocation();//实时获取现在所处在的格子和位置。
            updatePieceList();//更新棋子可以移动的格子列表。

            return true;//移动成功。
        }
        return false;//移动失败。
    }

    public void updateLocation(){
        //实时获取现在所处在的格子和位置。
        nowPosition = car.getChess_block();
        x = ChessBoard.findElement(ChessBoard.board,nowPosition)[0];
        y = ChessBoard.findElement(ChessBoard.board,nowPosition)[1];
    }

    private void updatePieceList(){
        if(WhitePlayer.isInWhitePiecesList(car)){
            //每次执行移动操作的时候，都需要重新判断可以移动的格子，以防上一次遇到不可移动的格子之后，将棋子卡在原地无法行动。
            chess_W_BlockList_moment_car.clear();//在每一次排查完合适的格子之后，删除原来的列表。以防止之前过时的格子还能生效。
            chess_W_BlockList_moment_car.addAll(List.of(scan_W_ChessBlock_canWalk()));//更新白棋可以移动的格子列表。
            car.getAttackBehavior().scan_W_canAttack();//更新白棋可以攻击的格子列表。
        }
    }
}
