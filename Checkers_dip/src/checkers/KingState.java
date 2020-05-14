package checkers;

import java.io.Serializable;
/**
 * Implements the king's state for the methods findValidMoves and findValidJumps
 */

public class KingState implements CheckerStates, Serializable {

    public boolean findValidMoves(final CheckerPosition coordPosition, final Board board,
                                  MoveList validMoves) {
        if (!findValidJumps(coordPosition, board, validMoves)) {

            if (GameRules.validKingMove(coordPosition.getPosition(), coordPosition.getPosition().downLeftMove(),
                    board))
                validMoves.add(new OnlyMove(coordPosition, coordPosition.getPosition().downLeftMove()));

            if (GameRules.validKingMove(coordPosition.getPosition(), coordPosition.getPosition().downRightMove(),
                    board))
                validMoves.add(new OnlyMove(coordPosition, coordPosition.getPosition().downRightMove()));

            if (GameRules.validKingMove(coordPosition.getPosition(), coordPosition.getPosition().upLeftMove(),
                    board))
                validMoves.add(new OnlyMove(coordPosition, coordPosition.getPosition().upLeftMove()));

            if (GameRules.validKingMove(coordPosition.getPosition(), coordPosition.getPosition().upRightMove(),
                    board))
                validMoves.add(new OnlyMove(coordPosition, coordPosition.getPosition().upRightMove()));
            return false;
        }
        else
            return true;
    }

    public boolean findValidJumps(CheckerPosition coordPosition, Board board, MoveList validJumps) {
        boolean isJumpFound = false;
        if (GameRules.validKingJump(coordPosition.getPosition(), coordPosition.getPosition().downLeftJump(), board)) {
            validJumps.add(new MoveJump(coordPosition, coordPosition.getPosition().downLeftJump()));
            isJumpFound = true;
        }

        if (GameRules.validKingJump(coordPosition.getPosition(), coordPosition.getPosition().downRightJump(), board)) {
            validJumps.add(new MoveJump(coordPosition, coordPosition.getPosition().downRightJump()));
            isJumpFound = true;
        }

        if (GameRules.validKingJump(coordPosition.getPosition(), coordPosition.getPosition().upLeftJump(), board)) {
            validJumps.add(new MoveJump(coordPosition, coordPosition.getPosition().upLeftJump()));
            isJumpFound = true;
        }

        if (GameRules.validKingJump(coordPosition.getPosition(), coordPosition.getPosition().upRightJump(), board)) {
            validJumps.add(new MoveJump(coordPosition, coordPosition.getPosition().upRightJump()));
            isJumpFound = true;
        }

        return isJumpFound;
    }
}
