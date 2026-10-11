package graphics;

import model.Line;
import model.Point;

import java.awt.*;
import java.util.Arrays;

public class TriangleIterator {
    private static final int DUMMY_COLOR = Color.BLACK.getRGB();

    public static void iterate(Point a, Point b, Point c, TrianglePointDrawer pointDrawer){
        Line[] lines = new Line[]{
                new Line(a, b, DUMMY_COLOR),
                new Line(a, c, DUMMY_COLOR),
                new Line(b, c, DUMMY_COLOR)
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
                    pointDrawer.draw(x, y);
                }
            }
        }
    }

    private static float getLineXValue(Line line, int y){
        if(line.getPointA().getX()==line.getPointB().getX())
            return line.getPointA().getX();

        Point a = line.getPointA();
        Point b = line.getPointB();

        float k = (b.getY()-a.getY())/(float)(b.getX()-a.getX());
        float q = a.getY()-k*a.getX();

        return (y-q)/k;
    }

    public interface TrianglePointDrawer{
        void draw(int x, int y);
    }
}
