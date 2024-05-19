package Behaviors;

import BackgroundThings.ChessBoard;
import Chesspieces.AbstractChessPiece;
import Chesspieces.Soldier;
import Players.WhitePlayer;
import Players.BlackPlayer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SoldiersMovement implements MoveBehavior{
    private int x;
    private int y;
    private JButton nowPosition;
    private ArrayList<JButton>  chess_W_BlockList_moment = new ArrayList<>();
    private ArrayList<JButton>  chess_B_BlockList_moment = new ArrayList<>();
    Soldier abstractChessPiece;
    public SoldiersMovement(Soldier chessPiece){
        abstractChessPiece = chessPiece;
        updateLocation();//初始化时，实时获取现在所处在的格子和位置。
    }
    @Override
    public boolean move(JButton target_chess_block) {
        int distance;
        if(WhitePlayer.isInWhitePiecesList(abstractChessPiece)){
            distance = ChessBoard.findElement(ChessBoard.board,target_chess_block)[0] - x;
        }else{
            //因为黑子的走法是反着的，所以要取反保证distance是正数。
            distance = -(ChessBoard.findElement(ChessBoard.board,target_chess_block)[0] - x);
        }

        updatePieceList();
        if (abstractChessPiece.getFirstMove() && distance <= 2 && abstractChessPiece.getChoiceState()
                == AbstractChessPiece.ChoiceState.CHOICE_ABLE){
            if(WhitePlayer.isInWhitePiecesList(abstractChessPiece)){
                return real_W_move(target_chess_block,distance);
            }else if(BlackPlayer.isInBlackPieceList(abstractChessPiece)){
                return real_B_move(target_chess_block,distance);
            }
        }else if(distance <= 1 && abstractChessPiece.getChoiceState()
                == AbstractChessPiece.ChoiceState.CHOICE_ABLE) {
            if(WhitePlayer.isInWhitePiecesList(abstractChessPiece)){
                return real_W_move(target_chess_block,1);
            }else if(BlackPlayer.isInBlackPieceList(abstractChessPiece)){
                return real_B_move(target_chess_block,1);
            }
        }
        return false;
    }

    @Override
    public JButton[] scan_W_ChessBlock_canWalk() {
        ArrayList<JButton> chess_W_BlockList_moments = new ArrayList<>();
        int x_copy = x;//复制x的值，以防止在循环中修改原值。
        for (;x_copy + 1 <ChessBoard.ROWS;x_copy++){//把x_copy放在这里的原因是每一次检查完之后，无论格子是否符合要求，都要把x_copy加一以检查下一个格子。
            if (!ChessBoard.hasPiece(ChessBoard.board[x_copy+1][y])){
                chess_W_BlockList_moments.add(ChessBoard.board[x_copy+1][y]);
            }
        }

        JButton[] chessBlockList_official = new JButton[chess_W_BlockList_moments.size()];//将ArrayList转换为JButton数组。
        chessBlockList_official = chess_W_BlockList_moments.toArray(chessBlockList_official);

        return chessBlockList_official;
    }

    @Override
    public JButton[] scan_B_ChessBlock_canWalk() {
        ArrayList<JButton> chess_B_BlockList_moments = new ArrayList<>();
        int x_copy = x;//复制x的值，以防止在循环中修改原值。
        for (;x_copy > 0;x_copy--){//把x_copy放在这里的原因是每一次检查完之后，无论格子是否符合要求，都要把x_copy减一以检查下一个格子。
            if (!ChessBoard.hasPiece(ChessBoard.board[x_copy-1][y])){
                chess_B_BlockList_moments.add(ChessBoard.board[x_copy-1][y]);
            }
        }

        JButton[] chessBlockList_officials = new JButton[chess_B_BlockList_moments.size()];//将ArrayList转换为JButton数组。
        chessBlockList_officials = chess_B_BlockList_moments.toArray(chessBlockList_officials);

        return chessBlockList_officials;
    }
    private boolean real_W_move(JButton target_chess_block,int distance){
        if(x+distance < ChessBoard.ROWS &&//防止棋子出界并判断下一个格子是否可以移动。
                chess_W_BlockList_moment.contains(ChessBoard.getChessBoardElement(x+distance,y))
        && chess_W_BlockList_moment.contains(target_chess_block)){

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
            updateLocation();//实时获取现在所处在的格子和位置。
            updatePieceList();//更新棋子可以移动的格子列表。

            return true;//移动成功。
        }
        return false;//移动失败。
    }
    private boolean real_B_move(JButton target_chess_block,int distance){
        //System.out.println("Is black move: "+ChessBoard.getChessBoardElement(x-distance,y));//test
        if(x-distance >= 0 &&//防止棋子出界并判断下一个格子是否可以移动。
                chess_B_BlockList_moment.contains(ChessBoard.getChessBoardElement(x-distance,y))
                && chess_B_BlockList_moment.contains(target_chess_block)){

            //获取当前所处方块此时的贴图。
            ImageIcon nowIcon = (ImageIcon) nowPosition.getIcon();//我在JButton的构造函数中传入的就是ImageIcon所以这里可以强转

            //获取要去的方块此时的贴图。
            ImageIcon targetImageIcon = (ImageIcon) target_chess_block.getIcon();

            ChessBoard.changeChessBoard(x,y, abstractChessPiece.getChess_block_iconImage());//将士兵目前待在这儿的方块还原成士兵没来这时的样子。
            abstractChessPiece.setChess_block_iconImage(targetImageIcon);//将士兵将要去的方块目前的状况记录下来。
            //将棋子所绑定的格子替换为目标格子。
            abstractChessPiece.setChess_block(target_chess_block);

            if(targetImageIcon == ChessBoard.WHITE){//如要去的格子是白格子，则把要去的格子的背景换成相应棋子在白格子下的背景。
                ChessBoard.changeChessBoard(x-distance,y,ChessBoard.BLACK_SOLDIER_W);

            }else {//不然则把要去的格子的背景换成相应棋子在黑格子下的背景。

                ChessBoard.changeChessBoard(x-distance,y,ChessBoard.BLACK_SOLDIER_B);
            }
            ChessBoard.getChessBoardElement(x-distance,y).repaint();//重绘移动完后格子的贴图。
            updateLocation();//实时获取现在所处在的格子和位置。
            updatePieceList();//更新棋子可以移动的格子列表。

            return true;//移动成功。
        }
        return false;//移动失败。
    }

    private void updateLocation(){
        //实时获取现在所处在的格子和位置。
        nowPosition = abstractChessPiece.getChess_block();
        x = ChessBoard.findElement(ChessBoard.board,nowPosition)[0];
        y = ChessBoard.findElement(ChessBoard.board,nowPosition)[1];

    }
    private void updatePieceList(){
        if(WhitePlayer.isInWhitePiecesList(abstractChessPiece)){
            //每次执行移动操作的时候，都需要重新判断可以移动的格子，以防上一次遇到不可移动的格子之后，将棋子卡在原地无法行动。
            chess_W_BlockList_moment.clear();//在每一次排查完合适的格子之后，删除原来的列表。以防止之前过时的格子还能生效。
            chess_W_BlockList_moment.addAll(List.of(scan_W_ChessBlock_canWalk()));
        }else if(BlackPlayer.isInBlackPieceList(abstractChessPiece)){
            chess_B_BlockList_moment.clear();
            chess_B_BlockList_moment.addAll(List.of(scan_B_ChessBlock_canWalk()));
        }
    }
}
