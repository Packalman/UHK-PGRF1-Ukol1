package model;


public class Polygon {
    private final Line[] lines;
    private final Point[] points;

    public Polygon(int color, Point... points){
        if(points.length > 2){
            lines = new Line[points.length];
            this.points = points;

            for (int i = 1; i < points.length; i++) {
                lines[i] = new Line(points[i-1], points[i], color);
            }
            lines[0] = new Line(points[0], points[points.length-1], color);
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
}
