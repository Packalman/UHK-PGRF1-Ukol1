package graphics.drawable;

import graphics.rasterizer.RasterizerContext;
import model.Line;
import model.Point;

public class LineDrawable implements Drawable {
    private final RasterizerContext context;
    private final Line line;

    public LineDrawable(RasterizerContext context, Line line) {
        this.context = context;
        this.line = line;
    }

    @Override
    public void draw(boolean selectMode) {
        this.context.getLineRasterizer().rasterize(line);
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
