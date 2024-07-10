package Chesspieces;

import BackgroundThings.ChessBoard;

import javax.swing.*;

public class WhitePiece extends ImageIcon {
    private final ChessBoard.BackGroundType background;
    private final ChessBoard.PieceType pieceType;
    public WhitePiece(String path, ChessBoard.BackGroundType background, ChessBoard.PieceType pieceType) {
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
