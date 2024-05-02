package Behaviors;

import BackgroundThings.ChessBoard;
import Chesspieces.Soldier;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SoldiersMovement implements MoveBehavior{
    private int x;
    private int y;
    private JButton nowPosition;
    private ArrayList<JButton>  chessBlockList_moment = new ArrayList<>();
    Soldier abstractChessPiece;
    public SoldiersMovement(Soldier chessPiece){
        abstractChessPiece = chessPiece;
        nowPosition = abstractChessPiece.getChess_block();
        x = ChessBoard.findElement(ChessBoard.board,nowPosition)[0];
        y = ChessBoard.findElement(ChessBoard.board,nowPosition)[1];
        chessBlockList_moment.clear();//在每一次排查完合适的格子之后，删除原来的列表。以防止之前过时的格子还能生效。
        chessBlockList_moment.addAll(List.of(scanChessBlock_canWalk()));
    }
    @Override
    public void move(JButton target_chess_block) {
        int distance = ChessBoard.findElement(ChessBoard.board,target_chess_block)[0] - x;
        //每次执行移动操作的时候，都需要重新判断可以移动的格子，以防上一次遇到不可移动的格子之后，将棋子卡在原地无法行动。
        chessBlockList_moment.addAll(List.of(scanChessBlock_canWalk()));
        if (abstractChessPiece.getFirstMove() && distance <= 2){
            if(x+distance < ChessBoard.ROWS &&//防止棋子出界并判断下一个格子是否可以移动。
                    chessBlockList_moment.contains(ChessBoard.getChessBoardElement(x+distance,y))){

                //获取当前所处方块此时的贴图。
                ImageIcon nowIcon = (ImageIcon) nowPosition.getIcon();//我在JButton的构造函数中传入的就是ImageIcon所以这里可以强转

                //获取要去的方块此时的贴图。
                ImageIcon targetImageIcon = (ImageIcon) target_chess_block.getIcon();

                ChessBoard.changeChessBoard(x,y, abstractChessPiece.getChess_block_iconImage());//将士兵目前待在这儿的方块还原成士兵没来这时的样子。
                abstractChessPiece.setChess_block_iconImage(targetImageIcon);//将士兵将要去的方块目前的状况记录下来。
                //将棋子所绑定的格子替换为目标格子。
                abstractChessPiece.setChess_block(target_chess_block);

                if(targetImageIcon == ChessBoard.WHITE){//如要去的格子是白格子，则把要去的格子的背景换成相应棋子在白格子下的背景。
                    ChessBoard.changeChessBoard(x+distance,y,ChessBoard.WHITE_SOLDIER_W);
                }else {//不然则把要去的格子的背景换成相应棋子在黑格子下的背景。
                    ChessBoard.changeChessBoard(x+distance,y,ChessBoard.WHITE_SOLDIER_B);
                }
                ChessBoard.getChessBoardElement(x+distance,y).repaint();//重绘移动完后格子的贴图。
                //实时获取现在所处在的格子和位置。
                nowPosition = abstractChessPiece.getChess_block();
                x = ChessBoard.findElement(ChessBoard.board,nowPosition)[0];
                y = ChessBoard.findElement(ChessBoard.board,nowPosition)[1];
                chessBlockList_moment.clear();//在每一次排查完合适的格子之后，删除原来的列表。以防止之前过时的格子还能生效。
                chessBlockList_moment.addAll(List.of(scanChessBlock_canWalk()));

            }
        }else if(chessBlockList_moment.contains(target_chess_block)) {
            if(x+1 < ChessBoard.ROWS &&//防止棋子出界并判断下一个格子是否可以移动。
                    chessBlockList_moment.contains(ChessBoard.getChessBoardElement(x+1,y))){

                //获取当前所处方块此时的贴图。
                ImageIcon nowIcon = (ImageIcon) nowPosition.getIcon();//我在JButton的构造函数中传入的就是ImageIcon所以这里可以强转

                //获取要去的方块此时的贴图。
                ImageIcon targetImageIcon = (ImageIcon) ChessBoard.getChessBoardElement(x+1,y).getIcon();

                ChessBoard.changeChessBoard(x,y, abstractChessPiece.getChess_block_iconImage());//将士兵目前待在这儿的方块还原成士兵没来这时的样子。
                abstractChessPiece.setChess_block_iconImage(targetImageIcon);//将士兵将要去的方块目前的状况记录下来。
                //将棋子所绑定的格子替换为目标格子。
                abstractChessPiece.setChess_block(ChessBoard.getChessBoardElement(x+1,y));

                if(targetImageIcon == ChessBoard.WHITE){//如要去的格子是白格子，则把要去的格子的背景换成相应棋子在白格子下的背景。
                    ChessBoard.changeChessBoard(x+1,y,ChessBoard.WHITE_SOLDIER_W);
                }else {//不然则把要去的格子的背景换成相应棋子在黑格子下的背景。
                    ChessBoard.changeChessBoard(x+1,y,ChessBoard.WHITE_SOLDIER_B);
                }
                ChessBoard.getChessBoardElement(x+1,y).repaint();//重绘移动完后格子的贴图。
                //实时获取现在所处在的格子和位置。
                nowPosition = abstractChessPiece.getChess_block();
                x = ChessBoard.findElement(ChessBoard.board,nowPosition)[0];
                y = ChessBoard.findElement(ChessBoard.board,nowPosition)[1];
                chessBlockList_moment.clear();//在每一次排查完合适的格子之后，删除原来的列表。以防止之前过时的格子还能生效。
                chessBlockList_moment.addAll(List.of(scanChessBlock_canWalk()));

            }
        }
    }

    @Override
    public JButton[] scanChessBlock_canWalk() {
        ArrayList<JButton> chessBlockList_moments = new ArrayList<>();
        int x_copy = x;
        for (;x_copy + 1 <ChessBoard.ROWS;x_copy++){//把x_copy放在这里的原因是每一次检查完之后，无论格子是否符合要求，都要把x_copy加一以检查下一个格子。
            if (!ChessBoard.hasPiece(ChessBoard.board[x_copy+1][y])){
                chessBlockList_moments.add(ChessBoard.board[x_copy+1][y]);
            }
        }

        JButton[] chessBlockList_official = new JButton[chessBlockList_moments.size()];
        chessBlockList_official = chessBlockList_moments.toArray(chessBlockList_official);

        return chessBlockList_official;
    }
}
