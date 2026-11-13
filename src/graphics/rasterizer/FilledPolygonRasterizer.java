package graphics.rasterizer;

import lib.utils.PolygonUtil;
import model.Polygon;

public interface FilledPolygonRasterizer {
    void rasterize(Polygon polygon, int fillColor);
    void rasterize(PolygonUtil.ShortenedLine[] preparedLines, Polygon polygon, int fillColor);
}
