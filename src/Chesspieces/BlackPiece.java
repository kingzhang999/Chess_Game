package Chesspieces;

import BackgroundThings.ChessBoard;

import javax.swing.*;

public class BlackPiece extends ImageIcon {
    private final ChessBoard.BackGroundType background;
    private final ChessBoard.PieceType pieceType;
    public BlackPiece(String path, ChessBoard.BackGroundType background, ChessBoard.PieceType pieceType) {
        super(path);
        this.background = background;
        this.pieceType = pieceType;
    }

    public ChessBoard.BackGroundType getBackground() {
        return background;
    }

    public ChessBoard.PieceType getPieceType() {
        return pieceType;
    }
}
