package BackgroundThings;

import Chesspieces.*;
import Players.BlackPlayer;
import Players.WhitePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChessBoard extends JPanel {
    public static final int ROWS = 8;
    public static final int COLS = 8;
    public static final int CELL_SIZE = 50;
    public static final int CHESS_PIECE_NUMBER = 32;
    private static int chess_piece_list_top = 0;
    public static final File DEFAULT_MANUAL_FILE = new File("resource/manuals/manual.txt");
    public static final Font WINNER_FONT = new Font("Arial", Font.BOLD, 50);
    public static final ImageIcon WHITE = new ImageIcon("resource/white.jpg");
    public static final ImageIcon BLACK = new ImageIcon("resource/black.jpg");
    public static final WhitePiece WHITE_SOLDIER_W = new WhitePiece("resource/white_soldier_in_white.jpg",ChessBoard.BackGroundType.WhiteBack,ChessBoard.PieceType.Soldier);
    public static final WhitePiece WHITE_SOLDIER_B = new WhitePiece("resource/white_soldier_in_black.jpg",ChessBoard.BackGroundType.BlackBack,ChessBoard.PieceType.Soldier);
    public static final BlackPiece BLACK_SOLDIER_W = new BlackPiece("resource/black_soldier_in_white.jpg",ChessBoard.BackGroundType.WhiteBack,ChessBoard.PieceType.Soldier);
    public static final BlackPiece BLACK_SOLDIER_B = new BlackPiece("resource/black_soldier_in_black.jpg",ChessBoard.BackGroundType.BlackBack,ChessBoard.PieceType.Soldier);
    public static final WhitePiece WHITE_CAR_W = new WhitePiece("resource/white_car_in_white.jpg",ChessBoard.BackGroundType.WhiteBack,ChessBoard.PieceType.Car);
    public static final WhitePiece WHITE_CAR_B = new WhitePiece("resource/white_car_in_black.jpg",ChessBoard.BackGroundType.BlackBack,ChessBoard.PieceType.Car);
    public static final BlackPiece BLACK_CAR_W = new BlackPiece("resource/black_car_in_white.jpg",ChessBoard.BackGroundType.WhiteBack,ChessBoard.PieceType.Car);
    public static final BlackPiece BLACK_CAR_B = new BlackPiece("resource/black_car_in_black.jpg",ChessBoard.BackGroundType.BlackBack,ChessBoard.PieceType.Car);
    public static final WhitePiece WHITE_HORSE_W = new WhitePiece("resource/white_horse_in_white.jpg",ChessBoard.BackGroundType.WhiteBack,ChessBoard.PieceType.Horse);
    public static final WhitePiece WHITE_HORSE_B = new WhitePiece("resource/white_horse_in_black.jpg",ChessBoard.BackGroundType.BlackBack,ChessBoard.PieceType.Horse);
    public static final BlackPiece BLACK_HORSE_W = new BlackPiece("resource/black_horse_in_white.jpg",ChessBoard.BackGroundType.WhiteBack,ChessBoard.PieceType.Horse);
    public static final BlackPiece BLACK_HORSE_B = new BlackPiece("resource/black_horse_in_black.jpg",ChessBoard.BackGroundType.BlackBack,ChessBoard.PieceType.Horse);
    public static final WhitePiece WHITE_ELEPHANT_W = new WhitePiece("resource/white_elephant_in_white.jpg",ChessBoard.BackGroundType.WhiteBack,ChessBoard.PieceType.Elephant);
    public static final WhitePiece WHITE_ELEPHANT_B = new WhitePiece("resource/white_elephant_in_black.jpg",ChessBoard.BackGroundType.BlackBack,ChessBoard.PieceType.Elephant);
    public static final BlackPiece BLACK_ELEPHANT_W = new BlackPiece("resource/black_elephant_in_white.jpg",ChessBoard.BackGroundType.WhiteBack,ChessBoard.PieceType.Elephant);
    public static final BlackPiece BLACK_ELEPHANT_B = new BlackPiece("resource/black_elephant_in_black.jpg",ChessBoard.BackGroundType.BlackBack,ChessBoard.PieceType.Elephant);
    public static final WhitePiece WHITE_QUEEN_W = new WhitePiece("resource/white_queen_in_white.jpg",ChessBoard.BackGroundType.WhiteBack,ChessBoard.PieceType.Queen);
    public static final WhitePiece WHITE_QUEEN_B = new WhitePiece("resource/white_queen_in_black.jpg",ChessBoard.BackGroundType.BlackBack,ChessBoard.PieceType.Queen);
    public static final BlackPiece BLACK_QUEEN_W = new BlackPiece("resource/black_queen_in_white.jpg",ChessBoard.BackGroundType.WhiteBack,ChessBoard.PieceType.Queen);
    public static final BlackPiece BLACK_QUEEN_B = new BlackPiece("resource/black_queen_in_black.jpg",ChessBoard.BackGroundType.BlackBack,ChessBoard.PieceType.Queen);
    public static final WhitePiece WHITE_KING_W = new WhitePiece("resource/white_king_in_white.jpg",ChessBoard.BackGroundType.WhiteBack,ChessBoard.PieceType.King);
    public static final WhitePiece WHITE_KING_B = new WhitePiece("resource/white_king_in_black.jpg",ChessBoard.BackGroundType.BlackBack,ChessBoard.PieceType.King);
    public static final BlackPiece BLACK_KING_W = new BlackPiece("resource/black_king_in_white.jpg",ChessBoard.BackGroundType.WhiteBack,ChessBoard.PieceType.King);
    public static final BlackPiece BLACK_KING_B = new BlackPiece("resource/black_king_in_black.jpg",ChessBoard.BackGroundType.BlackBack,ChessBoard.PieceType.King);

    public volatile static ChessBoard chessBoard = null;
    public static GameTurn gameTurn = GameTurn.WHITE_TURN;
    public static JButton[][] board;
    public static AbstractChessPiece[] all_chess_piece_list;

    private ChessBoard() {
        setLayout(new GridLayout(COLS,ROWS));
        board = new JButton[ROWS][COLS];
        all_chess_piece_list = new AbstractChessPiece[CHESS_PIECE_NUMBER];
        initializeBoard(DEFAULT_MANUAL_FILE);
    }

    public void initializeBoard(File file) {

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
        //初始化棋子
        initializeChessPieces(file);
    }

    private void initializeChessPieces(File file){
        int x = 0;
        int y = 0;
        for (String line : Objects.requireNonNull(readManual(file))){
            switch (line) {
                case "0" -> {
                    decideToPutWhatPiece(PieceType.Car, x, y, WHITE_CAR_W, WHITE_CAR_B);
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "1" -> {
                    decideToPutWhatPiece(PieceType.Horse, x, y, WHITE_HORSE_W, WHITE_HORSE_B);
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "2" -> {
                    decideToPutWhatPiece(PieceType.Elephant, x, y, WHITE_ELEPHANT_W, WHITE_ELEPHANT_B);
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "3" -> {
                    decideToPutWhatPiece(PieceType.Queen, x, y, WHITE_QUEEN_W, WHITE_QUEEN_B);
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "4" -> {
                    decideToPutWhatPiece(PieceType.King, x, y, WHITE_KING_W, WHITE_KING_B);
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "5" -> {
                    decideToPutWhatPiece(PieceType.Soldier, x, y, WHITE_SOLDIER_W, WHITE_SOLDIER_B);
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "6" -> {
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "7" -> {
                    decideToPutWhatPiece(PieceType.Soldier, x, y, BLACK_SOLDIER_W, BLACK_SOLDIER_B);
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "8" -> {
                    decideToPutWhatPiece(PieceType.Car, x, y, BLACK_CAR_W, BLACK_CAR_B);
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "9" -> {
                    decideToPutWhatPiece(PieceType.Horse, x, y, BLACK_HORSE_W, BLACK_HORSE_B);
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "10" -> {
                    decideToPutWhatPiece(PieceType.Elephant, x, y, BLACK_ELEPHANT_W, BLACK_ELEPHANT_B);
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "11" -> {
                    decideToPutWhatPiece(PieceType.Queen, x, y, BLACK_QUEEN_W, BLACK_QUEEN_B);
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "12" -> {
                    decideToPutWhatPiece(PieceType.King, x, y, BLACK_KING_W, BLACK_KING_B);
                    if (y + 1 >= COLS) {
                        x += 1;
                        y = 0;
                    } else {
                        y += 1;
                    }
                }
                case "White" -> setGameTurn(GameTurn.WHITE_TURN);
                case "Black" -> setGameTurn(GameTurn.BLACK_TURN);
            }
        }
    }

    private void decideToPutWhatPiece(PieceType pieceType,int x_copy, int y_copy,ImageIcon chess_face_w,ImageIcon chess_face_b){
        if(x_copy % 2 == 0){
            if(y_copy % 2 == 0){
                createChessPiece(pieceType,board[x_copy][y_copy],chess_face_b);
            }else {
                createChessPiece(pieceType,board[x_copy][y_copy],chess_face_w);
            }
        }else {
            if(y_copy % 2 == 0){
                createChessPiece(pieceType,board[x_copy][y_copy],chess_face_w);
            }else {
                createChessPiece(pieceType,board[x_copy][y_copy],chess_face_b);
            }
        }
    }

    private ArrayList<String> readManual(File file){
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            ArrayList<String> str = new ArrayList<>();
            String line;
            while((line = br.readLine())!= null){
                //System.out.println("Reading line: "+br.readLine());
                str.addAll(List.of(line.split(",")));

            }
            return str;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
        for (int i = 0; i < CHESS_PIECE_NUMBER; i++) {
            //在for循环中使用CHESS_PIECE_NUMBER作为循环次数是为了避免删除了一个在数组靠后位置的元素时，
            //chess_piece_list_top自动减一，如果后面需要删除更靠后的元素时，无法循环到那个元素。以至于导致错误。
            if (all_chess_piece_list[i] == piece) {
                all_chess_piece_list[i] = null;
                System.gc();//垃圾回收
                chess_piece_list_top--;
                break;
            }
        }
    }

    public boolean isWhiteWin(){
        for(AbstractChessPiece piece : BlackPlayer.black_pieces_list){
            if(piece instanceof King){
                return false;
            }
        }
        return true;
    }

    public boolean isBlackWin(){
        for(AbstractChessPiece piece : WhitePlayer.white_pieces_list){
            if(piece instanceof King){
                return false;
            }
        }
        return true;
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
            case Horse -> {
                if (chess_piece instanceof WhitePiece){
                    Horse horse = new Horse(chess_block,chess_piece);
                    addChessPiece(horse);//将创建好的棋子放入数组中统一管理
                    //将白棋加入白棋子列表中
                    WhitePlayer.add_W_Piece(horse);
                    return;
                }else if (chess_piece instanceof BlackPiece){
                    Horse horse = new Horse(chess_block,chess_piece);
                    addChessPiece(horse);//将创建好的棋子放入数组中统一管理
                    //将黑棋子加入黑棋子列表中
                    BlackPlayer.add_B_Piece(horse);
                    return;
                }
            }
            case Elephant -> {
                if (chess_piece instanceof WhitePiece){
                    Elephant elephant = new Elephant(chess_block,chess_piece);
                    addChessPiece(elephant);//将创建好的棋子放入数组中统一管理
                    //将白棋加入白棋子列表中
                    WhitePlayer.add_W_Piece(elephant);
                    return;
                }else if (chess_piece instanceof BlackPiece){
                    Elephant elephant = new Elephant(chess_block,chess_piece);
                    addChessPiece(elephant);//将创建好的棋子放入数组中统一管理
                    //将黑棋子加入黑棋子列表中
                    BlackPlayer.add_B_Piece(elephant);
                    return;
                }
            }
            case Queen -> {
                if (chess_piece instanceof WhitePiece){
                    Queen queen = new Queen(chess_block,chess_piece);
                    addChessPiece(queen);//将创建好的棋子放入数组中统一管理
                    //将白棋加入白棋子列表中
                    WhitePlayer.add_W_Piece(queen);
                    return;
                }else if (chess_piece instanceof BlackPiece){
                    Queen queen = new Queen(chess_block,chess_piece);
                    addChessPiece(queen);//将创建好的棋子放入数组中统一管理
                    //将黑棋子加入黑棋子列表中
                    BlackPlayer.add_B_Piece(queen);
                    return;
                }
            }
            case King -> {
                if (chess_piece instanceof WhitePiece){
                    King king = new King(chess_block,chess_piece);
                    addChessPiece(king);//将创建好的棋子放入数组中统一管理
                    //将白棋加入白棋子列表中
                    WhitePlayer.add_W_Piece(king);
                    return;
                }else if (chess_piece instanceof BlackPiece){
                    King king = new King(chess_block,chess_piece);
                    addChessPiece(king);//将创建好的棋子放入数组中统一管理
                    //将黑棋子加入黑棋子列表中
                    BlackPlayer.add_B_Piece(king);
                    return;
                }
            }
            default -> throw new IllegalArgumentException("NO SUCH TYPE PIECE");
        }
        throw new IllegalArgumentException("NO SUCH TYPE PIECE");
    }

    public void removeAllChessPiecesAndBlocks() {
        for(int i = 0; i < CHESS_PIECE_NUMBER; i++){
            all_chess_piece_list[i] = null;
            System.gc();
            chess_piece_list_top = 0;
        }

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                board[i][j] = null;
            }
        }
    }//此方法暂时废弃

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
            GameScreen.addNotice("White turn",null);
            //System.out.println("hasPieces && WhitePlayer.w_readyToMove.isEmpty: "+(hasPieces && WhitePlayer.w_readyToMove.isEmpty()));
            if (hasPieces && WhitePlayer.w_readyToMove.isEmpty()) {//检查当前格子是否有棋子并且确保当前没有棋子需要移动。

                //遍历棋子列表，找到触发ActionEvent的格子上所对应的棋子实例。
                AbstractChessPiece thePieceOnTrigger = findChessPiece(trigger);

                //如果发现此时被选中的棋子不是白棋，那么就把当前棋子拒绝加入到ReadyToMove列表中。
                if (WhitePlayer.isInWhitePiecesList(thePieceOnTrigger)){
                    //System.out.println("This piece is white piece.");//test
                    //将棋子设为已选择状态。
                    thePieceOnTrigger.setChoiceState(AbstractChessPiece.ChoiceState.CHOICE_ABLE);
                    WhitePlayer.add_W_ReadyToMove(thePieceOnTrigger);

                    //thePieceOnTrigger.move();//test
                }

            } else if (!WhitePlayer.w_readyToMove.isEmpty() && !hasPieces) {

                AbstractChessPiece abstractChessPiece = WhitePlayer.getNext_W_ReadyToMove();
                boolean isSuccess = abstractChessPiece.move(trigger);//移动棋子。
                //System.out.println("isSuccess: "+isSuccess);//test

                if (isSuccess) {
                    //移动完后将棋子恢复至未被选择状态。
                    abstractChessPiece.setChoiceState(AbstractChessPiece.ChoiceState.UN_CHOICE);
                    //移动成功后才将Solder的isFirstMove设为false。
                    if(abstractChessPiece instanceof Soldier){
                        ((Soldier) abstractChessPiece).setFirstMove(false);
                    }//如果移动成功，则判断当前轮到谁走
                    if(isWhiteWin()){//如果白棋胜利，则显示胜利信息。
                        GameScreen.addNotice("White Win!",WINNER_FONT);
                    }
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
                    if(isWhiteWin()){//如果白棋胜利，则显示胜利信息。
                        GameScreen.addNotice("White Win!",WINNER_FONT);
                    }
                    changeSide();
                }
            }
        }

        private void blackPlayerMove(boolean hasPieces, JButton trigger) {
            GameScreen.addNotice("Black turn",null);
            if (hasPieces && BlackPlayer.b_readyToMove.isEmpty()) {//检查当前格子是否有棋子并且确保当前没有棋子需要移动。

                //遍历棋子列表，找到触发ActionEvent的格子上所对应的棋子实例。
                AbstractChessPiece thePieceOnTrigger = findChessPiece(trigger);

                //如果发现此时被选中的棋子不是黑棋，那么就把当前棋子拒绝加入到ReadyToMove列表中。
                if(BlackPlayer.isInBlackPieceList(thePieceOnTrigger)){
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
                    if(isBlackWin()){//如果黑棋胜利，则显示胜利信息。
                        GameScreen.addNotice("Black Win!",WINNER_FONT);
                    }
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
                    if(isBlackWin()){//如果黑棋胜利，则显示胜利信息。
                        GameScreen.addNotice("Black Win!",WINNER_FONT);
                    }
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
    }

    enum GameTurn {
        WHITE_TURN, BLACK_TURN;

        @Override
        public String toString() {
            return switch (this) {
                case WHITE_TURN -> "White";
                case BLACK_TURN -> "Black";
            };
        }
    }
    public enum PieceType {
        Soldier,Queen,Car,Horse,Elephant,King
    }

     public enum BackGroundType {
        WhiteBack,
        BlackBack,
    }

}
