package checkers;

import javax.swing.ImageIcon;

public class WhiteChecker extends CheckerPosition {
    public WhiteChecker(Coordinate var1) {
        this.checkerState = new NormalStateWhite();
        this.position = var1;
        this.value = 2;
        this.stringRep = "O";
    }

    public int getColor() {
        return 2;
    }

    public ImageIcon getIcon() {
        return null;
    }

    public void makeKing() {
        this.checkerState = new KingState();
        this.value = 3;
        this.stringRep = "W";
    }

    public boolean kingRow() {
        return this.position.get() >= 1 && this.position.get() <= 4;
    }

    public boolean isKing() {
        return this.value == 3;
    }

    public CheckerPosition copy() {
        WhiteChecker var1 = new WhiteChecker(this.position);
        if (this.value == 3) {
            var1.makeKing();
        }

        return var1;
    }
}

