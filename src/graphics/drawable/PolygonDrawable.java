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
