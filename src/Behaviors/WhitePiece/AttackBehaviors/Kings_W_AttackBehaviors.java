package Behaviors.WhitePiece.AttackBehaviors;

import BackgroundThings.ChessBoard;
import Behaviors.AttackBehavior;
import Behaviors.WhitePiece.Movement.Kings_W_Movement;
import Chesspieces.AbstractChessPiece;
import Chesspieces.BlackPiece;
import Chesspieces.King;
import Chesspieces.WhitePiece;
import Players.BlackPlayer;

import javax.swing.*;
import java.util.ArrayList;

public class Kings_W_AttackBehaviors implements AttackBehavior {
    private int x;
    private int y;
    private JButton nowPosition;
    King king;
    private final ArrayList<JButton> attack_able_W_blocks = new ArrayList<>();

    public Kings_W_AttackBehaviors(King king) {
        this.king = king;
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

            //车走后便可以把要去的这个方块还原成没有车在这里时的样子。
            ImageIcon realImageIcon;
            if(targetImageIcon.getBackground() == ChessBoard.BackGroundType.WhiteBack){
                realImageIcon = ChessBoard.WHITE;
            }else {
                realImageIcon = ChessBoard.BLACK;
            }

            ChessBoard.changeChessBoard(x,y,king.getChess_block_iconImage());//将车目前待在这儿的方块还原成士兵没来这时的样子。
            king.setChess_block_iconImage(realImageIcon);//将车将要去的方块目前的状况记录下来。

            for(AbstractChessPiece king1 : ChessBoard.all_chess_piece_list){
                if(king1 != null && king1.getChess_block() == being_attacked_chess_block){
                    deletePieces(king1);//将要去的方块上的敌方棋子删除。
                    //System.out.println("delete: "+soldier1);
                }
            }

            //将棋子所绑定的格子替换为目标格子。
            king.setChess_block(being_attacked_chess_block);

            //System.out.println("targetImageIcon instanceof WhiteBackGround: "+(targetImageIcon instanceof WhiteBackGround));
            if(targetImageIcon.getBackground() == ChessBoard.BackGroundType.WhiteBack){//如要去的格子是白格子，则把要去的格子的背景换成相应棋子在白格子下的背景。

                ChessBoard.changeChessBoard(x_future,y_future,ChessBoard.WHITE_KING_W);

            }else {//不然则把要去的格子的背景换成相应棋子在黑格子下的背景。

                ChessBoard.changeChessBoard(x_future,y_future,ChessBoard.WHITE_KING_B);

            }
            ChessBoard.getChessBoardElement(x_future,y_future).repaint();//重绘移动完后格子的贴图。
            ((Kings_W_Movement)king.getMoveBehavior()).updateLocation();//更新移动行为中棋子的所在位置。
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

        int x_copy = x;
        int y_copy = y;

        //System.out.println("x_copy: "+x_copy);//test
        ArrayList<JButton> attack_able_W_blocks_temp = new ArrayList<>();//用于临时存储可攻击的位置
        //检查下方的格子。
        if (x_copy + 1 < ChessBoard.ROWS){//把x_copy放在这里的原因是每一次检查完之后，无论格子是否符合要求，都要把x_copy加一以检查下一个格子。
            if (ChessBoard.hasPiece(ChessBoard.board[x_copy+1][y]) &&
                    !(ChessBoard.getChessBoardElement(x_copy + 1,y).getIcon() instanceof WhitePiece)){
                attack_able_W_blocks_temp.add(ChessBoard.board[x_copy+1][y]);
            }
        }

        //检查右方的格子。
        if (y_copy + 1 < ChessBoard.COLS){
            if (ChessBoard.hasPiece(ChessBoard.board[x][y_copy+1]) &&
                    !(ChessBoard.getChessBoardElement(x,y_copy + 1).getIcon() instanceof WhitePiece)){
                attack_able_W_blocks_temp.add(ChessBoard.board[x][y_copy+1]);
            }
        }

        //检查上方的格子。
        if (x_copy > 0){//把x_copy放在这里的原因是每一次检查完之后，无论格子是否符合要求，都要把x_copy减一以检查下一个格子。
            if (ChessBoard.hasPiece(ChessBoard.board[x_copy-1][y]) &&
                    !(ChessBoard.getChessBoardElement(x_copy - 1,y).getIcon() instanceof WhitePiece)) {
                attack_able_W_blocks_temp.add(ChessBoard.board[x_copy - 1][y]);
            }
        }

        //检查左方的格子。
        if (y_copy > 0){
            if (ChessBoard.hasPiece(ChessBoard.board[x][y_copy-1]) &&
                    !(ChessBoard.getChessBoardElement(x,y_copy - 1).getIcon() instanceof WhitePiece)){
                attack_able_W_blocks_temp.add(ChessBoard.board[x][y_copy-1]);
            }
        }

        //检查左上方的格子。
        if (x_copy - 1 >= 0 && y_copy - 1 >= 0){//把x_copy放在这里的原因是每一次检查完之后，无论格子是否符合要求，都要把x_copy加一以检查下一个格子。
            if (ChessBoard.hasPiece(ChessBoard.board[x_copy-1][y_copy-1]) &&
                    !(ChessBoard.getChessBoardElement(x_copy-1,y_copy-1).getIcon() instanceof WhitePiece)){
                attack_able_W_blocks_temp.add(ChessBoard.board[x_copy-1][y_copy-1]);
            }
        }

        //检查右下方的格子。
        if (x_copy + 1 < ChessBoard.ROWS && y_copy + 1 < ChessBoard.COLS){
            if (ChessBoard.hasPiece(ChessBoard.board[x_copy+1][y_copy+1]) &&
                    !(ChessBoard.getChessBoardElement(x_copy+1,y_copy+1).getIcon() instanceof WhitePiece)){
                attack_able_W_blocks_temp.add(ChessBoard.board[x_copy+1][y_copy+1]);
            }
        }

        //检查右上方的格子。
        if (x_copy - 1 >= 0 && y_copy + 1 < ChessBoard.COLS){//把x_copy放在这里的原因是每一次检查完之后，无论格子是否符合要求，都要把x_copy减一以检查下一个格子。
            if (ChessBoard.hasPiece(ChessBoard.board[x_copy-1][y_copy+1]) &&
                    !(ChessBoard.getChessBoardElement(x_copy-1,y_copy+1).getIcon() instanceof WhitePiece)){
                attack_able_W_blocks_temp.add(ChessBoard.board[x_copy-1][y_copy+1]);
            }
        }

        //检查左下方的格子。
        if (x_copy + 1 < ChessBoard.ROWS && y_copy - 1 >= 0){
            if (ChessBoard.hasPiece(ChessBoard.board[x_copy+1][y_copy-1]) &&
                    !(ChessBoard.getChessBoardElement(x_copy+1,y_copy-1).getIcon() instanceof WhitePiece)){
                attack_able_W_blocks_temp.add(ChessBoard.board[x_copy+1][y_copy-1]);
            }
        }

        /*for (JButton chess_blocks : attack_able_W_blocks_temp){
            ChessBoard.findElement_test(ChessBoard.board,chess_blocks,"car_W_attack");//测试能否找到元素。
        }*/

        attack_able_W_blocks.addAll(attack_able_W_blocks_temp);
    }

    @Override
    public void scan_B_canAttack() {
        throw new IllegalArgumentException("This is White Piece attack behavior, can not scan black Piece can attacked blocks.");
    }

    @Override
    public void deletePieces(AbstractChessPiece abstractChessPiece) {
        BlackPlayer.remove_B_Piece(abstractChessPiece);
        ChessBoard.removeChessPiece(abstractChessPiece);
    }

    public void updateLocation(){
        //实时获取现在所处在的格子和位置。
        nowPosition = king.getChess_block();
        x = ChessBoard.findElement(ChessBoard.board,nowPosition)[0];
        y = ChessBoard.findElement(ChessBoard.board,nowPosition)[1];
    }
}
