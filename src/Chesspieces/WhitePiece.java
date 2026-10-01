package Chesspieces;

import BackgroundThings.ChessBoard;

import javax.swing.ImageIcon;

/**
 * 白方棋子的贴图。
 */
public class WhitePiece extends PieceImageIcon {
    private static final long serialVersionUID = 1L;

    public WhitePiece(ImageIcon image, ChessBoard.BackGroundType background,
                      ChessBoard.PieceType pieceType) {
        super(image, background, pieceType);
    }

    @Override
    public boolean isWhite() {
        return true;
    }

    @Override
    public boolean isBlack() {
        return false;
    }
}
