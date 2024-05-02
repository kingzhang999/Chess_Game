package Players;

import Chesspieces.AbstractChessPiece;

import javax.swing.*;
import java.util.ArrayList;

public class WhitePlayer {
    public static ArrayList<JButton> white_pieces_list = new ArrayList<>();
    public static ArrayStack<AbstractChessPiece> w_readyToMove = new ArrayStack<>(16);
    public static final WhitePlayer whitePlayer = new WhitePlayer();
}
