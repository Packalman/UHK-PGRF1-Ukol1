package controller.sub_controllers.drawable_modifier;

import graphics.drawable.LineDrawable;
import graphics.rasterizer.FilledCircleRasterizer;
import graphics.rasterizer.LineRasterizer;
import model.Line;
import model.Point;

import java.awt.*;
import java.awt.event.MouseEvent;

public class DrawableLineModifier extends DrawableModifier<LineDrawable> {
    private static final int MOUSE_CAN_MOVE_POINT_COLOR = new Color(255, 0, 0).getRGB();
    private static final int MOUSE_CANT_MOVE_POINT_COLOR = new Color(255, 145, 0).getRGB();

    private final FilledCircleRasterizer circleRasterizer;
    private final LineRasterizer lineRasterizer;
    private final Point[] modifiedLinePoints = new Point[2];
    private Line modifiedLine;
    private int modifiedPointIndex = -1;

    private boolean isMouseNear = false;


    public DrawableLineModifier(FilledCircleRasterizer circleRasterizer, LineRasterizer lineRasterizer, DrawableModifiedNotifier notifier) {
        super(notifier);
        this.circleRasterizer = circleRasterizer;
        this.lineRasterizer = lineRasterizer;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        int x = e.getX();
        int y = e.getY();
        if (!modifiedLine.contains(x, y, 10)) {
            notifyDrawableModified(new LineDrawable(lineRasterizer, modifiedLine));
        }
        else{
            updateModifiedPointIndex(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if(modifiedPointIndex!=-1){
            modifiedLinePoints[modifiedPointIndex] = Point.fromMouseEvent(e);
            modifiedLine = new Line(modifiedLinePoints[0], modifiedLinePoints[1], modifiedLine.getColor());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);

        isMouseNear = modifiedLine==null?false:modifiedLine.contains(e.getX(), e.getY(), 10);
    }

    private void updateModifiedPointIndex(int x, int y){
        int x1 = modifiedLinePoints[0].getX();
        int y1 = modifiedLinePoints[0].getY();
        int x2 = modifiedLinePoints[1].getX();
        int y2 = modifiedLinePoints[1].getY();

        int dx1 = x1-x;
        int dy1 = y1-y;
        int dx2 = x2-x;
        int dy2 = y2-y;

        if (dx1*dx1+dy1*dy1 < 100){
            modifiedPointIndex = 0;
        } else if (dx2*dx2+dy2*dy2 < 100) {
            modifiedPointIndex = 1;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        modifiedPointIndex = -1;
    }

    @Override
    public void setModifiedDrawable(LineDrawable drawable, MouseEvent e) {
        super.setModifiedDrawable(drawable, e);

        var p = drawable.getPoints();
        modifiedLinePoints[0] = p[0];
        modifiedLinePoints[1] = p[1];

        modifiedLine = new Line(p[0], p[1], drawable.getColor());

        updateModifiedPointIndex(e.getX(), e.getY());

        isMouseNear = getModifiedDrawableToModify().isPointNear(e.getX(), e.getY());
    }

    @Override
    public void render() {
        lineRasterizer.rasterize(modifiedLine);
        circleRasterizer.rasterize(modifiedLinePoints[0].getX(), modifiedLinePoints[0].getY(), 7, isMouseNear?MOUSE_CAN_MOVE_POINT_COLOR:MOUSE_CANT_MOVE_POINT_COLOR);
        circleRasterizer.rasterize(modifiedLinePoints[1].getX(), modifiedLinePoints[1].getY(), 7, isMouseNear?MOUSE_CAN_MOVE_POINT_COLOR:MOUSE_CANT_MOVE_POINT_COLOR);
    }

    @Override
    public void clear() {

    }

    @Override
    public void disable() {
        if(modifiedLine!=null)
            notifyDrawableModified(new LineDrawable(lineRasterizer, modifiedLine));
    }
}
