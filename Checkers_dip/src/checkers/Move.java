package checkers;

import java.io.Serializable;

public abstract class Move implements Serializable {
    protected Coordinate destination;
    protected CheckerPosition checker;
    protected Move next = null;

    public abstract boolean isJump();

    public abstract String toString();

    public abstract Move copy(Board board);

    public abstract Move copy();

    public CheckerPosition getChecker() {
        return checker;
    }

    public Coordinate getDestination() {
        return destination;
    }


    // For the MoveList
    public void setNext(Move next) {
        this.next = next;
    }

    // For the MoveList
    public Move getNext() {
        return next;
    }
}

