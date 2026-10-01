package Chesspieces;

import BackgroundThings.ChessBoard;

import javax.swing.ImageIcon;

/**
 * 黑方棋子的贴图。
 */
public class BlackPiece extends PieceImageIcon {
    private static final long serialVersionUID = 1L;

    public BlackPiece(ImageIcon image, ChessBoard.BackGroundType background,
                      ChessBoard.PieceType pieceType) {
        super(image, background, pieceType);
    }

    @Override
    public boolean isWhite() {
        return false;
    }

    @Override
    public boolean isBlack() {
        return true;
    }
}
