package Chesspieces;

import BackgroundThings.ChessBoard;
import Utilities.Resources;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 棋子贴图的唯一来源。
 *
 * <p>棋盘只需要“某种棋子在某种底色格子上长什么样”，
 * 因此这里按“棋子颜色 + 棋种 + 格子底色”生成贴图并缓存复用。
 * 资源文件命名规则与 {@code resource/} 下的实际文件一致：
 * {@code <棋子颜色>_<棋种>_in_<格子底色>.jpg}。</p>
 */
public final class PieceAppearance {

    private static final Map<String, PieceImageIcon> CACHE = new HashMap<>();

    private PieceAppearance() {
    }

    /**
     * @param pieceType  棋种
     * @param whitePiece 是否是白方棋子
     * @param whiteBlock 所在格是否白底
     */
    public static PieceImageIcon imageOf(ChessBoard.PieceType pieceType, boolean whitePiece, boolean whiteBlock) {
        return CACHE.computeIfAbsent(appearanceKey(pieceType, whitePiece, whiteBlock),
                PieceAppearance::load);
    }

    /** 贴图资源路径，与 {@code resource/} 下的实际文件命名一致。 */
    static String imagePath(ChessBoard.PieceType pieceType, boolean whitePiece, boolean whiteBlock) {
        return "/resource/" + appearanceKey(pieceType, whitePiece, whiteBlock) + ".jpg";
    }

    private static String appearanceKey(ChessBoard.PieceType pieceType, boolean whitePiece, boolean whiteBlock) {
        return (whitePiece ? "white" : "black")
                + "_" + pieceType.name().toLowerCase()
                + "_in_" + (whiteBlock ? "white" : "black");
    }

    private static PieceImageIcon load(String key) {
        ChessBoard.BackGroundType background = backgroundOf(key);
        ChessBoard.PieceType pieceType = pieceTypeOf(key);
        //从 classpath 读取，因此从 jar 运行同样可用。
        ImageIcon image = Resources.readIcon("/resource/" + key + ".jpg");
        return key.startsWith("white")
                ? new WhitePiece(image, background, pieceType)
                : new BlackPiece(image, background, pieceType);
    }

    private static ChessBoard.BackGroundType backgroundOf(String key) {
        return key.endsWith("_in_white")
                ? ChessBoard.BackGroundType.WhiteBack
                : ChessBoard.BackGroundType.BlackBack;
    }

    private static ChessBoard.PieceType pieceTypeOf(String key) {
        for (ChessBoard.PieceType pieceType : ChessBoard.PieceType.values()) {
            if (key.contains("_" + pieceType.name().toLowerCase() + "_")) {
                return pieceType;
            }
        }
        throw new IllegalArgumentException("无法识别的贴图名称：" + key);
    }
}
