package checkers;

/**
 * States for two methods: findValidMoves and findValidJumps in Checker's class
 * It will be implemented in special classes
 */
public interface CheckerStates {
    boolean findValidMoves(CheckerPosition checker, Board board,
                           MoveList validMoves);

    boolean findValidJumps(CheckerPosition checker, Board board,
                                  MoveList validJumps);
}