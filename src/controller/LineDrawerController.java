package controller;

import graphics.rasterizer.LineRasterizer;
import model.Line;
import model.Point;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static controller.Controller.LINE_INTERACTION_DISTANCE;
import static controller.Controller.LINE_INTERACTION_DISTANCE_SQUARED;

public class LineDrawerController extends SubController {
    private static final int LINE_COLOR = Color.BLACK.getRGB();
    private static final int PREVIEW_LINE_COLOR = Color.RED.getRGB();

    private final LineRasterizer lineRasterizer;

    private final List<Line> lines = new ArrayList<>();

    private Point previewLineStart = null;
    private Line previewLine = null;
    //First point is the one edited
    private Line editedLine = null;

    public LineDrawerController(LineRasterizer lineRasterizer)
    {
        this.lineRasterizer = lineRasterizer;
    }

    @Override
    public String getName() {
        return "Lines";
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if(e.getButton() == MouseEvent.BUTTON1) {
            previewLineStart = Point.fromMouseEvent(e);
        }
        else if (e.getButton() == MouseEvent.BUTTON3){
            Point p = Point.fromMouseEvent(e);
            for (int i = 0; i < lines.size(); i++) {
                var l = lines.get(i);

                if(l.getPointA().distanceSquaredTo(p) <= LINE_INTERACTION_DISTANCE_SQUARED){
                    editedLine = new Line(Point.fromMouseEvent(e), l.getPointB(), l.getColor());

                    lines.remove(i);
                    i = lines.size();
                }
                else if(l.getPointB().distanceSquaredTo(p) <= LINE_INTERACTION_DISTANCE_SQUARED) {
                    editedLine = new Line(Point.fromMouseEvent(e), l.getPointA(), l.getColor());

                    lines.remove(i);
                    i = lines.size();
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(previewLineStart!=null){
            lines.add(new Line(previewLineStart, Point.fromMouseEvent(e), LINE_COLOR));
            previewLine = null;
            previewLineStart = null;
        }
        else{
            previewLine = null;
        }

        if(editedLine!=null){
            lines.add(editedLine);
            editedLine = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if (previewLineStart!=null){
            previewLine = new Line(previewLineStart, Point.fromMouseEvent(e), PREVIEW_LINE_COLOR);
        }
        if (editedLine!=null){
            editedLine = new Line(Point.fromMouseEvent(e), editedLine.getPointB(), editedLine.getColor());
        }
    }

    Point mousePos;
    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        mousePos = Point.fromMouseEvent(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if(e.getKeyCode() == KeyEvent.VK_DELETE) {
            int length = lines.size();
            for (int i = 0; i < length; i++) {
                var l = lines.get(i);
                if(l.getNormalSize(mousePos)<=LINE_INTERACTION_DISTANCE&&l.containsX(mousePos.getX())&&l.containsY(mousePos.getY())){
                    lines.remove(i);
                    i--;
                    length--;
                }
            }
        }
    }

    @Override
    public void render() {
        if(previewLine!=null)
            lineRasterizer.rasterize(previewLine);
        if(editedLine!=null)
            lineRasterizer.rasterize(editedLine);
        for (var l : lines){
            lineRasterizer.rasterize(l);
        }
    }

    @Override
    public void clear() {
        lines.clear();
        previewLine = null;
    }
}
