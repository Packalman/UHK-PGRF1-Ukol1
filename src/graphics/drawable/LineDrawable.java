package graphics.drawable;

import graphics.rasterizer.LineRasterizer;
import model.Line;
import model.Point;

public class LineDrawable implements Drawable {
    private final LineRasterizer lineRasterizer;
    private final Line line;

    public LineDrawable(LineRasterizer lineRasterizer, Line line) {
        this.lineRasterizer = lineRasterizer;
        this.line = line;
    }

    @Override
    public void draw(boolean selectMode) {
        lineRasterizer.rasterize(line);
    }

    @Override
    public Point[] getPoints() {
        return new Point[]{line.getPointA(), line.getPointB()};
    }

    public int getColor(){
        return line.getColor();
    }

    @Override
    public boolean isPointNear(int x, int y) {
        return line.contains(x, y, 10);
    }
}
