package controller;

import graphics.rasterizer.LineRasterizer;
import model.Line;
import model.Point;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class TriangleDrawerController extends SubController {
    private final LineRasterizer lineRasterizer;
    private final int PREVIEW_COLOR = Color.RED.getRGB();

    private final Line[] lines = new Line[3];
    private final List<Point> trianglePoints;

    public TriangleDrawerController(LineRasterizer lineRasterizer){
        this.lineRasterizer = lineRasterizer;
        trianglePoints = new ArrayList<>(3);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        trianglePoints.add(Point.fromMouseEvent(e));
        if(trianglePoints.size()==3){
            constructTriangle(trianglePoints.get(0), trianglePoints.get(1), trianglePoints.get(2));

            trianglePoints.clear();
            for (int i = 0; i < 3; i++) {
                lines[i] = null;
            }
        }
        else if(trianglePoints.size()==2){
            lines[2] = new Line(trianglePoints.getFirst(), trianglePoints.getLast(), PREVIEW_COLOR);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        var p = Point.fromMouseEvent(e);

        if(!trianglePoints.isEmpty()){
            lines[0] = new Line(trianglePoints.getFirst(), p, PREVIEW_COLOR);
            if(trianglePoints.size()>1){
                lines[1] = new Line(trianglePoints.getLast(), p, PREVIEW_COLOR);
            }
        }
    }

    @Override
    public void render() {
        for(var l : lines)
        {
            if(l!=null)
                lineRasterizer.rasterize(l);
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < lines.length; i++) {
            lines[i] = null;
        }
    }

    public abstract void constructTriangle(Point a, Point b, Point c);
}
