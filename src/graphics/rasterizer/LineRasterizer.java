package graphics.rasterizer;

import model.Line;
import model.Point;

public interface LineRasterizer {
    void rasterize(Line line);
    void rasterize(Point a, Point b, int color);
    void rasterize(int x1, int y1, int x2, int y2, int color);
}
