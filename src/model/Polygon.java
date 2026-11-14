package model;


import java.util.Arrays;

public class Polygon {
    private final Line[] lines;
    private final Point[] points;
    private final int maxY;
    private final int minY;

    public Polygon(int color, Point... points){
        if(points.length > 2){
            lines = new Line[points.length];
            this.points = points;

            int minY = Integer.MAX_VALUE;
            int maxY = 0;

            for (int i = 0; i < points.length; i++) {
                lines[i] = new Line(points[i], points[(i+1)%points.length], color);

                int currentY = points[i].getY();
                if(currentY>maxY)
                    maxY = currentY;
                if(currentY<minY)
                    minY = currentY;
            }

            this.minY = minY;
            this.maxY = maxY;
        }
        else{
            throw new RuntimeException("Attempted to create polygon with less than 3 points");
        }
    }

    public Line[] getLines(){
        return lines;
    }

    public Point[] getPoints() {
        return points;
    }

    public int getMinYBound(){
        return this.minY;
    }

    public int getMaxYBound(){
        return this.maxY;
    }

    public static boolean isPointNearOutline(Polygon polygon, Point point, int tolerance){
        return Arrays.stream(polygon.getLines()).anyMatch(l->l.contains(point.getX(), point.getY(), tolerance));
    }
}
