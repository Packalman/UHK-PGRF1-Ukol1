package model;

public class BasicTriangle {
    private final int color;
    private final Point pointA;
    private final Point pointB;
    private final Point pointC;

    public BasicTriangle(Point pointA, Point pointB, Point pointC, int color) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
        this.color = color;
    }

    public Point getPointA() {
        return pointA;
    }

    public Point getPointB() {
        return pointB;
    }

    public Point getPointC() {
        return pointC;
    }

    public int getColor() {
        return color;
    }
}
