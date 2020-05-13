package checkersUI;

import java.awt.Point;
import javax.swing.ImageIcon;

public class Pawn {
    Point point;
    int posindex;
    ImageIcon image;
    int type;

    public Pawn(Point var1, ImageIcon var2) {
        this.point = var1;
        this.image = var2;
        this.posindex = 0;
    }

    public Point getP() {
        return this.point;
    }

    public void setP(Point var1) {
        this.point = var1;
    }

    public ImageIcon getImage() {
        return this.image;
    }

    public void setImage(ImageIcon var1) {
        this.image = var1;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int var1) {
        this.type = var1;
    }

    public void movePawn(int var1, int var2) {
    }
}

