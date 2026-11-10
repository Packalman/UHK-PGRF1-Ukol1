package graphics.rasterizer;

import model.Line;
import model.Polygon;

public interface FilledPolygonRasterizer {
    void rasterize(Polygon polygon, int fillColor);
    void rasterize(Line[] preparedLines, Polygon polygon, int fillColor);
}
