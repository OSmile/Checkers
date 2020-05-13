package checkers;

import java.io.Serializable;

public class KingState implements CheckerStates, Serializable {
    public KingState() {
    }

    public boolean findValidMoves(CheckerPosition var1, Board var2, MoveList var3) {
        if (!this.findValidJumps(var1, var2, var3)) {
            if (GameSearch.validKingMove(var1.getPosition(), var1.getPosition().downLeftMove(), var2)) {
                var3.add(new MoveNormal(var1, var1.getPosition().downLeftMove()));
            }

            if (GameSearch.validKingMove(var1.getPosition(), var1.getPosition().downRightMove(), var2)) {
                var3.add(new MoveNormal(var1, var1.getPosition().downRightMove()));
            }

            if (GameSearch.validKingMove(var1.getPosition(), var1.getPosition().upLeftMove(), var2)) {
                var3.add(new MoveNormal(var1, var1.getPosition().upLeftMove()));
            }

            if (GameSearch.validKingMove(var1.getPosition(), var1.getPosition().upRightMove(), var2)) {
                var3.add(new MoveNormal(var1, var1.getPosition().upRightMove()));
            }

            return false;
        } else {
            return true;
        }
    }

    public boolean findValidJumps(CheckerPosition var1, Board var2, MoveList var3) {
        boolean var4 = false;
        if (GameSearch.validKingJump(var1.getPosition(), var1.getPosition().downLeftJump(), var2)) {
            var3.add(new MoveJump(var1, var1.getPosition().downLeftJump()));
            var4 = true;
        }

        if (GameSearch.validKingJump(var1.getPosition(), var1.getPosition().downRightJump(), var2)) {
            var3.add(new MoveJump(var1, var1.getPosition().downRightJump()));
            var4 = true;
        }

        if (GameSearch.validKingJump(var1.getPosition(), var1.getPosition().upLeftJump(), var2)) {
            var3.add(new MoveJump(var1, var1.getPosition().upLeftJump()));
            var4 = true;
        }

        if (GameSearch.validKingJump(var1.getPosition(), var1.getPosition().upRightJump(), var2)) {
            var3.add(new MoveJump(var1, var1.getPosition().upRightJump()));
            var4 = true;
        }

        return var4;
    }
}
