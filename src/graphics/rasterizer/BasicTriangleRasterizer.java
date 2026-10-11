package graphics.rasterizer;

import graphics.Raster;
import model.BasicTriangle;
import model.Line;
import model.Point;

import java.util.Arrays;

@Deprecated
public class BasicTriangleRasterizer implements TriangleRasterizer{
    private final Raster<Integer> raster;
    public BasicTriangleRasterizer(Raster<Integer> raster) {
        this.raster = raster;
    }

    @Override
    public void rasterize(BasicTriangle triangle) {
        rasterize(triangle.getPointA(), triangle.getPointB(), triangle.getPointC(), triangle.getColor());
    }

    private void rasterize(Point a, Point b, Point c, int color){
        Line[] lines = new Line[]{
                new Line(a, b, color),
                new Line(a, c, color),
                new Line(b, c, color)
        };

        int minY = Math.min(Math.min(a.getY(), b.getY()), c.getY());
        int maxY = Math.max(Math.max(a.getY(), b.getY()), c.getY());

        var v = getLineXValue(lines[2], 10);

        for (int y = minY; y <= maxY; y++) {
            int  yFinal = y;
            int[] horizontalBorders = Arrays.stream(lines).filter(l->l.containsX((int)getLineXValue(l, yFinal))).mapToInt(l->(int)getLineXValue(l, yFinal)).toArray();

            if(horizontalBorders.length>1){
                int minX = Arrays.stream(horizontalBorders).min().getAsInt();
                int maxX = Arrays.stream(horizontalBorders).max().getAsInt();
                for (int x = minX; x <= maxX; x++) {
                    raster.setPixel(x, y, color);
                }
            }
        }
    }

    private float getLineXValue(Line line, int y){
        if(line.getPointA().getX()==line.getPointB().getX())
            return line.getPointA().getX();

        Point a = line.getPointA();
        Point b = line.getPointB();

        float k = (b.getY()-a.getY())/(float)(b.getX()-a.getX());
        float q = a.getY()-k*a.getX();

        return (y-q)/k;
    }
}
