package checkers;

/**
 * List of boards
 */

public class BoardList {
    private Board boardList = null;
    private Board last;
    private int listSize = 0;

    public BoardList() {
    }

    public void add(Board boardElem) {
        if (this.boardList == null) { // first elem added
            this.boardList = boardElem;
        } else { // adding new board elem to the last place
            this.last.setNext(boardElem);
        }
        this.last = boardElem;

        this.listSize++;
    }

    public int size() {
        return this.listSize;
    }

    public Board get(int index) throws IndexOutOfBoundsException {
        int currentBoard = 0;
        Board board = boardList;

        while (currentBoard != index) {
            board = board.getNext();
            if (board == null) {
                throw new IndexOutOfBoundsException();
            }
            currentBoard++;
        }

        return board;
    }

    public BoardIter getIterator() {
        return new BoardIter(this);
    }

    public Board findBestBoard(int color) {
        BoardIter iterator = getIterator();
        Board bestBoard = iterator.next();

        while (iterator.hasNext()) {
            if (color == CheckerPosition.WHITE)
                bestBoard = GameRules.maxBoard(bestBoard, iterator.next());
            else
                bestBoard = GameRules.minBoard(bestBoard, iterator.next());
        }
        return bestBoard;
    }

    public String toString() {
        BoardIter iterator = getIterator();
        StringBuilder boardList = new StringBuilder("BoardList: ");
        while (iterator.hasNext()) {
            boardList.append(iterator.next().toString());
            if (iterator.hasNext()) {
                boardList.append(" , ");
            }
        }
        return boardList.toString();
    }
}
