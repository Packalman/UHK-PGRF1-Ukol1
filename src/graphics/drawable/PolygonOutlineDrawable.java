package graphics.drawable;

import graphics.rasterizer.RasterizerContext;
import model.Point;
import model.Polygon;

public class PolygonOutlineDrawable implements Drawable{
    private final RasterizerContext context;
    private final Polygon polygon;

    public PolygonOutlineDrawable(RasterizerContext context, Polygon polygon) {
        this.context = context;
        this.polygon = polygon;
    }

    @Override
    public void draw(boolean selectMode) {
        context.getPolygonOutlineRasterizer().rasterize(polygon);
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
