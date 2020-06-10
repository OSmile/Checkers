package checkers;

public class OnlyMove extends Move {

    public OnlyMove(CheckerPosition checker, Coordinate destination) {
        this.checker = checker;
        this.destination = destination;
    }

    public boolean isJump() {
        return false;
    }

    public Move copy() {
        return new OnlyMove(checker.copy(), destination);
    }

    // Return a copy of this move
    public Move copy(Board board) {
        return new OnlyMove(board.getChecker(checker.getPosition()), destination);
    }

    public String toString() {
        String whichMove = "";
        System.out.println(checker.getColor());
        if (this.checker.getColor() == 1) {
            whichMove = "Black is moving:";
        }
        if (checker.getColor() == 2){
            whichMove = "White is moving:";
        }

        whichMove = whichMove + ("(" + checker.getPosition() + " -> " + destination + ")");
        return whichMove;
    }
}

