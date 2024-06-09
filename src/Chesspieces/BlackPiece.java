package Chesspieces;

import BackgroundThings.ChessBoard;

import javax.swing.*;

public class BlackPiece extends ImageIcon {
    private final ChessBoard.BackGroundType background;
    public BlackPiece(String path, ChessBoard.BackGroundType background) {
        super(path);
        this.background = background;
    }

    public ChessBoard.BackGroundType getBackground() {
        return background;
    }
}
