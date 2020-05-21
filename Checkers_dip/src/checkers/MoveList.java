package checkers;

import java.io.Serializable;

/**
 * List of moves
 */

public class MoveList implements Serializable {
    private Move moveList;
    private Move last;
    private int listSize;

    public MoveList() {
        listSize = 0;
        moveList = null;
    }

    public void add(Move move) {
        if (this.moveList == null) { // First elem added
            this.moveList = move;
        } else { // Add to the end of list
            this.last.setNext(move);
        }
        this.last = move;
        listSize++;
    }

    public Move get(int index) throws IndexOutOfBoundsException {
        int current = 0;
        Move move = moveList;
        while (current != index) {
            move = move.getNext();
            if (move == null) throw new IndexOutOfBoundsException();
            current++;
        }
        return move;
    }

    public MoveIter getIterator() {
        return new MoveIter(this);
    }

    public MoveList copy() {
        MoveIter iterator = getIterator();
        MoveList newList = new MoveList();
        while (iterator.hasNext())
            newList.add(iterator.next().copy());
        return newList;
    }

    // Availability to make another move
    // by deleting previous
    public void remove(Move lastMove) {
        if (lastMove == moveList) {  // If first element
            moveList = moveList.getNext();
            listSize--;
        } else {
                MoveIter iterator = getIterator();
                Move previous = iterator.next();    // previous move is first element
                Move current;
                while (iterator.hasNext()) {
                    current = iterator.next();
                    if (lastMove == current) {
                        previous.setNext(current.getNext());
                        listSize--;
                    }
                }
            }
    }


    public int size() {
        return this.listSize;
    }

    public Move first() {
        return this.moveList;
    }

    // Reset board to start new game
    public void reset() {
        this.listSize = 0;
        this.moveList = null;
    }

    public String toString() {
        MoveIter iterator = getIterator();
        StringBuilder allMoves = new StringBuilder("All moves from list: ");
        while (iterator.hasNext()) {
            allMoves.append(iterator.next().toString());
            if (iterator.hasNext()) allMoves.append(", ");
        }
        return allMoves.toString();
    }
}

