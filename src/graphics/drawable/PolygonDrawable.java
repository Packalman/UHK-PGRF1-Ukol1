package graphics.drawable;

import model.Point;
import model.Polygon;

public abstract class PolygonDrawable implements Drawable{
    private final Polygon polygon;

    public PolygonDrawable(Polygon polygon) {
        this.polygon = polygon;
    }

    @Override
    public Point[] getPoints() {
        return polygon.getPoints();
    }

    protected Polygon getPolygon(){
        return this.polygon;
    }
}
