package checkers;

public class MoveIter {
    private MoveList moveList;
    private int current;

    public MoveIter(MoveList var1) {
        this.moveList = var1;
        this.current = 0;
    }

    public boolean hasNext() {
        return this.current < this.moveList.size();
    }

    public Move next() {
        return this.moveList.get(this.current++);
    }
}
