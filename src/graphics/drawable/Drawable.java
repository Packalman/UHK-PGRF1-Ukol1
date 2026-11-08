package graphics.drawable;

import model.Point;

public interface Drawable {
    void draw();
    Point[] getPoints();
    boolean isPointNear(int x, int y);
}
