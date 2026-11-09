package graphics.drawable;

import model.Point;

public interface Drawable {
    void draw(boolean selectMode);
    Point[] getPoints();
    boolean isPointNear(int x, int y);
}
