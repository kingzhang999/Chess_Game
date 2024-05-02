package BackgroundThings;

import Chesspieces.AbstractChessPiece;
import Chesspieces.Soldier;
import Players.WhitePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessBoard extends JPanel {
    public static final int ROWS = 8;
    public static final int COLS = 8;
    public static final int CELL_SIZE = 50;
    public static final int CHESS_PIECE_NUMBER = 32;
    private static int chess_piece_list_top = 0;
    public static final ImageIcon WHITE = new ImageIcon("resource/white.jpg");
    public static final ImageIcon BLACK = new ImageIcon("resource/black.jpg");
    public static final ImageIcon WHITE_SOLDIER_W = new ImageIcon("resource/white_soldier_in_white.jpg");
    public static final ImageIcon WHITE_SOLDIER_B = new ImageIcon("resource/white_soldier_in_black.jpg");
    public static ChessBoard chessBoard = new ChessBoard();

    public static JButton[][] board;
    public static AbstractChessPiece[] all_chess_piece_list;

    public ChessBoard() {
        setLayout(new GridLayout(COLS,ROWS));
        board = new JButton[ROWS][COLS];
        all_chess_piece_list = new AbstractChessPiece[CHESS_PIECE_NUMBER];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int cols=0;cols<COLS;cols++){
            for (int rows=0;rows<ROWS;rows++){
                if (cols % 2 == 0){
                    if(rows % 2 == 0){
                        board[cols][rows] = chessBlockMaker(BLACK);
                    }else {
                        board[cols][rows] = chessBlockMaker(WHITE);
                    }
                }else {
                    if(rows % 2 == 0){
                        board[cols][rows] = chessBlockMaker(WHITE);
                    }else {
                        board[cols][rows] = chessBlockMaker(BLACK);
                    }
                }
                add(board[cols][rows]);
            }
        }
        Soldier firstSoldier = (Soldier) createChessPiece(PieceType.Soldier,board[1][1],WHITE_SOLDIER_B);//test
        //firstSoldier.move();
        Soldier secondSoldier = (Soldier) createChessPiece(PieceType.Soldier,board[3][1],WHITE_SOLDIER_B);//test
    }

    private JButton chessBlockMaker(ImageIcon color) {
        if(color == WHITE){
            JButton button = new JButton(WHITE);
            button.addActionListener(new ClickButtonEvent());
            return button;
        }else if (color == BLACK){
            JButton button = new JButton(BLACK);
            button.addActionListener(new ClickButtonEvent());
            return button;
        }
        throw new IllegalArgumentException("Error Color!");
    }
    public static int[] findElement(JButton[][] array, JButton target) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == target) {
                    //System.out.println("元素 " + target + " 在第 " + (i+1) + " 行，第 " + (j+1) + " 列");//test
                    return new int[]{i,j};
                }
            }
        }
        //System.out.println("元素 " + target + " 不在数组中");/test
        throw new RuntimeException("元素 " + target + " 不在数组中");
    }

    public static void changeChessBoard(int x,int y,ImageIcon things){
        board[x][y].setIcon(things);
    }
    public static JButton getChessBoardElement(int x, int y){
        return board[x][y];
    }
    public static boolean hasPiece(JButton testObject){
        if((ImageIcon)testObject.getIcon() == WHITE_SOLDIER_W){
            return true;
        } else if ((ImageIcon)testObject.getIcon() == WHITE_SOLDIER_B) {
            return true;
        }else {
            return false;
        }
    }
    public static AbstractChessPiece createChessPiece(PieceType pieceType,JButton chess_block,
                                                      ImageIcon chess_piece){
        switch (pieceType){
            case Soldier -> {
                Soldier soldier = new Soldier(chess_block,chess_piece);
                all_chess_piece_list[chess_piece_list_top] = soldier;//将创建好的棋子放入数组中统一管理
                chess_piece_list_top += 1;
                return soldier;
            }
        }
        throw new IllegalArgumentException("NO SUCH TYPE PIECE");
    }
    class ClickButtonEvent implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton trigger){
                boolean hasPieces = hasPiece(trigger);
                if (hasPieces){//检查当前格子是否有棋子。
                    //遍历棋子列表，找到触发ActionEvent的格子上所对应的棋子实例。
                    AbstractChessPiece thePieceOnTrigger = findChessPiece(trigger);
                    thePieceOnTrigger.setChoiceState(AbstractChessPiece.ChoiceState.CHOICE_ABLE);
                    WhitePlayer.w_readyToMove.push(thePieceOnTrigger);
                    //thePieceOnTrigger.move();//test
                } else if (!WhitePlayer.w_readyToMove.isEmpty()) {
                    AbstractChessPiece abstractChessPiece = WhitePlayer.w_readyToMove.pop();
                    abstractChessPiece.move(trigger);
                    if (abstractChessPiece instanceof Soldier){
                        ((Soldier) abstractChessPiece).setFirstMove(false);
                    }
                }
            }
            //System.out.println("This is a test.");
        }
        private AbstractChessPiece findChessPiece(JButton trigger){
            for (AbstractChessPiece chessPiece : all_chess_piece_list){
                if (trigger == chessPiece.getChess_block()){
                    //System.out.println("chessPiece: "+chessPiece);//test
                    //System.out.println("I have piece.");//test
                    return chessPiece;//找到对应棋子后退出循环。
                }
            }
            throw new IllegalArgumentException("This trigger don't have piece.");
        }
    }
    enum PieceType{
        Soldier,Queen
    }
}
