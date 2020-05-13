package checkers;

public class BoardIter{
    private BoardList boardList;
    private int current;

    public BoardIter(BoardList var1) {
        this.boardList = var1;
        this.current = 0;
    }

    public boolean hasNext() {
        return this.current < this.boardList.size();
    }

    public Board next() {
        return this.boardList.get(this.current++);
    }
}

