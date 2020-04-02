package Checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static Checkers.Checkers.TILE_SIZE;

public class Piece extends StackPane {

    private PieceType type;

    private double mouseX, mouseY;
    private double lastX, lastY;

    public PieceType getType() {
        return type;
    }

    public double getLastX() {
        return lastX;
    }

    public double getLastY() {
        return lastY;
    }

    public Piece(PieceType type, int x, int y) {
        this.type = type;

        move(x, y);

        Ellipse shadow = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        shadow.setFill(Color.BLACK);
        shadow.setStroke(Color.BLACK);
        shadow.setStrokeWidth(TILE_SIZE * 0.03);
        shadow.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        shadow.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);


        Ellipse checker = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        checker.setFill(type == PieceType.BLACK ? Color.valueOf("2c2c2c") : Color.valueOf("#ffffff"));

        checker.setStroke(Color.BLACK);
        checker.setStrokeWidth(TILE_SIZE * 0.03);

        checker.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        checker.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(shadow, checker);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + lastX, e.getSceneY() - mouseY + lastY);
        });
    }

    public void move(int x, int y) {
        lastX = x * TILE_SIZE;
        lastY = y * TILE_SIZE;
        relocate(lastX, lastY);
    }

    public void abortMove() {
        relocate(lastX, lastY);
    }
}





