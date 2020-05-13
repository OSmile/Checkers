package checkers;

import java.io.Serializable;
import javax.swing.ImageIcon;

public abstract class CheckerPosition extends Position implements Serializable {
    public static final int BLACK = 1;
    public static final int WHITE = 2;
    public static final int WHITE_VALUE_NORMAL = 2;
    public static final int BLACK_VALUE_NORMAL = -2;
    public static final int WHITE_VALUE_KING = 3;
    public static final int BLACK_VALUE_KING = -3;
    protected CheckerStates checkerState;
    protected Coordinate position;
    protected int value;
    protected String stringRep;

    public CheckerPosition() {
    }

    public abstract boolean isKing();

    public abstract int getColor();

    public abstract void makeKing();

    public abstract CheckerPosition copy();

    public abstract boolean kingRow();

    public abstract ImageIcon getIcon();

    public Coordinate getPosition() {
        return this.position;
    }

    public void setPosition(Coordinate var1) {
        this.position = var1;
    }

    public int getValue() {
        return this.value;
    }

    public boolean findValidMoves(MoveList var1, Board var2) {
        return this.checkerState.findValidMoves(this, var2, var1);
    }

    public boolean findValidJumps(MoveList var1, Board var2) {
        return this.checkerState.findValidJumps(this, var2, var1);
    }

    public String toString() {
        return this.stringRep;
    }
}


