package Chesspieces;

import BackgroundThings.ChessBoard;

import javax.swing.*;

public class WhitePiece extends ImageIcon {
    private final ChessBoard.BackGroundType background;
    public WhitePiece(String path, ChessBoard.BackGroundType background) {
        super(path);
        this.background = background;
    }

    public ChessBoard.BackGroundType getBackground() {
        return background;
    }
}
