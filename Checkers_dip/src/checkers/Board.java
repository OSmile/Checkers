package checkers;

import java.io.Serializable;

public class Board implements Serializable {
    private CheckerPosition[] board = new CheckerPosition[32];
    private MoveList history = new MoveList();
    protected Board next = null;

    public Board() {
    }

    public Board copy() {
        Board var1 = new Board();
        Coordinate var2 = null;
        var1.setHistory(this.history.copy());

        for(int var3 = 1; var3 < 33; ++var3) {
            var2 = new Coordinate(var3);
            if (this.getChecker(var2) != null) {
                var1.setChecker(this.getChecker(var2).copy(), var2);
            }
        }

        return var1;
    }

    public CheckerPosition getChecker(Coordinate var1) {
        return this.board[var1.get() - 1];
    }

    public void setChecker(CheckerPosition var1, Coordinate var2) {
        this.board[var2.get() - 1] = var1;
        var1.setPosition(var2);
    }

    public void removeChecker(CheckerPosition var1) {
        this.board[var1.getPosition().get() - 1] = null;
    }

    public boolean vacantCoordinate(Coordinate var1) {
        return this.getChecker(var1) == null;
    }

    public int evaluate() {
        int var1 = 0;
        Coordinate var2 = null;

        for(int var3 = 1; var3 < 33; ++var3) {
            var2 = new Coordinate(var3);
            if (this.getChecker(var2) != null) {
                var1 += this.getChecker(var2).getValue();
            }
        }

        return var1;
    }

    public void setHistory(MoveList var1) {
        this.history = var1;
    }

    public MoveList getHistory() {
        return this.history;
    }

    public void addMoveToHistory(Move var1) {
        this.history.add(var1);
    }

    public void initialize() {
        this.initializeTop();
        this.initializeMiddle();
        this.initializeBottom();
    }

    private void initializeTop() {
        for(int var1 = 1; var1 < 13; ++var1) {
            this.board[var1 - 1] = new BlackChecker(new Coordinate(var1));
        }

    }

    private void initializeMiddle() {
        for(int var1 = 13; var1 < 21; ++var1) {
            this.board[var1 - 1] = null;
        }

    }

    private void initializeBottom() {
        for(int var1 = 21; var1 < 33; ++var1) {
            this.board[var1 - 1] = new WhiteChecker(new Coordinate(var1));
        }

    }

    public void setNext(Board var1) {
        this.next = var1;
    }

    public Board getNext() {
        return this.next;
    }

    public String toString() {
        String[][] var1 = new String[8][8];
        Coordinate var2 = null;

        for(int var3 = 1; var3 < 33; ++var3) {
            var2 = new Coordinate(var3);
            if (this.getChecker(var2) != null) {
                var1[var2.column()][var2.row()] = this.getChecker(var2).toString();
            }
        }

        String var6 = " +---+---+---+---+---+---+---+---+\n ";

        for(int var4 = 0; var4 < 8; ++var4) {
            for(int var5 = 0; var5 < 8; ++var5) {
                if (var1[var5][var4] != null) {
                    var6 = var6 + "| " + var1[var5][var4] + " ";
                } else {
                    var6 = var6 + "|   ";
                }
            }

            var6 = var6 + "| (" + (var4 * 4 + 1) + "-" + (var4 * 4 + 4) + ")";
            var6 = var6 + "\n +---+---+---+---+---+---+---+---+\n ";
        }

        return var6;
    }
}

