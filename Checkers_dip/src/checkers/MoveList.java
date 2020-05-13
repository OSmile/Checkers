package checkers;

import java.io.Serializable;

public class MoveList implements Serializable {
    private Move moveList = null;
    private Move last;
    private int listSize = 0;

    public MoveList() {
    }

    public void add(Move var1) {
        if (this.moveList == null) {
            this.moveList = var1;
            this.last = var1;
        } else {
            this.last.setNext(var1);
            this.last = var1;
        }

        ++this.listSize;
    }

    public void remove(Move var1) {
        if (var1 == this.moveList) {
            this.moveList = this.moveList.getNext();
            --this.listSize;
        } else {
            MoveIter var2 = this.getIterator();
            Move var3 = var2.next();
            Move var4 = null;

            while(var2.hasNext()) {
                var4 = var2.next();
                if (var1 == var4) {
                    var3.setNext(var4.getNext());
                    --this.listSize;
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

    public void reset() {
        this.listSize = 0;
        this.moveList = null;
    }

    public Move get(int var1) throws IndexOutOfBoundsException {
        int var2 = 0;

        Move var3;
        for(var3 = this.moveList; var2 != var1; ++var2) {
            var3 = var3.getNext();
            if (var3 == null) {
                throw new IndexOutOfBoundsException();
            }
        }

        return var3;
    }

    public MoveIter getIterator() {
        return new MoveIter(this);
    }

    public MoveList copy() {
        MoveIter var1 = this.getIterator();
        MoveList var2 = new MoveList();

        while(var1.hasNext()) {
            var2.add(var1.next().copy());
        }

        return var2;
    }

    public String toString() {
        MoveIter var1 = this.getIterator();
        String var2 = "Movelist: ";

        while(var1.hasNext()) {
            var2 = var2 + var1.next().toString();
            if (var1.hasNext()) {
                var2 = var2 + " , ";
            }
        }

        return var2;
    }
}

