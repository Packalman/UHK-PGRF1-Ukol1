package graphics.drawable;

import graphics.rasterizer.RasterizerContext;
import lib.utils.PolygonUtil;
import model.Point;
import model.Polygon;

public class FilledPolygonDrawable implements Drawable{
    private final RasterizerContext context;
    private final Polygon polygon;
    private final int fillColor;

    public FilledPolygonDrawable(RasterizerContext context, Polygon polygon, int fillColor) {
        this.context = context;
        this.polygon = polygon;
        this.fillColor = fillColor;
    }

    @Override
    public void draw(boolean selectMode) {
        context.getFilledPolygonRasterizer().rasterize(polygon, fillColor);
    }

    @Override
    public Point[] getPoints() {
        return polygon.getPoints();
    }

    @Override
    public boolean isPointNear(int x, int y) {
        if(y>=polygon.getMinYBound()&&y<=polygon.getMaxYBound()){
            var ranges = PolygonUtil.getLineIntersections(y, PolygonUtil.getLinesForRasterization(polygon.getLines()));
            for (int i = 0; i < ranges.length-1; i+=2) {
                if(x>=ranges[i]&&x<=ranges[i+1])
                    return true;
            }
        }
        return false;
    }
}
