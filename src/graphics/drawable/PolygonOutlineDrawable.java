package graphics.drawable;

import graphics.rasterizer.RasterizerContext;
import model.Polygon;

public class PolygonOutlineDrawable extends PolygonDrawable{
    private final RasterizerContext context;

    public PolygonOutlineDrawable(RasterizerContext context, Polygon polygon) {
        super(polygon);
        this.context = context;
    }

    @Override
    public void draw(boolean selectMode) {
        context.getPolygonOutlineRasterizer().rasterize(getPolygon());
    }

    @Override
    public boolean isPointNear(int x, int y) {
        var lines = getPolygon().getLines();
        for (int i = 0; i < lines.length; i++) {
            if(lines[i].contains(x, y, 15))
                return true;
        }
        return false;
    }
}
