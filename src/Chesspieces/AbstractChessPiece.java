package Chesspieces;

import BackgroundThings.ChessBoard;
import Behaviors.AttackBehavior;
import Behaviors.MoveBehavior;
import Behaviors.common.PieceAttackBehavior;
import Behaviors.common.PieceMoveBehavior;
import Players.BlackPlayer;
import Players.WhitePlayer;

import javax.swing.*;

/**
 * 棋子的公共部分：所在格、贴图、走法与吃法行为、选中状态。
 *
 * <p>走法与吃法用策略模式注入：{@link #create} 由具体棋子调用，
 * 一次性装配好与棋子颜色匹配的行为对象，外界只需要使用
 * {@link #move(JButton)} 与 {@link #attack(JButton)}。</p>
 */
public abstract class AbstractChessPiece {
    protected JButton chess_block;//棋子目前所在的格子
    protected ImageIcon chess_piece;//棋子的贴图
    protected MoveBehavior moveBehavior;
    protected AttackBehavior attackBehavior;
    protected ChoiceState choiceState;

    protected AbstractChessPiece(JButton chess_block, ImageIcon chess_piece) {
        this.chess_block = chess_block;
        this.chess_piece = chess_piece;
        this.choiceState = ChoiceState.UN_CHOICE;//初始化为未选中状态
    }

    /**
     * 装配走法与吃法行为。必须放在棋子贴图设置之后，
     * 因为选白方还是黑方的行为取决于贴图。
     */
    protected final void create(MoveBehaviorFactory whiteMove, AttackBehaviorFactory whiteAttack,
                                MoveBehaviorFactory blackMove, AttackBehaviorFactory blackAttack) {
        if (isWhitePiece()) {
            setMoveBehavior(whiteMove.create(this));
            setAttackBehavior(whiteAttack.create(this));
        } else if (isBlackPiece()) {
            setMoveBehavior(blackMove.create(this));
            setAttackBehavior(blackAttack.create(this));
        } else {
            throw new IllegalArgumentException("Invalid piece type: " + chess_piece);
        }
    }

    /** 走法行为工厂，按棋子颜色产出走法。 */
    public interface MoveBehaviorFactory {
        PieceMoveBehavior create(AbstractChessPiece piece);
    }

    /** 吃法行为工厂，按棋子颜色产出吃法。 */
    public interface AttackBehaviorFactory {
        PieceAttackBehavior create(AbstractChessPiece piece);
    }

    public boolean move(JButton target_chess_block) {
        return moveBehavior.move(target_chess_block);
    }

    public boolean attack(JButton target_chess_block) {
        return attackBehavior.attack(target_chess_block);
    }

    public void setAttackBehavior(AttackBehavior attackBehavior) {
        this.attackBehavior = attackBehavior;
    }

    public void setMoveBehavior(MoveBehavior moveBehavior) {
        this.moveBehavior = moveBehavior;
    }

    public void setChess_block(JButton chess_blocks) {
        this.chess_block = chess_blocks;
    }

    public void setChess_piece(ImageIcon chess_piece) {
        this.chess_piece = chess_piece;
    }

    public JButton getChess_block() {
        return this.chess_block;
    }

    public ImageIcon getChess_piece() {
        return chess_piece;
    }

    public ChoiceState getChoiceState() {
        return choiceState;
    }

    public void setChoiceState(ChoiceState choiceState) {
        this.choiceState = choiceState;
    }

    public AttackBehavior getAttackBehavior() {
        return attackBehavior;
    }

    public MoveBehavior getMoveBehavior() {
        return moveBehavior;
    }

    public boolean isWhitePiece() {
        return chess_piece instanceof PieceImageIcon icon && icon.isWhite();
    }

    public boolean isBlackPiece() {
        return chess_piece instanceof PieceImageIcon icon && icon.isBlack();
    }

    /**
     * 棋子所属的棋种，由具体棋子给出，取代原先各处对图标类型的 switch/instanceof 判断。
     */
    public abstract ChessBoard.PieceType getPieceType();

    /** 该棋子是否还留在本方棋子名册中。 */
    public final boolean isOwnPiece() {
        return isWhitePiece() ? WhitePlayer.contains(this) : BlackPlayer.contains(this);
    }

    @Override
    public String toString() {
        String side = isWhitePiece() ? "白" : "黑";
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " (" + side + ")";
    }

    public enum ChoiceState {
        CHOICE_ABLE, UN_CHOICE
    }
}
