package checkers;

/**
 * Iterator for BoardList
 */

public class BoardIter{
    private BoardList boardList;
    private int current;

    public BoardIter(BoardList bl) {
        this.boardList = bl;
        this.current = 0;
    }

    public boolean hasNext() {
        return this.current < this.boardList.size();
    }

    public Board next() {
        return this.boardList.get(this.current++);
    }
}

