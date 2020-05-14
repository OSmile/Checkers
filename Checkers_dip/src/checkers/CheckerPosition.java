package checkers;

import java.io.Serializable;

public abstract class CheckerPosition extends Position implements Serializable {
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    protected CheckerStates checkerState;
    protected Coordinate position;
    protected int value;
    protected String posToString;

    public CheckerPosition() {
    }

    public abstract boolean isKing();

    public abstract int getColor();

    public abstract void makeKing();

    public abstract CheckerPosition copy();

    public abstract boolean kingRow();


    public Coordinate getPosition() {
        return this.position;
    }

    public void setPosition(Coordinate var1) {
        this.position = var1;
    }

    public int getValue() {
        return this.value;
    }

    public boolean findValidMoves(MoveList moveList, final Board board) {
        return checkerState.findValidMoves(this, board, moveList);
    }

    public boolean findValidJumps(MoveList moveList, final Board board) {
        return checkerState.findValidJumps(this, board, moveList);
    }

    public String toString() {
        return posToString;
    }
}


