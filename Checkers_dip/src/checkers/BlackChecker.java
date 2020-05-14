package checkers;

public class BlackChecker extends CheckerPosition {
    public BlackChecker(Coordinate coordinate) {
        this.checkerState = new BlackState();
        this.position = coordinate;
        this.value = -2;
        this.posToString = "X";
    }

    public int getColor() {
        return BLACK;
    }

    public void makeKing() {
        this.checkerState = new KingState();
        this.value = -3;
        this.posToString = "B";
    }

    public boolean isKing() {
        return this.value == -3;
    }

    public boolean kingRow() {
        return this.position.get() >= 29 && this.position.get() <= 32;
    }

    public CheckerPosition copy() {
        BlackChecker newBlackChecker = new BlackChecker(this.position);
        if (this.value == -3) {
            newBlackChecker.makeKing();
        }

        return newBlackChecker;
    }
}

