package checkers;

import java.io.Serializable;

/**
 * Implements the Black's state for the methods findValidMoves and findValidJumps
 */

public class BlackState implements CheckerStates, Serializable {

    // Add available and normal moves to the normalMove list.
    // Returns true if a jump can be used
    public boolean findValidMoves(final CheckerPosition checker, final Board board, MoveList normalMove) {
        if (!findValidJumps(checker, board, normalMove)) {
            if (GameRules.validBlackMove(checker.getPosition(), checker.getPosition().downLeftMove(), board))
                normalMove.add(new OnlyMove(checker, checker.getPosition().downLeftMove()));

            if (GameRules.validBlackMove(checker.getPosition(), checker.getPosition().downRightMove(), board))
                normalMove.add(new OnlyMove(checker, checker.getPosition().downRightMove()));
            return false;
        }
        else
            return true;
    }

    public boolean findValidJumps(final CheckerPosition checker, final Board board, MoveList validJumps) {
        boolean jumpIsFound = false;
        if (GameRules.validBlackJump(checker.getPosition(), checker.getPosition().downLeftJump(), board)) {
            validJumps.add(new MoveJump(checker, checker.getPosition().downLeftJump()));
            jumpIsFound = true;
        }

        if (GameRules.validBlackJump(checker.getPosition(), checker.getPosition().downRightJump(), board)) {
            validJumps.add(new MoveJump(checker, checker.getPosition().downRightJump()));
            jumpIsFound = true;
        }
        return jumpIsFound;
    }
}

