package BackgroundThings;

import Chesspieces.*;
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
    public static final WhitePiece WHITE_SOLDIER_W = new WhitePiece("resource/white_soldier_in_white.jpg",ChessBoard.BackGroundType.WhiteBack);
    public static final WhitePiece WHITE_SOLDIER_B = new WhitePiece("resource/white_soldier_in_black.jpg",ChessBoard.BackGroundType.BlackBack);
    public static final BlackPiece BLACK_SOLDIER_W = new BlackPiece("resource/black_soldier_in_white.jpg",ChessBoard.BackGroundType.WhiteBack);
    public static final BlackPiece BLACK_SOLDIER_B = new BlackPiece("resource/black_soldier_in_black.jpg",ChessBoard.BackGroundType.BlackBack);
    public static final WhitePiece WHITE_CAR_W = new WhitePiece("resource/white_car_in_white.jpg",ChessBoard.BackGroundType.WhiteBack);
    public static final WhitePiece WHITE_CAR_B = new WhitePiece("resource/white_car_in_black.jpg",ChessBoard.BackGroundType.BlackBack);
    public static final BlackPiece BLACK_CAR_W = new BlackPiece("resource/black_car_in_white.jpg",ChessBoard.BackGroundType.WhiteBack);
    public static final BlackPiece BLACK_CAR_B = new BlackPiece("resource/black_car_in_black.jpg",ChessBoard.BackGroundType.BlackBack);

    public volatile static ChessBoard chessBoard = null;
    public static GameTurn gameTurn = GameTurn.WHITE_TURN;
    public static JButton[][] board;
    public static AbstractChessPiece[] all_chess_piece_list;

    private ChessBoard() {
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
        createChessPiece(PieceType.Soldier,board[1][2],WHITE_SOLDIER_W);//test
        //firstSoldier.move();
        createChessPiece(PieceType.Soldier,board[6][1],BLACK_SOLDIER_W);//test
        createChessPiece(PieceType.Car,board[3][4],WHITE_CAR_W);//test
        createChessPiece(PieceType.Car,board[5][3],BLACK_CAR_B);//test
        createChessPiece(PieceType.Soldier,board[4][7],BLACK_SOLDIER_W);//test
    }

    public static ChessBoard getChessBoard(){
        if(chessBoard == null){
            synchronized (ChessBoard.class){
                if(chessBoard == null){
                    chessBoard = new ChessBoard();
                }
            }
        }
        return chessBoard;
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

    public static int[] findElement_test(JButton[][] array, JButton target,String message) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == target) {
                    System.out.println(message+"元素 " + target + " 在第 " + (i+1) + " 行，第 " + (j+1) + " 列");//test
                    return new int[]{i,j};
                }
            }
        }
        System.out.println("元素 " + target + " 不在数组中");//test
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
        return testObject.getIcon() instanceof WhitePiece || testObject.getIcon() instanceof BlackPiece;
    }
    /* if((ImageIcon)testObject.getIcon() instanceof WhitePiece || (ImageIcon)testObject.getIcon() instanceof BlackPiece){
            return true;
        }else {
            return false;
        }*/

    private void changeSide(){
        if(getGameTurn() == ChessBoard.GameTurn.WHITE_TURN){
            setGameTurn(GameTurn.BLACK_TURN);
        }else{
            setGameTurn(GameTurn.WHITE_TURN);
        }
    }

    private void addChessPiece(AbstractChessPiece chessPiece){
        all_chess_piece_list[chess_piece_list_top] = chessPiece;
        chess_piece_list_top += 1;
    }

    public static void removeChessPiece(AbstractChessPiece piece){
        for (int i = 0; i < chess_piece_list_top; i++) {
            if (all_chess_piece_list[i] == piece) {
                all_chess_piece_list[i] = null;
                System.gc();//垃圾回收
                chess_piece_list_top--;
                break;
            }
        }
    }

    public void createChessPiece(PieceType pieceType, JButton chess_block,
                                 ImageIcon chess_piece){
        switch (pieceType){
            case Soldier -> {
                if (chess_piece instanceof WhitePiece){
                    Soldier soldier = new Soldier(chess_block,chess_piece);
                    addChessPiece(soldier);//将创建好的棋子放入数组中统一管理
                    //将白棋加入白棋子列表中
                    WhitePlayer.add_W_Piece(soldier);
                    return;
                }else if (chess_piece instanceof BlackPiece){
                    Soldier soldier = new Soldier(chess_block,chess_piece);
                    addChessPiece(soldier);//将创建好的棋子放入数组中统一管理
                    //将黑棋子加入黑棋子列表中
                    BlackPlayer.add_B_Piece(soldier);
                    return;
                }
            }
            case Car -> {
                if (chess_piece instanceof WhitePiece){
                    Car car = new Car(chess_block,chess_piece);
                    addChessPiece(car);//将创建好的棋子放入数组中统一管理
                    //将白棋加入白棋子列表中
                    WhitePlayer.add_W_Piece(car);
                    return;
                }else if (chess_piece instanceof BlackPiece){
                   Car car = new Car(chess_block,chess_piece);
                    addChessPiece(car);//将创建好的棋子放入数组中统一管理
                    //将黑棋子加入黑棋子列表中
                    BlackPlayer.add_B_Piece(car);
                    return;
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

                //System.out.println("hasPieces: "+hasPieces);//test

                if (getGameTurn() == GameTurn.WHITE_TURN) {
                    whitePlayerMove(hasPieces, trigger);
                }else if (getGameTurn() == GameTurn.BLACK_TURN) {
                    blackPlayerMove(hasPieces, trigger);
                }
                //System.out.println("This is a test.");
            }
        }

        private void whitePlayerMove(boolean hasPieces, JButton trigger) {
            System.out.println("white turn");
            //System.out.println("hasPieces && WhitePlayer.w_readyToMove.isEmpty: "+(hasPieces && WhitePlayer.w_readyToMove.isEmpty()));
            if (hasPieces && WhitePlayer.w_readyToMove.isEmpty()) {//检查当前格子是否有棋子并且确保当前没有棋子需要移动。

                //遍历棋子列表，找到触发ActionEvent的格子上所对应的棋子实例。
                AbstractChessPiece thePieceOnTrigger = findChessPiece(trigger);

                //如果发现此时被选中的棋子不是白棋，那么就把当前棋子拒绝加入到ReadyToMove列表中。
                if (findPiecesInBlackListOrWhiteList(thePieceOnTrigger)){
                    //System.out.println("This piece is white piece.");//test
                    //将棋子设为已选择状态。
                    thePieceOnTrigger.setChoiceState(AbstractChessPiece.ChoiceState.CHOICE_ABLE);
                    WhitePlayer.add_W_ReadyToMove(thePieceOnTrigger);

                    //thePieceOnTrigger.move();//test
                }

            } else if (!WhitePlayer.w_readyToMove.isEmpty() && !hasPieces) {

                AbstractChessPiece abstractChessPiece = WhitePlayer.getNext_W_ReadyToMove();
                boolean isSuccess = abstractChessPiece.move(trigger);//移动棋子。
                //System.out.println("here");
                //System.out.println("isSuccess: "+isSuccess);//test

                if (isSuccess) {
                    //移动完后将棋子恢复至未被选择状态。
                    abstractChessPiece.setChoiceState(AbstractChessPiece.ChoiceState.UN_CHOICE);
                    //移动成功后才将Solder的isFirstMove设为false。
                    if(abstractChessPiece instanceof Soldier){
                        ((Soldier) abstractChessPiece).setFirstMove(false);
                    }//如果移动成功，则判断当前轮到谁走
                    changeSide();
                }
            } else if (hasPieces && !WhitePlayer.w_readyToMove.isEmpty()) {

                AbstractChessPiece abstractChessPiece = WhitePlayer.getNext_W_ReadyToMove();

                //判断被选中的棋子是否为黑棋，如果是，则进行攻击。
                boolean isSuccess = false;
                if(findChessPiece(trigger).getChess_piece() instanceof BlackPiece){
                    isSuccess = abstractChessPiece.attack(trigger);//移动棋子攻击。
                }

                //System.out.println("isSuccess: "+isSuccess);//test

                if (isSuccess) {
                    //攻击完后将棋子恢复至未被选择状态。
                    abstractChessPiece.setChoiceState(AbstractChessPiece.ChoiceState.UN_CHOICE);
                    //攻击成功后才将Solder的isFirstMove设为false。
                    if(abstractChessPiece instanceof Soldier){
                        ((Soldier) abstractChessPiece).setFirstMove(false);
                    }//如果攻击成功，则判断当前轮到谁走
                    changeSide();
                }
            }
        }

        private void blackPlayerMove(boolean hasPieces, JButton trigger) {
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

            } else if (!BlackPlayer.b_readyToMove.isEmpty() && !hasPieces) {

                AbstractChessPiece abstractChessPiece = BlackPlayer.getNext_B_ReadyToMove();
                boolean isSuccess = abstractChessPiece.move(trigger);

                if (isSuccess) {
                    //移动完后将棋子恢复至未被选择状态。
                    abstractChessPiece.setChoiceState(AbstractChessPiece.ChoiceState.UN_CHOICE);
                    //移动成功后才将Solder的isFirstMove设为false。
                    if(abstractChessPiece instanceof Soldier){
                        ((Soldier) abstractChessPiece).setFirstMove(false);
                    }
                    //如果移动成功，则判断当前轮到谁走
                    changeSide();
                }
            }else if (hasPieces && !BlackPlayer.b_readyToMove.isEmpty()) {

                AbstractChessPiece abstractChessPiece = BlackPlayer.getNext_B_ReadyToMove();

                //判断被选中的棋子是否为白棋，如果是，则进行攻击。
                boolean isSuccess = false;
                if(findChessPiece(trigger).getChess_piece() instanceof WhitePiece){
                    isSuccess = abstractChessPiece.attack(trigger);//移动棋子攻击。
                }

                //System.out.println("isSuccess: "+isSuccess);//test

                if (isSuccess) {
                    //攻击完后将棋子恢复至未被选择状态。
                    abstractChessPiece.setChoiceState(AbstractChessPiece.ChoiceState.UN_CHOICE);
                    //攻击成功后才将Solder的isFirstMove设为false。
                    if(abstractChessPiece instanceof Soldier){
                        ((Soldier) abstractChessPiece).setFirstMove(false);
                    }//如果攻击成功，则判断当前轮到谁走
                    changeSide();
                }
            }
        }

        private AbstractChessPiece findChessPiece(JButton trigger) {

            for (AbstractChessPiece chessPiece : all_chess_piece_list) {
                //System.out.println("ChessPiece: "+chessPiece);//test
                if(chessPiece != null){//防止数组中有空元素。
                    if (trigger == chessPiece.getChess_block()) {
                        return chessPiece;//找到对应棋子后退出循环。
                    }
                }
            }
            throw new IllegalArgumentException("This trigger don't have piece.");
        }

        private boolean findPiecesInBlackListOrWhiteList(AbstractChessPiece thePiece) {

            for (AbstractChessPiece chessPiece : WhitePlayer.white_pieces_list) {
                //System.out.println("W_ChessPiece: "+chessPiece);//test
                if (chessPiece == thePiece) {
                    return true;//找到对应棋子后退出循环。
                }
            }

            for (AbstractChessPiece chessPiece : BlackPlayer.black_pieces_list) {
                //System.out.println("B_ChessPiece: "+chessPiece);//test
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
        Soldier, Queen,Car
    }

     public enum BackGroundType {
        WhiteBack,
        BlackBack,
    }

}
