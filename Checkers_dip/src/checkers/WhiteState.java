package checkers;

import java.io.Serializable;

/**
 * Implements the White's state for the methods findValidMoves and findValidJumps
 */

public class WhiteState implements CheckerStates, Serializable {
    // Add available and normal moves to the normalMove list.
    // Returns true if a jump can be used
    public boolean findValidMoves(final CheckerPosition checker, final Board board, MoveList normalMove) {
        if (!findValidJumps(checker, board, normalMove)) {
            if (GameRules.validWhiteMove(checker.getPosition(), checker.getPosition().upLeftMove(), board))
                normalMove.add(new OnlyMove(checker, checker.getPosition().upLeftMove()));

            if (GameRules.validWhiteMove(checker.getPosition(), checker.getPosition().upRightMove(), board))
                normalMove.add(new OnlyMove(checker, checker.getPosition().upRightMove()));
            return false;
        }
        else
            return true;

    }


    public boolean findValidJumps(CheckerPosition checker, Board board, MoveList validJumps) {
        boolean jumpIsFound = false;
        if (GameRules.validWhiteJump(checker.getPosition(), checker.getPosition().upLeftJump(), board)) {
            validJumps.add(new MoveJump(checker, checker.getPosition().upLeftJump()));
            jumpIsFound = true;
        }

        if (GameRules.validWhiteJump(checker.getPosition(), checker.getPosition().upRightJump(), board)) {
            validJumps.add(new MoveJump(checker, checker.getPosition().upRightJump()));
            jumpIsFound = true;
        }
        return jumpIsFound;
    }
}
