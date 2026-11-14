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
}
