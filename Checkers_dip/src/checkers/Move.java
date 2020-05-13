package checkers;

import java.io.Serializable;

public abstract class Move implements Serializable {
    protected Coordinate destination;
    protected CheckerPosition checker;
    protected Move next = null;

    public Move() {
    }

    public abstract boolean isJump();

    public abstract String toString();

    public abstract Move copy(Board var1);

    public abstract Move copy();

    public CheckerPosition getChecker() {
        return this.checker;
    }

    public Coordinate getDestination() {
        return this.destination;
    }

    public void setNext(Move var1) {
        this.next = var1;
    }

    public Move getNext() {
        return this.next;
    }
}

