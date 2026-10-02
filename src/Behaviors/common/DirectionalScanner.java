package Behaviors.common;

import BackgroundThings.ChessBoard;

import javax.swing.*;
import java.util.List;

/**
 * 沿固定方向滑行的棋子的公共扫描逻辑：车与后的直线、象与后的斜线。
 *
 * <p>只提供“扫描”这一件事，不涉及棋子自身状态，因此走法子类与吃法子类都能复用。</p>
 */
public abstract class DirectionalScanner {

    protected DirectionalScanner() {
    }

    /**
     * 沿一个方向一直前进，把途中空格加入结果，遇到棋子就停止（不把该格加入）。
     * 用于走法扫描。
     */
    protected final void scanEmptySquares(List<JButton> blocks, int startRow, int startCol,
                                          int rowStep, int colStep) {
        int row = startRow + rowStep;
        int col = startCol + colStep;
        while (canReach(row, col)) {
            if (ChessBoard.hasPiece(blockAt(row, col))) {
                break;
            }
            blocks.add(blockAt(row, col));
            row += rowStep;
            col += colStep;
        }
    }

    /**
     * 沿一个方向一直前进，遇到第一个棋子时把该格加入结果（供吃子筛选），然后停止。
     */
    protected final void scanUntilPiece(List<JButton> blocks, int startRow, int startCol,
                                        int rowStep, int colStep) {
        int row = startRow + rowStep;
        int col = startCol + colStep;
        while (canReach(row, col)) {
            if (ChessBoard.hasPiece(blockAt(row, col))) {
                blocks.add(blockAt(row, col));
                break;
            }
            row += rowStep;
            col += colStep;
        }
    }

    protected static boolean canReach(int row, int col) {
        return row >= 0 && row < ChessBoard.ROWS && col >= 0 && col < ChessBoard.COLS;
    }

    protected static JButton blockAt(int row, int col) {
        return ChessBoard.getChessBoardElement(row, col);
    }

    protected static boolean isEmpty(int row, int col) {
        return !ChessBoard.hasPiece(blockAt(row, col));
    }
}
