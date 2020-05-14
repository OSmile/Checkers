package checkers;

public class WhiteChecker extends CheckerPosition {
    public WhiteChecker(Coordinate pos) {
        this.checkerState = new StateWhite();
        this.position = pos;
        this.value = 2;
        this.posToString = "O";
    }

    public int getColor() {
        return WHITE;
    }

    public void makeKing() {
        this.checkerState = new KingState();
        this.value = 3;
        this.posToString = "W";
    }

    public boolean kingRow() {
        return position.get() >= 1 && position.get() <= 4;
    }

    public boolean isKing() {
        return value == 3;
    }

    public CheckerPosition copy() {
        WhiteChecker newChecker = new WhiteChecker(this.position);
        if (this.value == 3) {
            newChecker.makeKing();
        }
        return newChecker;
    }
}

