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
}
