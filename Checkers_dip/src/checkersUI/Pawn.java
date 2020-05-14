package checkersUI;

import java.awt.Point;
import javax.swing.ImageIcon;

public class Pawn {
    Point point;
    int index;
    ImageIcon image;

    public Pawn(Point point, ImageIcon imageIcon) {
        this.point = point;
        this.image = imageIcon;
        this.index = 0;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}

