package checkers;

/**
 * Contains the rules of Checkers.
 */

public class GameRules {

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
        Coordinate c;
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
     * contains jumps, since jump is mandatory.
     * A normal move should not be considered if jump exist.
     */
    private static void removeNormalMoves(MoveList movelist) {
        MoveList normalMoves = new MoveList();
        MoveIter iterator = movelist.getIterator();
        Move current;
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

    // Returns a board with all white kings score (remember that white king = 3)
    public static Board allWhiteKingsOnABoard() {
        Board b = new Board();
        for (int i = 1; i < 33; i++) {
            Coordinate c = new Coordinate(i);
            CheckerPosition WhiteKing = new WhiteChecker(c);
            WhiteKing.makeKing();
            b.setChecker(WhiteKing, c);
        }
        return b;
    }

    // Returns a board with all black kings score (remember that black king = -3)
    public static Board allBlackKingsOnABoard() {
        Board b = new Board();
        for (int i = 1; i < 33; i++) {
            Coordinate c = new Coordinate(i);
            CheckerPosition BlackKing = new BlackChecker(c);
            BlackKing.makeKing();
            b.setChecker(BlackKing, c);
        }
        return b;
    }

    public static boolean validCoordinate(Coordinate coordinate) {
        return coordinate.get() >= 1 && coordinate.get() <= 32;
    }

    public static boolean validWhiteMove(Coordinate from, Coordinate to, Board board) {
        return validUpMove(from, to, board) && board.getChecker(from).getColor() == 2;
    }

    public static boolean validBlackMove(Coordinate from, Coordinate to, Board board) {
        return validDownMove(from, to, board) && board.getChecker(from).getColor() == 1;
    }

    public static boolean validKingMove(Coordinate from, Coordinate to, Board board) {
        return validUpMove(from, to, board) || validDownMove(from, to, board);
    }

    public static boolean validWhiteJump(Coordinate from, Coordinate to, Board board) {
        return validUpJump(from, to, 2, board);
    }

    public static boolean validBlackJump(Coordinate from, Coordinate to, Board board) {
        return validDownJump(from, to, 1, board);
    }

    public static boolean validKingJump(Coordinate from, Coordinate to, Board board) {
        int color = board.getChecker(from).getColor();
        return validUpJump(from, to, color, board) || validDownJump(from, to, color, board);
    }

    private static boolean validUpMove(Coordinate from, Coordinate to, Board board) {
        return validCoordinate(from) && validCoordinate(to)
                && board.availableCoordinate(to)
                && !board.availableCoordinate(from)
                && from.row() - to.row() == 1
                && (from.upLeftMove().equals(to) || from.upRightMove().equals(to));
    }

    private static boolean validDownMove(Coordinate from, Coordinate to, Board board) {
        return validCoordinate(from) && validCoordinate(to)
                && board.availableCoordinate(to)
                && !board.availableCoordinate(from)
                && to.row() - from.row() == 1
                && (from.downLeftMove().equals(to) || from.downRightMove().equals(to));
    }

    private static boolean validUpJump(Coordinate from, Coordinate to, int color, Board board) {
        boolean b = validCoordinate(from) && validCoordinate(to)
                && board.availableCoordinate(to)
                && !board.availableCoordinate(from)
                && board.getChecker(from).getColor() == color
                && from.row() - to.row() == 2;
        if (!b) {
            return false;
        } else if (from.upLeftJump().equals(to)) {
            return !board.availableCoordinate(from.upLeftMove()) && board.getChecker(from.upLeftMove()).getColor() == opponent(color);
        } else if (!from.upRightJump().equals(to)) {
            return false;
        } else {
            return !board.availableCoordinate(from.upRightMove()) && board.getChecker(from.upRightMove()).getColor() == opponent(color);
        }
    }

    private static boolean validDownJump(Coordinate from, Coordinate to, int color, Board board) {
        boolean b = validCoordinate(from) && validCoordinate(to)
                && board.availableCoordinate(to)
                && !board.availableCoordinate(from)
                && board.getChecker(from).getColor() == color
                && to.row() - from.row() == 2;
        if (!b) {
            return false;
        } else if (from.downLeftJump().equals(to)) {
            return !board.availableCoordinate(from.downLeftMove()) && board.getChecker(from.downLeftMove()).getColor() == opponent(color);
        } else if (!from.downRightJump().equals(to)) {
            return false;
        } else {
            return !board.availableCoordinate(from.downRightMove()) && board.getChecker(from.downRightMove()).getColor() == opponent(color);
        }
    }
}

