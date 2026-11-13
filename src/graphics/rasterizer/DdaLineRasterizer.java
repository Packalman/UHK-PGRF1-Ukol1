package graphics.rasterizer;

import graphics.Raster;
import model.Line;
import model.Point;

public class DdaLineRasterizer implements LineRasterizer{
    private final Raster<Integer> raster;

    public DdaLineRasterizer(Raster<Integer> raster) {
        this.raster = raster;
    }

    @Override
    public void rasterize(Line line) {
        rasterize(line.getPointA(), line.getPointB(), line.getColor());
    }

    @Override
    public void rasterize(Point a, Point b, int color) {
        rasterize(a.getX(), a.getY(), b.getX(), b.getY(), color);
    }

    @Override
    public void rasterize(int x1, int y1, int x2, int y2, int color) {
        int dx = x2-x1;
        int dy = y2-y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        double stepX = dx/(double)steps;
        double stepY = dy/(double)steps;

        double x = x1;
        double y = y1;
        for (int i = 0; i < steps; i++) {
            raster.setPixel((int)Math.round(x), (int)Math.round(y), color);
            x += stepX;
            y += stepY;
        }
    }
}
