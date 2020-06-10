package checkers;

import java.io.Serializable;

public class Board implements Serializable {

    private CheckerPosition[] board = new CheckerPosition[32];
    private MoveList allMoves = new MoveList(); // All moves here
    protected Board next = null;

    public Board() {
    }

    public CheckerPosition getChecker(Coordinate c) {
        return board[c.get() - 1];
    }

    public void setChecker(CheckerPosition setChecker, Coordinate coordinate) {
        this.board[coordinate.get() - 1] = setChecker;
        setChecker.setPosition(coordinate);
    }

    public Board copy() {
        Board newBoard = new Board();
        Coordinate tempCoord;
        newBoard.setHistory(this.allMoves.copy());

        for(int i = 1; i < 33; i++) {
            tempCoord = new Coordinate(i);
            if (this.getChecker(tempCoord) != null) {
                newBoard.setChecker(this.getChecker(tempCoord).copy(), tempCoord);
            }
        }

        return newBoard;
    }

    public void removeChecker(CheckerPosition checker) {
        board[checker.getPosition().get() - 1] = null;
    }

    public boolean availableCoordinate(Coordinate c) {
        return (getChecker(c) == null);
    }

    public int evaluate() {
        int score = 0;
        Coordinate c;
        for (int i = 1; i < 33; i++) {
            c = new Coordinate(i);
            if (getChecker(c) != null)
                score = score + getChecker(c).getValue();
        }
        return score;
    }

    public void setHistory(MoveList history) {
        this.allMoves = history;
    }


    public MoveList getHistory() {
        return allMoves;
    }


    public void addMoveToHistory(Move move) {
        allMoves.add(move);
    }


    public void initialize() {
        initializeTop();
        initializeMiddle();
        initializeBottom();
    }


    private void initializeTop() {
        for (int i = 1; i < 13; i++)
            board[i - 1] = new BlackChecker(new Coordinate(i));
    }


    private void initializeMiddle() {
        for (int i = 13; i < 21; i++)
            board[i - 1] = null;
    }


    private void initializeBottom() {
        for (int i = 21; i < 33; i++)
            board[i - 1] = new WhiteChecker(new Coordinate(i));
    }


    // For the BoardList class.
    public void setNext(Board next) {
        this.next = next;
    }


    // For the BoardList class.
    public Board getNext() {
        return next;
    }



}