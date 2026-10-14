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

import static controller.Controller.LINE_INTERACTION_DISTANCE_SQUARED;

public class PolygonDrawerController extends SubController {
    private final int POLYGON_COLOR = Color.BLACK.getRGB();
    private final int PREVIEW_LINE_COLOR = Color.RED.getRGB();

    private final PolygonOutlineRasterizer polygonRasterizer;
    private final LineRasterizer lineRasterizer;
    private final List<Polygon> completedPolygons;
    private final List<Point> polygonPoints;
    private final Line[] lines = new Line[3];
    private Polygon currentPolygon = null;
    private EditedPolygon editedPolygon = null;

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

            if (polygonPoints.size() == 3){
                lines[2] = null;
            }

            if (polygonPoints.size() > 2) {
                currentPolygon = new Polygon(POLYGON_COLOR, polygonPoints.toArray(new Point[0]));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        if(e.getButton() == MouseEvent.BUTTON3){
            if(polygonPoints.isEmpty()){
                for (int i = 0; i < completedPolygons.size(); i++) {
                    var polygon = completedPolygons.get(i);
                    for (int j = 0; j < polygon.getPoints().length; j++) {
                        var p = polygon.getPoints()[j];
                        if(p.distanceSquaredTo(Point.fromMouseEvent(e))<LINE_INTERACTION_DISTANCE_SQUARED){
                            editedPolygon = EditedPolygon.fromPolygon(j, polygon.getPoints(), polygon.getLines()[0].getColor());

                            completedPolygons.remove(i);

                            i = completedPolygons.size();
                            j = polygon.getPoints().length;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if(editedPolygon!=null){
            editedPolygon.movedPoint = Point.fromMouseEvent(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(editedPolygon!=null){
            completedPolygons.add(editedPolygon.getPolygon());
            editedPolygon = null;
        }
    }
    Point mousePos;
    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        mousePos = Point.fromMouseEvent(e);
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if(currentPolygon!=null){
                completedPolygons.add(currentPolygon);
                currentPolygon = null;
            }

            polygonPoints.clear();
            lines[0] = null;
            lines[1] = null;
            lines[2] = null;
        }
        if (e.getKeyCode() == KeyEvent.VK_DELETE){
            for (int i = 0; i < completedPolygons.size(); i++) {
                var polygon = completedPolygons.get(i);
                for (int j = 0; j < polygon.getPoints().length; j++) {
                    var p = polygon.getPoints()[j];
                    if(p.distanceSquaredTo(mousePos)<LINE_INTERACTION_DISTANCE_SQUARED){
                        completedPolygons.remove(i);

                        if(polygon.getPoints().length>3){
                            var result = new Point[polygon.getPoints().length-1];
                            for (int k = 0; k < result.length; k++) {
                                int index = k;
                                if(k >= j){
                                    index += 1;
                                }
                                result[k] = polygon.getPoints()[index];
                            }
                            completedPolygons.add(new Polygon(polygon.getLines()[0].getColor(), result));
                        }



                        i = completedPolygons.size();
                        j = polygon.getPoints().length;
                    }
                }
            }
        }
    }

    @Override
    public void render() {
        if(editedPolygon!=null){
            polygonRasterizer.rasterize(editedPolygon.getPolygon());
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
        editedPolygon = null;
        polygonPoints.clear();
        completedPolygons.clear();
    }

    private static class EditedPolygon{
        public Point movedPoint;
        public final Point[] otherPoints;
        public final int color;
        private final int editedPointIndex;

        private EditedPolygon(int editedPointIndex, Point movedPoint, Point[] otherPoints, int color) {
            this.movedPoint = movedPoint;
            this.otherPoints = otherPoints;
            this.color = color;
            this.editedPointIndex = editedPointIndex;
        }

        public static EditedPolygon fromPolygon(int editedPoint, Point[] polygonPoints, int color){
            var points = new Point[polygonPoints.length-1];

            for (int i = 0; i < points.length; i++) {
                int externalIndex = i;
                if(i>=editedPoint){
                    externalIndex += 1;
                }

                points[i] = polygonPoints[externalIndex];
            }

            return new EditedPolygon(editedPoint, polygonPoints[editedPoint], points, color);
        }

        public Polygon getPolygon(){
            var points = new Point[otherPoints.length+1];
            for (int i = 0; i < points.length; i++) {
                if(i<editedPointIndex){
                    points[i] = otherPoints[i];
                }
                else if(i==editedPointIndex){
                    points[i] = movedPoint;
                }
                else{
                    points[i] = otherPoints[i-1];
                }
            }
            return new Polygon(color, points);
        }
    }
}
