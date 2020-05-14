package checkers;

/**
 * Iterator for the MoveList
 */
public class MoveIter {
    private MoveList moveList;
    private int current;

    public MoveIter(MoveList moveList) {
        this.moveList = moveList;
        current = 0;
    }

    public boolean hasNext() {
        return current < moveList.size();
    }

    public Move next() {
        return  moveList.get(current++);
    }
}
