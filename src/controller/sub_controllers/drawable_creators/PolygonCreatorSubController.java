package controller.sub_controllers.drawable_creators;

import controller.Controller;
import graphics.drawable.PolygonOutlineDrawable;
import graphics.rasterizer.LineRasterizer;
import graphics.rasterizer.PolygonOutlineRasterizer;
import model.Point;
import model.Polygon;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PolygonCreatorSubController extends CreatorSubController {
    private final LineRasterizer lineRasterizer;
    private final PolygonOutlineRasterizer polygonOutlineRasterizer;
    private final List<Point> points;
    private Point mousePoint;
    private int outlineColor;

    public PolygonCreatorSubController(Controller.DrawableSubmitter submitter, LineRasterizer lineRasterizer, PolygonOutlineRasterizer polygonOutlineRasterizer) {
        super(submitter);
        this.lineRasterizer = lineRasterizer;
        this.polygonOutlineRasterizer = polygonOutlineRasterizer;
        points = new ArrayList<>();
    }

    public void setOutlineColor(int outlineColor){
        this.outlineColor = outlineColor;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        points.add(Point.fromMouseEvent(e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        mousePoint = Point.fromMouseEvent(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        mousePoint = Point.fromMouseEvent(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);

        if (e.getKeyCode() == KeyEvent.VK_ENTER && points.size()>2){
            submitDrawable(new PolygonOutlineDrawable(polygonOutlineRasterizer, new Polygon(outlineColor, points.toArray(Point[]::new))));
            clear();
        }
    }

    @Override
    public void render() {
        if (points.size()>1){
            for (int i = 0; i < points.size()-1; i++) {
                lineRasterizer.rasterize(points.get(i), points.get(i+1), outlineColor);
            }
            lineRasterizer.rasterize(points.getLast(), mousePoint, outlineColor);
            lineRasterizer.rasterize(mousePoint, points.getFirst(), outlineColor);
        }
        else if (points.size()==1){
            lineRasterizer.rasterize(points.getFirst(), mousePoint, outlineColor);
        }
    }

    @Override
    public void clear() {
        points.clear();
    }

    @Override
    public void disable() {
        if(points.size()>2) {
            submitDrawable(new PolygonOutlineDrawable(polygonOutlineRasterizer, new Polygon(outlineColor, points.toArray(Point[]::new))));
            clear();
        }
    }
}
