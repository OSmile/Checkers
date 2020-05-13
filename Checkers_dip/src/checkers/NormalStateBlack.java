package checkers;

import java.io.Serializable;

public class NormalStateBlack implements CheckerStates, Serializable {
    public NormalStateBlack() {
    }

    public boolean findValidMoves(CheckerPosition var1, Board var2, MoveList var3) {
        if (!this.findValidJumps(var1, var2, var3)) {
            if (GameSearch.validBlackMove(var1.getPosition(), var1.getPosition().downLeftMove(), var2)) {
                var3.add(new MoveNormal(var1, var1.getPosition().downLeftMove()));
            }

            if (GameSearch.validBlackMove(var1.getPosition(), var1.getPosition().downRightMove(), var2)) {
                var3.add(new MoveNormal(var1, var1.getPosition().downRightMove()));
            }

            return false;
        } else {
            return true;
        }
    }

    public boolean findValidJumps(CheckerPosition var1, Board var2, MoveList var3) {
        boolean var4 = false;
        if (GameSearch.validBlackJump(var1.getPosition(), var1.getPosition().downLeftJump(), var2)) {
            var3.add(new MoveJump(var1, var1.getPosition().downLeftJump()));
            var4 = true;
        }

        if (GameSearch.validBlackJump(var1.getPosition(), var1.getPosition().downRightJump(), var2)) {
            var3.add(new MoveJump(var1, var1.getPosition().downRightJump()));
            var4 = true;
        }

        return var4;
    }
}

