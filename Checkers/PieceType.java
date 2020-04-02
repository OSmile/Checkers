package Checkers;

public enum PieceType {
    BLACK(1), WHITE(-1);

    final int moveDir;

    PieceType(int moveDirection) {
        this.moveDir = moveDirection;
    }
}

