package Chesspieces;

import BackgroundThings.ChessBoard;

import javax.swing.*;

/**
 * 棋子图片。携带这枚图片属于哪一方、在哪种底色的格子上，供棋盘贴图与存档使用。
 */
public abstract class PieceImageIcon extends ImageIcon {
    private static final long serialVersionUID = 1L;

    private final ChessBoard.BackGroundType background;
    private final ChessBoard.PieceType pieceType;

    protected PieceImageIcon(ImageIcon image, ChessBoard.BackGroundType background,
                             ChessBoard.PieceType pieceType) {
        super(image.getImage());
        this.background = background;
        this.pieceType = pieceType;
    }

    public ChessBoard.BackGroundType getBackground() {
        return background;
    }

    public ChessBoard.PieceType getPieceType() {
        return pieceType;
    }

    public boolean isWhite() {
        return false;
    }

    public boolean isBlack() {
        return false;
    }
}
