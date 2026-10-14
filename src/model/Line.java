package model;

public class Line {
    private final Point pointA;
    private final Point pointB;
    private final int color;

    private final int min_x;
    private final int max_x;

    private final int min_y;
    private final int max_y;

    public Line(Point pointA, Point pointB, int color) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.color = color;

        min_x = Math.min(pointA.getX(), pointB.getX());
        max_x = Math.max(pointA.getX(), pointB.getX());
        min_y = Math.min(pointA.getY(), pointB.getY());
        max_y = Math.max(pointA.getY(), pointB.getY());
    }

    public int getColor() {
        return color;
    }

    public Point getPointA() {
        return pointA;
    }

    public Point getPointB() {
        return pointB;
    }

    public boolean containsX(int x){
        return x >= min_x && x <= max_x;
    }

    public boolean containsY(int y){
        return y >= min_y && y <= max_y;
    }

    //https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
    /// Vrací vzdálenost bodu od přímky
    public double getNormalSize(Point point){
        int x0 = point.getX();
        int y0 = point.getY();

        int x1 = pointA.getX();
        int x2 = pointB.getX();
        int y1 = pointA.getY();
        int y2 = pointB.getY();

        return Math.abs((y2-y1)*x0 - (x2-x1)*y0 + x2*y1 - y2*x1)/
                Math.sqrt(Math.pow(y2-y1,2)+Math.pow(x2-x1,2));
    }
}
