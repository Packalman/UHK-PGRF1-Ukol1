package model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private final List<Line> lines;

    public Polygon(int color, Point... points){
        this(new ArrayList<>());
        if(points.length>1){
            for (int i = 1; i < points.length; i++) {
                lines.add(new Line(points[i-1], points[i], color));
            }
            lines.add(new Line(points[0], points[points.length-1], color));
        }
    }

    public Polygon(int color, List<Point> points){
        this(color, points.toArray(new Point[0]));
    }

    public Polygon(List<Line> lines){
        this.lines = lines;
    }

    public List<Line> getLines(){
        return lines;
    }
}
