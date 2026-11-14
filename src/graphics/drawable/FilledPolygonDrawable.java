package graphics.drawable;

import graphics.rasterizer.RasterizerContext;
import lib.utils.PolygonUtil;
import model.Polygon;

public class FilledPolygonDrawable extends PolygonDrawable{
    private final RasterizerContext context;
    private final int fillColor;
    private final PolygonUtil.ShortenedLine[] shortenedLines;

    public FilledPolygonDrawable(RasterizerContext context, Polygon polygon, int fillColor) {
        super(polygon);
        this.context = context;
        this.fillColor = fillColor;

        this.shortenedLines = PolygonUtil.getShortenedLines(polygon.getLines());
    }

    @Override
    public void draw(boolean selectMode) {
        context.getFilledPolygonRasterizer().rasterize(shortenedLines, getPolygon(), fillColor);
    }

    @Override
    public boolean isPointNear(int x, int y) {
        if(super.isPointNear(x, y))
            return true;

        if(y>=getPolygon().getMinYBound()&&y<=getPolygon().getMaxYBound()){
            var ranges = PolygonUtil.getLineIntersections(y, PolygonUtil.getShortenedLines(getPolygon().getLines()));
            for (int i = 0; i < ranges.length-1; i+=2) {
                if(x>=ranges[i]&&x<=ranges[i+1])
                    return true;
            }
        }
        return false;
    }
}
