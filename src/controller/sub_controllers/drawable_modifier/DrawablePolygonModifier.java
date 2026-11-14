package controller.sub_controllers.drawable_modifier;

import graphics.drawable.FilledPolygonDrawable;
import graphics.drawable.PolygonDrawable;
import graphics.drawable.PolygonOutlineDrawable;
import graphics.rasterizer.RasterizerContext;
import model.Point;
import model.Polygon;
import view.Sidebar;
import view.sidebar.PolygonSettings;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawablePolygonModifier extends DrawableModifier<PolygonDrawable> {
    private final List<Point> points = new ArrayList<>();
    private int editedPointIndex = -1;
    private Polygon outlinePolygon;//For click detection

    public DrawablePolygonModifier(RasterizerContext context, DrawableModifiedNotifier drawableModifiedNotifier) {
        super(context, Sidebar.Setting.POLYGON, drawableModifiedNotifier);
    }

    @Override
    public void setModifiedDrawable(PolygonDrawable drawable, MouseEvent e) {
        super.setModifiedDrawable(drawable, e);
        points.clear();
        points.addAll(List.of(drawable.getPoints()));
        updateOutline();

        Point mousePoint = Point.fromMouseEvent(e);

        // is mouse near a point?
        for (int i = 0; i < points.size(); i++) {
            int distSquared = mousePoint.distanceSquaredTo(points.get(i));

            if(distSquared<100){
                editedPointIndex = i;
                return;//i = points.size();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        Point mousePoint = Point.fromMouseEvent(e);

        // is mouse near a point?
        for (int i = 0; i < points.size(); i++) {
            int distSquared = mousePoint.distanceSquaredTo(points.get(i));

            if(distSquared<100){
                editedPointIndex = i;
                return;//i = points.size();
            }
        }

        // is mouse near a line?
        if(Polygon.isPointNearOutline(outlinePolygon, mousePoint, 10)){
            if(e.getClickCount()==2){

            }
            return;
        }

        // mouse is outside, submit
        disable();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            disable();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        editedPointIndex = -1;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if(editedPointIndex!=-1)
        {
            points.set(editedPointIndex, Point.fromMouseEvent(e));
            updateOutline();
        }
    }

    @Override
    public void render() {
        updateOutline();
        var s = (PolygonSettings)getSetting().getSidebarSettings();
        switch (s.getFillType()){
            case COLOR -> getContext().getFilledPolygonRasterizer().rasterize(outlinePolygon, s.getFillColor().getRGB());
            default -> getContext().getPolygonOutlineRasterizer().rasterize(outlinePolygon);
        }


        for (var p : points)
        {
            getContext().getFilledCircleRasterizer().rasterize(p.getX(), p.getY(), 7, Color.RED.getRGB());
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public void disable() {
        super.disable();
        var s = (PolygonSettings) getSetting().getSidebarSettings();
        switch (s.getFillType()) {
            case NONE -> notifyDrawableModified(new PolygonOutlineDrawable(getContext(), outlinePolygon));
            case COLOR ->
                    notifyDrawableModified(new FilledPolygonDrawable(getContext(), outlinePolygon, s.getFillColor().getRGB()));
        }
        notifyDrawableModified();
    }

    private void updateOutline(){
        outlinePolygon = new Polygon(((PolygonSettings)getSetting().getSidebarSettings()).getOutlineColor().getRGB(), points.toArray(Point[]::new));
    }
}
