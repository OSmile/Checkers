package checkers;

public interface CheckerStates {
    boolean findValidMoves(CheckerPosition var1, Board var2, MoveList var3);

    boolean findValidJumps(CheckerPosition var1, Board var2, MoveList var3);
}