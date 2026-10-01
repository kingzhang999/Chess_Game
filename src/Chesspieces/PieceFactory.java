package Chesspieces;

import BackgroundThings.ChessBoard;
import Players.BlackPlayer;
import Players.WhitePlayer;

import javax.swing.*;

/**
 * 棋子的创建入口：按棋种和归属方新建棋子，并登记到全局名册与棋盘外观上。
 */
public final class PieceFactory {

    private PieceFactory() {
    }

    /** 按棋种、归属方和所在格新建棋子，并把棋子贴图贴到该格上。 */
    public static AbstractChessPiece create(ChessBoard.PieceType pieceType, boolean whitePiece,
                                           JButton block, boolean whiteBlock) {
        ImageIcon pieceImage = PieceAppearance.imageOf(pieceType, whitePiece, whiteBlock);
        AbstractChessPiece pieces = newPiece(pieceType, block, pieceImage);

        //棋盘统一登记，便于查找与胜负判定。
        ChessBoard.addChessPiece(pieces);
        if (whitePiece) {
            WhitePlayer.add_W_Piece(pieces);
        } else {
            BlackPlayer.add_B_Piece(pieces);
        }
        return pieces;
    }

    /** 按存档数字码新建棋子，码值见 {@link ChessBoard.PieceType#archiveCode(boolean)}。 */
    public static AbstractChessPiece create(int archiveCode, JButton block, boolean whiteBlock) {
        ChessBoard.PieceType pieceType = ChessBoard.PieceType.fromArchiveCode(archiveCode);
        return create(pieceType, ChessBoard.PieceType.isWhiteCode(archiveCode), block, whiteBlock);
    }

    private static AbstractChessPiece newPiece(ChessBoard.PieceType pieceType, JButton block,
                                               ImageIcon pieceImage) {
        return switch (pieceType) {
            case Soldier -> new Soldier(block, pieceImage);
            case Car -> new Car(block, pieceImage);
            case Horse -> new Horse(block, pieceImage);
            case Elephant -> new Elephant(block, pieceImage);
            case Queen -> new Queen(block, pieceImage);
            case King -> new King(block, pieceImage);
        };
    }
}
