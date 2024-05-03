package BackgroundThings;

import Chesspieces.AbstractChessPiece;
import Chesspieces.Soldier;
import Players.BlackPlayer;
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
    public static final ImageIcon BLACK_SOLDIER_W = new ImageIcon("resource/black_soldier_in_white.jpg");
    public static final ImageIcon BLACK_SOLDIER_B = new ImageIcon("resource/black_soldier_in_black.jpg");
    public static ChessBoard chessBoard = new ChessBoard();
    public static GameTurn gameTurn = GameTurn.WHITE_TURN;

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
        Soldier secondSoldier = (Soldier) createChessPiece(PieceType.Soldier,board[3][1],BLACK_SOLDIER_W);//test
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
    public static void setGameTurn(GameTurn gameTurn){
        ChessBoard.gameTurn = gameTurn;
    }
    public static GameTurn getGameTurn(){
        return gameTurn;
    }

    public static void changeChessBoard(int x,int y,ImageIcon things){
        board[x][y].setIcon(things);
    }
    public static JButton getChessBoardElement(int x, int y){
        return board[x][y];
    }
    public static boolean hasPiece(JButton testObject){//检测棋格上是否有棋子。
        if((ImageIcon)testObject.getIcon() == WHITE_SOLDIER_W){
            return true;
        } else if ((ImageIcon)testObject.getIcon() == WHITE_SOLDIER_B) {
            return true;
        }else if ((ImageIcon)testObject.getIcon() == BLACK_SOLDIER_W) {
            return true;
        }else if ((ImageIcon)testObject.getIcon() == BLACK_SOLDIER_B) {
            return true;
        }
        else {
            return false;
        }
    }
    public static AbstractChessPiece createChessPiece(PieceType pieceType,JButton chess_block,
                                                      ImageIcon chess_piece){
        switch (pieceType){
            case Soldier -> {
                if (chess_piece == WHITE_SOLDIER_W || chess_piece == WHITE_SOLDIER_B){
                    Soldier soldier = new Soldier(chess_block,chess_piece);
                    all_chess_piece_list[chess_piece_list_top] = soldier;//将创建好的棋子放入数组中统一管理
                    chess_piece_list_top += 1;
                    //将白棋加入白棋子列表中
                    WhitePlayer.add_W_Piece(soldier);
                    return soldier;
                }else if (chess_piece == BLACK_SOLDIER_W || chess_piece == BLACK_SOLDIER_B){
                    Soldier soldier = new Soldier(chess_block,chess_piece);
                    all_chess_piece_list[chess_piece_list_top] = soldier;//将创建好的棋子放入数组中统一管理
                    chess_piece_list_top += 1;
                    //将黑棋子加入黑棋子列表中
                    BlackPlayer.add_B_Piece(soldier);
                    return soldier;
                }
            }
            default -> throw new IllegalArgumentException("NO SUCH TYPE PIECE");
        }
        throw new IllegalArgumentException("NO SUCH TYPE PIECE");
    }
    class ClickButtonEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton trigger) {
                boolean hasPieces = hasPiece(trigger);
                if (getGameTurn() == GameTurn.WHITE_TURN) {
                    System.out.println("white turn");
                    if (hasPieces && WhitePlayer.w_readyToMove.isEmpty()) {//检查当前格子是否有棋子并且确保当前没有棋子需要移动。

                        //遍历棋子列表，找到触发ActionEvent的格子上所对应的棋子实例。
                        AbstractChessPiece thePieceOnTrigger = findChessPiece(trigger);

                        //如果发现此时被选中的棋子不是白棋，那么就把当前棋子拒绝加入到ReadyToMove列表中。
                        if (findPiecesInBlackListOrWhiteList(thePieceOnTrigger)){
                            //将棋子设为已选择状态。
                            thePieceOnTrigger.setChoiceState(AbstractChessPiece.ChoiceState.CHOICE_ABLE);
                            WhitePlayer.add_W_ReadyToMove(thePieceOnTrigger);

                            //thePieceOnTrigger.move();//test
                        }

                    } else if (!WhitePlayer.w_readyToMove.isEmpty()) {

                        AbstractChessPiece abstractChessPiece = WhitePlayer.getNext_W_ReadyToMove();
                        boolean isSuccess = abstractChessPiece.move(trigger);//移动棋子。
                        //移动完后将棋子恢复至未被选择状态。
                        abstractChessPiece.setChoiceState(AbstractChessPiece.ChoiceState.UN_CHOICE);

                        if (isSuccess) {
                            //移动成功后才将Solder的isFirstMove设为false。
                            if(abstractChessPiece instanceof Soldier){
                                ((Soldier) abstractChessPiece).setFirstMove(false);
                            }//如果移动成功，则判断当前轮到谁走
                            if(getGameTurn() == ChessBoard.GameTurn.WHITE_TURN){
                                setGameTurn(GameTurn.BLACK_TURN);
                            }else{
                                setGameTurn(GameTurn.WHITE_TURN);
                            }
                        }
                    }
                }else if (getGameTurn() == GameTurn.BLACK_TURN) {
                    System.out.println("Black turn");
                    if (hasPieces && BlackPlayer.b_readyToMove.isEmpty()) {//检查当前格子是否有棋子并且确保当前没有棋子需要移动。

                        //遍历棋子列表，找到触发ActionEvent的格子上所对应的棋子实例。
                        AbstractChessPiece thePieceOnTrigger = findChessPiece(trigger);
                        //如果发现此时被选中的棋子不是黑棋，那么就把当前棋子拒绝加入到ReadyToMove列表中。
                        if(!findPiecesInBlackListOrWhiteList(thePieceOnTrigger)){
                            //将棋子设为已选择状态。
                            thePieceOnTrigger.setChoiceState(AbstractChessPiece.ChoiceState.CHOICE_ABLE);
                            BlackPlayer.add_B_ReadyToMove(thePieceOnTrigger);

                            //thePieceOnTrigger.move();//test
                        }

                    } else if (!BlackPlayer.b_readyToMove.isEmpty()) {

                        AbstractChessPiece abstractChessPiece = BlackPlayer.getNext_B_ReadyToMove();
                        boolean isSuccess = abstractChessPiece.move(trigger);
                        //移动完后将棋子恢复至未被选择状态。
                        abstractChessPiece.setChoiceState(AbstractChessPiece.ChoiceState.UN_CHOICE);

                        if (isSuccess) {
                            //移动成功后才将Solder的isFirstMove设为false。
                            if(abstractChessPiece instanceof Soldier){
                                ((Soldier) abstractChessPiece).setFirstMove(false);
                            }
                            //如果移动成功，则判断当前轮到谁走
                            if(getGameTurn() == ChessBoard.GameTurn.WHITE_TURN){
                                setGameTurn(GameTurn.BLACK_TURN);
                            }else{
                                setGameTurn(GameTurn.WHITE_TURN);
                            }
                        }
                    }
                }
                //System.out.println("This is a test.");
            }
        }

        private AbstractChessPiece findChessPiece(JButton trigger) {

            for (AbstractChessPiece chessPiece : all_chess_piece_list) {
                System.out.println("ChessPiece: "+chessPiece);
                if (trigger == chessPiece.getChess_block()) {
                    return chessPiece;//找到对应棋子后退出循环。
                }
            }
            throw new IllegalArgumentException("This trigger don't have piece.");
        }

        private boolean findPiecesInBlackListOrWhiteList(AbstractChessPiece thePiece) {

            for (AbstractChessPiece chessPiece : WhitePlayer.white_pieces_list) {
                System.out.println("W_ChessPiece: "+chessPiece);
                if (chessPiece == thePiece) {
                    return true;//找到对应棋子后退出循环。
                }
            }

            for (AbstractChessPiece chessPiece : BlackPlayer.black_pieces_list) {
                System.out.println("B_ChessPiece: "+chessPiece);
                if (chessPiece == thePiece) {
                    return false;//找到对应棋子后退出循环。
                }
            }
            throw new IllegalArgumentException("This trigger don't have piece.");
        }
    }

    enum GameTurn {
        WHITE_TURN, BLACK_TURN
    }
    enum PieceType {
        Soldier, Queen
    }

}
