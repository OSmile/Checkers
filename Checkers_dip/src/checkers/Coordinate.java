package checkers;

import java.io.Serializable;


public class Coordinate implements Serializable {
    private int c;

    public Coordinate(int var1) {
        this.c = var1;
    }

    Coordinate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int get() {
        return this.c;
    }

    public boolean equals(Coordinate var1) {
        return this.c == var1.get();
    }

    public int row() {
        int row;
        row = (int) Math.floor(c / 4.0);
        if (c % 4 == 0) {
            return row - 1;
        } else {
            return row;
        }
    }

    public int column() {
        if (row() % 2 == 0) {
            return (((c - (row() * 4)) * 2) - 1);
        } else {
            return (((c - (row() * 4)) * 2) - 2);
        }
    }

    public String toString() {
        return "" + c;
    }

    public Coordinate upLeftMove() {
        return this.row() % 2 == 0 ? new Coordinate(this.c - 5) : new Coordinate(this.c - 4);
    }

    public Coordinate upRightMove() {
        //System.out.println("Checking if it is Up Right Move");

        if (row() % 2 == 0) {
            return new Coordinate(c - 4);
        } else {
            return new Coordinate(c - 3);
        }
    }

    public Coordinate downLeftMove() {
        if (row() % 2 == 0) {
            return new Coordinate(c + 3);
        } else {
            return new Coordinate(c + 4);
        }
    }

    public Coordinate downRightMove() {
        if (row() % 2 == 0) {
            return new Coordinate(c + 4);
        } else {
            return new Coordinate(c + 5);
        }
    }

    public Coordinate upLeftJump() {
        return new Coordinate(this.c - 9);
    }

    public Coordinate upRightJump() {
        return new Coordinate(this.c - 7);
    }

    public Coordinate downLeftJump() {
        return new Coordinate(this.c + 7);
    }

    public Coordinate downRightJump() {
        return new Coordinate(this.c + 9);
    }
}

