package checkers;

import java.io.Serializable;

public class NormalStateWhite implements CheckerStates, Serializable {
    public NormalStateWhite() {
    }

    public boolean findValidMoves(CheckerPosition var1, Board var2, MoveList var3) {
        if (!this.findValidJumps(var1, var2, var3)) {
            if (GameSearch.validWhiteMove(var1.getPosition(), var1.getPosition().upLeftMove(), var2)) {
                var3.add(new MoveNormal(var1, var1.getPosition().upLeftMove()));
            }

            if (GameSearch.validWhiteMove(var1.getPosition(), var1.getPosition().upRightMove(), var2)) {
                var3.add(new MoveNormal(var1, var1.getPosition().upRightMove()));
            }

            return false;
        } else {
            return true;
        }
    }

    public boolean findValidJumps(CheckerPosition var1, Board var2, MoveList var3) {
        boolean var4 = false;
        if (GameSearch.validWhiteJump(var1.getPosition(), var1.getPosition().upLeftJump(), var2)) {
            var3.add(new MoveJump(var1, var1.getPosition().upLeftJump()));
            var4 = true;
        }

        if (GameSearch.validWhiteJump(var1.getPosition(), var1.getPosition().upRightJump(), var2)) {
            var3.add(new MoveJump(var1, var1.getPosition().upRightJump()));
            var4 = true;
        }

        return var4;
    }
}


