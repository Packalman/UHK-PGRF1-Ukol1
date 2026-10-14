package controller;


import graphics.rasterizer.LineRasterizer;
import graphics.rasterizer.PolygonOutlineRasterizer;
import model.Line;
import model.Point;
import model.Polygon;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PolygonDrawerController extends SubController {
    private final int POLYGON_COLOR = Color.BLACK.getRGB();
    private final int PREVIEW_LINE_COLOR = Color.RED.getRGB();

    private final PolygonOutlineRasterizer polygonRasterizer;
    private final LineRasterizer lineRasterizer;
    private final List<Polygon> completedPolygons;
    private final List<Point> polygonPoints;
    private final Line[] lines = new Line[3];
    private Polygon currentPolygon;

    public PolygonDrawerController(PolygonOutlineRasterizer polygonRasterizer, LineRasterizer lineRasterizer){
        this.polygonRasterizer = polygonRasterizer;
        this.lineRasterizer = lineRasterizer;
        completedPolygons = new ArrayList<>();
        polygonPoints = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "Polygons";
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if(e.getButton() == MouseEvent.BUTTON1) {
            polygonPoints.add(Point.fromMouseEvent(e));

            lines[0] = null;
            lines[1] = null;

            if (polygonPoints.size() == 2) {
                lines[2] = new Line(polygonPoints.getFirst(), polygonPoints.getLast(), POLYGON_COLOR);
            }

            if (polygonPoints.size() > 2) {
                currentPolygon = new Polygon(POLYGON_COLOR, polygonPoints);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        if(!polygonPoints.isEmpty()){
            lines[0] = new Line(polygonPoints.getFirst(), Point.fromMouseEvent(e), PREVIEW_LINE_COLOR);
            if(polygonPoints.size()>1){
                lines[1] = new Line(polygonPoints.getLast(), Point.fromMouseEvent(e), PREVIEW_LINE_COLOR);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            completedPolygons.add(currentPolygon);
            currentPolygon = null;
            polygonPoints.clear();
            lines[0] = null;
            lines[1] = null;
        }
    }

    @Override
    public void render() {
        if(polygonPoints.size() == 2){

        }
        if(currentPolygon!=null){
            polygonRasterizer.rasterize(currentPolygon);
        }
        for (var p : completedPolygons){
            polygonRasterizer.rasterize(p);
        }
        for (var l : lines) {
            if(l!=null){
                lineRasterizer.rasterize(l);
            }
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < lines.length; i++) {
            lines[i] = null;
        }

        currentPolygon = null;
        polygonPoints.clear();
        completedPolygons.clear();
    }
}
