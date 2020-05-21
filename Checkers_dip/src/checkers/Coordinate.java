package checkers;

import java.io.Serializable;


public class Coordinate implements Serializable {
    private int coordForChecker;

    public Coordinate(int coordinate) {
        coordForChecker = coordinate;
    }

    public int get() {
        return coordForChecker;
    }

    public boolean equals(Coordinate coord) {
        return coordForChecker == coord.get();
    }


    // Returns the row (0-7) of the coordinate (1-32)
    public int row() {
        int row;
        row = (int) Math.floor(coordForChecker / 4.0);
        if (coordForChecker % 4 == 0) {
            return row - 1;
        } else {
            return row;
        }
    }

    // Returns the column (0-7) of the coordinate (1-32)
    public int column() {
        if (row() % 2 == 0) {
            return (((coordForChecker - (row() * 4)) * 2) - 1);
        } else {
            return (((coordForChecker - (row() * 4)) * 2) - 2);
        }
    }

    public String toString() {
        return "" + coordForChecker;
    }

    // Returns the coordinate of the field that is
    // one field above and one field to the left of previous state
    public Coordinate upLeftMove() {
        return this.row() % 2 == 0 ? new Coordinate(this.coordForChecker - 5) : new Coordinate(this.coordForChecker - 4);
    }

    public Coordinate downLeftMove() {
        if (row() % 2 == 0) {
            return new Coordinate(coordForChecker + 3);
        } else {
            return new Coordinate(coordForChecker + 4);
        }
    }

    public Coordinate upRightMove() {
        if (row() % 2 == 0) {
            return new Coordinate(coordForChecker - 4);
        } else {
            return new Coordinate(coordForChecker - 3);
        }
    }

    public Coordinate downRightMove() {
        if (row() % 2 == 0) {
            return new Coordinate(coordForChecker + 4);
        } else {
            return new Coordinate(coordForChecker + 5);
        }
    }

    public Coordinate upLeftJump() {
        return new Coordinate(coordForChecker - 9);
    }
    public Coordinate downLeftJump() {
        return new Coordinate(coordForChecker + 7);
    }

    public Coordinate upRightJump() {
        return new Coordinate(coordForChecker - 7);
    }
    public Coordinate downRightJump() {
        return new Coordinate(coordForChecker + 9);
    }
}

