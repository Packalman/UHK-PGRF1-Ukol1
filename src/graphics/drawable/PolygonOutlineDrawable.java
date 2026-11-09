package graphics.drawable;

import graphics.rasterizer.PolygonOutlineRasterizer;
import model.Point;
import model.Polygon;

public class PolygonOutlineDrawable implements Drawable{
    private final PolygonOutlineRasterizer polygonOutlineRasterizer;
    private final Polygon polygon;

    public PolygonOutlineDrawable(PolygonOutlineRasterizer polygonOutlineRasterizer, Polygon polygon) {
        this.polygonOutlineRasterizer = polygonOutlineRasterizer;
        this.polygon = polygon;
    }

    @Override
    public void draw() {
        polygonOutlineRasterizer.rasterize(polygon);
    }

    @Override
    public Point[] getPoints() {
        return polygon.getPoints();
    }

    @Override
    public boolean isPointNear(int x, int y) {
        var lines = polygon.getLines();
        for (int i = 0; i < lines.length; i++) {
            if(lines[i].contains(x, y, 15))
                return true;
        }
        return false;
    }
}
