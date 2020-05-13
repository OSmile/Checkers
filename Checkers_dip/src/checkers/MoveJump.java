package checkers;

public class MoveJump extends Move {
    public MoveJump(CheckerPosition var1, Coordinate var2) {
        this.checker = var1;
        this.destination = var2;
    }

    public boolean isJump() {
        return true;
    }

    public Move copy() {
        return new MoveJump(this.checker.copy(), this.destination);
    }

    public Move copy(Board var1) {
        return new MoveJump(var1.getChecker(this.checker.getPosition()), this.destination);
    }

    public Coordinate capturedCoordinate() {
        if (this.checker.getPosition().row() - this.destination.row() == 2) {
            return this.checker.getPosition().column() - this.destination.column() == 2 ? this.checker.getPosition().upLeftMove() : this.checker.getPosition().upRightMove();
        } else {
            return this.checker.getPosition().column() - this.destination.column() == 2 ? this.checker.getPosition().downLeftMove() : this.checker.getPosition().downRightMove();
        }
    }

    public String toString() {
        String var1 = "";
        if (this.checker.getColor() == 1) {
            var1 = "Black-J:";
        } else {
            var1 = "White-J:";
        }

        var1 = var1 + "(" + this.checker.getPosition() + "-" + this.destination + ")";
        return var1;
    }
}

