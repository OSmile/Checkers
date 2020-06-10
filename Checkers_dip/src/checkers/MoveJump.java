package checkers;

public class MoveJump extends Move {

    public MoveJump(CheckerPosition checker, Coordinate destination) {
        this.checker = checker;
        this.destination = destination;
    }

    public boolean isJump() {
        return true;
    }

    public Move copy() {
        return new MoveJump(checker.copy(), destination);
    }

    // Return a copy of this move from the board
    public Move copy(Board board) {
        return new MoveJump(board.getChecker(checker.getPosition()), destination);
    }

    // Return the coordinate of the captured checker
    public Coordinate capturedCoordinate() {
        if (checker.getPosition().row() - destination.row() == 2) { // Up
            if (checker.getPosition().column() - destination.column() == 2) // Up, left
                return checker.getPosition().upLeftMove();
            else
                return checker.getPosition().upRightMove();
        }
        else { // Down
            if (checker.getPosition().column() - destination.column() == 2) // Down, left
                return checker.getPosition().downLeftMove();
            else
                return checker.getPosition().downRightMove();
        }
    }

    public String toString() {
        String logging;

        if (checker.getColor() == 1) {
            logging = "Black is Jump:";
        } else {
            logging = "White is Jump:";
        }

        logging = logging + "(" + checker.getPosition() + " -> " + destination + ")";
        return logging;
    }
}

