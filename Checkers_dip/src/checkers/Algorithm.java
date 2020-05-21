package checkers;

import static checkers.GameRules.*;

public class Algorithm {
    /**
     * "Minimax with alpha-beta" & Minimax algorithms to find the best move
     * for an opponent and player
     */

    public static Board minimaxAB(Board board, int recursion, int player,
                                  Board alpha, Board beta) {
        if (recursion > 0) {
            MoveList validMoves;
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
                        return beta; //Return the best solution
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
            MoveList validMoves;
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
}
