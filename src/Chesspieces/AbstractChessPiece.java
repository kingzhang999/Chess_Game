package Chesspieces;

import Behaviors.AttackBehavior;
import Behaviors.MoveBehavior;

import javax.swing.*;

public abstract class AbstractChessPiece {
    protected JButton chess_block;//保存棋子目前所在的格子
    protected ImageIcon chess_piece;
    protected ImageIcon chess_block_iconImage;//记录方块所在格子在棋子来之前的贴图。在棋子离开后方便复原方块
    protected MoveBehavior moveBehavior;
    protected AttackBehavior attackBehavior;
    protected JButton[] chessBlockList_canWalk;

    public AbstractChessPiece(JButton chess_blocks,ImageIcon chess_piece){
        chess_block = chess_blocks;
        this.chess_piece = chess_piece;
        chess_block_iconImage = (ImageIcon) chess_block.getIcon();
        //我在JButton的构造函数中传入的就是ImageIcon所以这里可以强转
    }
    public void move(){
        moveBehavior.move();
    }
    public void attack(){
        attackBehavior.attack();
    }

    public void setAttackBehavior(AttackBehavior attackBehavior) {
        this.attackBehavior = attackBehavior;
    }

    public void setMoveBehavior(MoveBehavior moveBehavior) {
        this.moveBehavior = moveBehavior;
    }

    public void setChess_block(JButton chess_blocks) {
        this.chess_block = chess_blocks;
    }

    public void setChess_piece(ImageIcon chess_piece) {
        this.chess_piece = chess_piece;
    }

    public JButton getChess_block() {
        return this.chess_block;
    }

    public ImageIcon getChess_piece() {
        return chess_piece;
    }
    public void setChess_block_iconImage(ImageIcon newIcon){
        this.chess_block_iconImage = newIcon;
    }

    public ImageIcon getChess_block_iconImage() {
        return this.chess_block_iconImage;
    }
}
