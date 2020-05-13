package checkers;

/**
 * Contains the rules of Checkers.
 * It also has methods for making minimax algorithm.
 */

public class GameSearch {

    public static Board executeMove(final Move oldMove, final Board oldBoard) {
        if (oldMove.isJump()) {
            return executeJump(oldMove, oldBoard);
        } else {
            Board newBoard = oldBoard.copy();
            Move newMove = oldMove.copy(newBoard);
            moveChecker(newMove, newBoard);
            newBoard.addMoveToHistory(oldMove);
            return newBoard;
        }
    }

    private static Board executeJump(final Move oldMove, final Board oldBoard) {
        Board newBoard = oldBoard.copy();
        Move newMove = oldMove.copy(newBoard);
        removeCapturedChecker((MoveJump) newMove, newBoard);
        moveChecker(newMove, newBoard);
        newBoard.addMoveToHistory(oldMove);

        // Check for multiple jumps.
        MoveList movelist = new MoveList();
        newMove.getChecker().findValidJumps(movelist, newBoard);
        if (movelist.size() == 0) {
            return newBoard;   // No more jumps.
        } else if (movelist.size() == 1) {
            return executeJump(movelist.first(), newBoard);
        } else {
            MoveIter iterator = movelist.getIterator();
            BoardList boardlist = new BoardList();
            while (iterator.hasNext()) {
                boardlist.add(executeJump(iterator.next(), newBoard));
            }
            return boardlist.findBestBoard(oldMove.getChecker().getColor());
        }
    }

    /** Returns a new board where oldMove has been executed.
    * Since it is the user that makes the move,
    * the jump should not continue with multiple
    * jumps as is done in executeJump.
     */
    public static Board executeUserJump(final Move oldMove, final Board oldBoard) {
        Board newBoard = oldBoard.copy();
        Move newMove = oldMove.copy(newBoard);
        removeCapturedChecker((MoveJump) newMove, newBoard);
        moveChecker(newMove, newBoard);
        return newBoard;
    }

    private static void moveChecker(Move move, Board board) {
        board.removeChecker(move.getChecker());
        board.setChecker(move.getChecker(), move.getDestination());
        if ((move.getChecker().kingRow()) && (!move.getChecker().isKing())) {
            move.getChecker().makeKing();
        }
    }

    private static void removeCapturedChecker(MoveJump move, Board board) {
        board.removeChecker(board.getChecker(move.capturedCoordinate()));
    }

    /**
     * Returns the list of all valid moves for player "color" on "board". If a
     * jump is possible, only jumps will be returned, since jumps are mandatory.
     */
    public static MoveList findAllValidMoves(final Board board, int color) {
        boolean jumpExist = false;
        MoveList movelist = new MoveList();
        Coordinate c = null;
        for (int i = 1; i < 33; i++) {
            c = new Coordinate(i);
            if ((board.getChecker(c) != null)
                    && (board.getChecker(c).getColor() == color)) {
                if (jumpExist) {
                    board.getChecker(c).findValidJumps(movelist, board);
                } else if (board.getChecker(c).findValidMoves(movelist, board)) {
                    jumpExist = true;
                }
            }
        }
        // Remove normal moves, in case a normal move has been added to the list
        // before the first jump.
        if (jumpExist) {
            removeNormalMoves(movelist);
        }
        return movelist;
    }

    /**
     * Returns true if there is a jump among the valid moves. It is used to test
     * that the user does not make a normal move, when she can make a jump.
     */
    public static boolean existJump(final Board board, int color) {
        MoveList movelist = findAllValidMoves(board, color);
        MoveIter iterator = movelist.getIterator();
        while (iterator.hasNext()) {
            if (iterator.next().isJump()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes normal moves from movelist. This is necessary if the list
     * contains jumps, since a jump is mandatory.
     * A normal move should therefore
     * not be considered if a jump exist.
     */
    private static void removeNormalMoves(MoveList movelist) {
        MoveList normalMoves = new MoveList();
        MoveIter iterator = movelist.getIterator();
        Move current = null;
        while (iterator.hasNext()) {
            current = iterator.next();
            if (!current.isJump()) {
                normalMoves.add(current);
            }
        }
        iterator = normalMoves.getIterator();
        while (iterator.hasNext()) {
            movelist.remove(iterator.next());
        }
    }

    /**
     * Uses the "Minimax with alpha-beta" algorithm to find the best move
     * for a player.
     * However, unlike the normal minimax algorithm, which
     * searches an already built game tree.
     */

    public static Board minimaxAB(Board board, int recursion, int player,
                                  Board alpha, Board beta) {
        if (recursion > 0) {
            MoveList validMoves = null;
            validMoves = findAllValidMoves(board, player);
            if (validMoves.size() == 0) {
                return board;
            }
            MoveIter iterator = validMoves.getIterator();
            if (player == CheckerPosition.BLACK) {   // Black - min node.
                while (iterator.hasNext()) {
                    Board nextBoard = minimaxAB(executeMove(iterator.next(), board),
                            recursion - 1, opponent(player),
                            alpha, beta);
                    beta = minBoard(beta, nextBoard);
                    if (alpha.evaluate() >= beta.evaluate()) {
                        return alpha;
                    }
                }
                return beta;
            } else {      // White - max node.
                while (iterator.hasNext()) {
                    Board nextBoard = minimaxAB(executeMove(iterator.next(), board),
                            recursion - 1, opponent(player),
                            alpha, beta);
                    alpha = maxBoard(alpha, nextBoard);
                    if (alpha.evaluate() >= beta.evaluate()) {
                        return beta; //Cutoff. Return the best solution so far.
                    }
                }
                return alpha;
            }
        } else {
            return board;   // Recursion done -> leaf in game tree.
        }
    }

    public static Board minimax(Board board, int recursion, int player) {
        if (recursion > 0) {
            MoveList validMoves = null;
            validMoves = findAllValidMoves(board, player);
            if (validMoves.size() == 0) {
                return board;
            }
            MoveIter iterator = validMoves.getIterator();
            BoardList boardlist = new BoardList();
            if (player == CheckerPosition.BLACK) {   // Black - min node.
                while (iterator.hasNext()) {
                    boardlist.add(minimax(executeMove(iterator.next(), board),
                            recursion - 1, opponent(player)));
                }
                return boardlist.findBestBoard(CheckerPosition.BLACK);
            } else {      // White - max node.
                while (iterator.hasNext()) {
                    boardlist.add(minimax(executeMove(iterator.next(), board),
                            recursion - 1, opponent(player)));
                }
                return boardlist.findBestBoard(CheckerPosition.WHITE);
            }
        } else {
            return board;   // Recursion done -> leaf in game tree.
        }
    }

    public static int opponent(int player) {
        if (player == CheckerPosition.WHITE) {
            return CheckerPosition.BLACK;
        } else {
            return CheckerPosition.WHITE;
        }
    }

    // Returns the board with the smallest score.
    public static Board minBoard(Board a, Board b) {
        if (a.evaluate() <= b.evaluate()) {
            return a;
        } else {
            return b;
        }
    }

    // Returns the board with the largest score.
    public static Board maxBoard(Board a, Board b) {
        if (a.evaluate() >= b.evaluate()) {
            return a;
        } else {
            return b;
        }
    }

    // Returns a board that represents a +infinity score when evaluated.
    public static Board plusInfinityBoard() {
        Board b = new Board();
        for (int i = 1; i < 33; i++) {
            Coordinate c = new Coordinate(i);
            CheckerPosition WhiteKing = new WhiteChecker(c);
            WhiteKing.makeKing();
            b.setChecker(WhiteKing, c);
        }
        return b;
    }

    // Returns a board that represents a -infinity score when evaluated.
    public static Board minusInfinityBoard() {
        Board b = new Board();
        for (int i = 1; i < 33; i++) {
            Coordinate c = new Coordinate(i);
            CheckerPosition BlackKing = new BlackChecker(c);
            BlackKing.makeKing();
            b.setChecker(BlackKing, c);
        }
        return b;
    }

    public static boolean validCoordinate(Coordinate var0) {
        return var0.get() >= 1 && var0.get() <= 32;
    }

    public static boolean validWhiteMove(Coordinate var0, Coordinate var1, Board var2) {
        return validUpMove(var0, var1, var2) && var2.getChecker(var0).getColor() == 2;
    }

    public static boolean validBlackMove(Coordinate var0, Coordinate var1, Board var2) {
        return validDownMove(var0, var1, var2) && var2.getChecker(var0).getColor() == 1;
    }

    public static boolean validKingMove(Coordinate var0, Coordinate var1, Board var2) {
        return validUpMove(var0, var1, var2) || validDownMove(var0, var1, var2);
    }

    public static boolean validWhiteJump(Coordinate var0, Coordinate var1, Board var2) {
        return validUpJump(var0, var1, 2, var2);
    }

    public static boolean validBlackJump(Coordinate var0, Coordinate var1, Board var2) {
        return validDownJump(var0, var1, 1, var2);
    }

    public static boolean validKingJump(Coordinate var0, Coordinate var1, Board var2) {
        int var3 = var2.getChecker(var0).getColor();
        return validUpJump(var0, var1, var3, var2) || validDownJump(var0, var1, var3, var2);
    }

    private static boolean validUpMove(Coordinate var0, Coordinate var1, Board var2) {
        return validCoordinate(var0) && validCoordinate(var1)
                && var2.vacantCoordinate(var1)
                && !var2.vacantCoordinate(var0)
                && var0.row() - var1.row() == 1
                && (var0.upLeftMove().equals(var1) || var0.upRightMove().equals(var1));
    }

    private static boolean validDownMove(Coordinate var0, Coordinate var1, Board var2) {
        return validCoordinate(var0) && validCoordinate(var1)
                && var2.vacantCoordinate(var1)
                && !var2.vacantCoordinate(var0)
                && var1.row() - var0.row() == 1
                && (var0.downLeftMove().equals(var1) || var0.downRightMove().equals(var1));
    }

    private static boolean validUpJump(Coordinate var0, Coordinate var1, int var2, Board var3) {
        boolean var4 = validCoordinate(var0) && validCoordinate(var1)
                && var3.vacantCoordinate(var1)
                && !var3.vacantCoordinate(var0)
                && var3.getChecker(var0).getColor() == var2
                && var0.row() - var1.row() == 2;
        if (!var4) {
            return false;
        } else if (var0.upLeftJump().equals(var1)) {
            return !var3.vacantCoordinate(var0.upLeftMove()) && var3.getChecker(var0.upLeftMove()).getColor() == opponent(var2);
        } else if (!var0.upRightJump().equals(var1)) {
            return false;
        } else {
            return !var3.vacantCoordinate(var0.upRightMove()) && var3.getChecker(var0.upRightMove()).getColor() == opponent(var2);
        }
    }

    private static boolean validDownJump(Coordinate var0, Coordinate var1, int var2, Board var3) {
        boolean var4 = validCoordinate(var0) && validCoordinate(var1)
                && var3.vacantCoordinate(var1)
                && !var3.vacantCoordinate(var0)
                && var3.getChecker(var0).getColor() == var2
                && var1.row() - var0.row() == 2;
        if (!var4) {
            return false;
        } else if (var0.downLeftJump().equals(var1)) {
            return !var3.vacantCoordinate(var0.downLeftMove()) && var3.getChecker(var0.downLeftMove()).getColor() == opponent(var2);
        } else if (!var0.downRightJump().equals(var1)) {
            return false;
        } else {
            return !var3.vacantCoordinate(var0.downRightMove()) && var3.getChecker(var0.downRightMove()).getColor() == opponent(var2);
        }
    }
}

