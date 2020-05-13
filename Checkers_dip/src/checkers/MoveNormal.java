package checkers;

public class MoveNormal extends Move {
    public MoveNormal(CheckerPosition var1, Coordinate var2) {
        this.checker = var1;
        this.destination = var2;
    }

    public boolean isJump() {
        return false;
    }

    public Move copy() {
        return new MoveNormal(this.checker.copy(), this.destination);
    }

    public Move copy(Board var1) {
        return new MoveNormal(var1.getChecker(this.checker.getPosition()), this.destination);
    }

    public String toString() {
        String var1 = "Move: ";
        if (this.checker.getColor() == 1) {
            var1 = "Black:";
        } else {
            var1 = "White:";
        }

        var1 = var1 + "(" + this.checker.getPosition() + "-" + this.destination + ")";
        return var1;
    }
}

