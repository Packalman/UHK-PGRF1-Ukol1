package graphics.drawable;

import graphics.rasterizer.RasterizerContext;
import lib.utils.PolygonUtil;
import model.Polygon;

public class FilledPolygonDrawable extends PolygonDrawable{
    private final RasterizerContext context;
    private final int fillColor;

    public FilledPolygonDrawable(RasterizerContext context, Polygon polygon, int fillColor) {
        super(polygon);
        this.context = context;
        this.fillColor = fillColor;
    }

    @Override
    public void draw(boolean selectMode) {
        context.getFilledPolygonRasterizer().rasterize(getPolygon(), fillColor);
    }

    @Override
    public boolean isPointNear(int x, int y) {
        if(y>=getPolygon().getMinYBound()&&y<=getPolygon().getMaxYBound()){
            var ranges = PolygonUtil.getLineIntersections(y, PolygonUtil.getLinesForRasterization(getPolygon().getLines()));
            for (int i = 0; i < ranges.length-1; i+=2) {
                if(x>=ranges[i]&&x<=ranges[i+1])
                    return true;
            }
        }
        return false;
    }
}
