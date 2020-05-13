package checkers;

import javax.swing.ImageIcon;

public class BlackChecker extends CheckerPosition {
    public BlackChecker(Coordinate var1) {
        this.checkerState = new NormalStateBlack();
        this.position = var1;
        this.value = -2;
        this.stringRep = "X";
    }

    public int getColor() {
        return 1;
    }

    public void makeKing() {
        this.checkerState = new KingState();
        this.value = -3;
        this.stringRep = "B";
    }

    public boolean isKing() {
        return this.value == -3;
    }

    public boolean kingRow() {
        return this.position.get() >= 29 && this.position.get() <= 32;
    }

    public CheckerPosition copy() {
        BlackChecker var1 = new BlackChecker(this.position);
        if (this.value == -3) {
            var1.makeKing();
        }

        return var1;
    }

    public ImageIcon getIcon() {
        return null;
    }
}

