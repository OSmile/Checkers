package checkers;

public class BoardList {
    private Board boardList = null;
    private Board last;
    private int listSize = 0;

    public BoardList() {
    }

    public void add(Board var1) {
        if (this.boardList == null) {
            this.boardList = var1;
            this.last = var1;
        } else {
            this.last.setNext(var1);
            this.last = var1;
        }

        ++this.listSize;
    }

    public int size() {
        return this.listSize;
    }

    public Board first() {
        return this.boardList;
    }

    public Board get(int var1) throws IndexOutOfBoundsException {
        int var2 = 0;

        Board var3;
        for(var3 = this.boardList; var2 != var1; ++var2) {
            var3 = var3.getNext();
            if (var3 == null) {
                throw new IndexOutOfBoundsException();
            }
        }

        return var3;
    }

    public BoardIter getIterator() {
        return new BoardIter(this);
    }

    public Board findBestBoard(int var1) {
        BoardIter var2 = this.getIterator();
        Board var3 = var2.next();

        while(var2.hasNext()) {
            if (var1 == 2) {
                var3 = GameSearch.maxBoard(var3, var2.next());
            } else {
                var3 = GameSearch.minBoard(var3, var2.next());
            }
        }

        return var3;
    }

    public String toString() {
        BoardIter var1 = this.getIterator();
        String var2 = "BoardList: ";

        while(var1.hasNext()) {
            var2 = var2 + var1.next().toString();
            if (var1.hasNext()) {
                var2 = var2 + " , ";
            }
        }

        return var2;
    }
}
