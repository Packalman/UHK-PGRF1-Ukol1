package controller.sub_controllers.drawable_modifier;

import graphics.drawable.LineDrawable;
import graphics.rasterizer.RasterizerContext;
import lib.utils.LineUtil;
import model.Line;
import model.Point;
import view.Sidebar;
import view.sidebar.LineSettings;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class DrawableLineModifier extends DrawableModifier<LineDrawable> {
    private static final int MOUSE_CAN_MOVE_POINT_COLOR = new Color(255, 0, 0).getRGB();
    private static final int MOUSE_CANT_MOVE_POINT_COLOR = new Color(255, 145, 0).getRGB();

    private final Point[] modifiedLinePoints = new Point[2];
    private Line collisionLine;
    private int modifiedPointIndex = -1;

    private boolean isMouseNear = false;
    private boolean precision = false;


    public DrawableLineModifier(RasterizerContext context, DrawableModifiedNotifier notifier) {
        super(context, Sidebar.Setting.LINE, notifier);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        int x = e.getX();
        int y = e.getY();
        if (!collisionLine.contains(x, y, 10)) {
            disable();
        }
        else{
            updateModifiedPointIndex(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        modifiedPointIndex = -1;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        var mousePoint = Point.fromMouseEvent(e);
        if(modifiedPointIndex!=-1){
            if(precision){
                modifiedLinePoints[modifiedPointIndex] = LineUtil.getPointAxisLocked(modifiedLinePoints[(modifiedPointIndex+1)%2], mousePoint);
            }
            else{
                modifiedLinePoints[modifiedPointIndex] = mousePoint;
            }

            updateCollisionLine();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);

        updateCollisionLine();
        isMouseNear = collisionLine.contains(e.getX(), e.getY(), 10);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            precision = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);

        if(e.getKeyCode()==KeyEvent.VK_SHIFT)
            precision = false;
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

    private Line constructLine(){
        int color = ((LineSettings)getSetting().getSidebarSettings()).getColor().getRGB();
        return new Line(modifiedLinePoints[0], modifiedLinePoints[1], color);
    }

    private void updateCollisionLine(){
        this.collisionLine =  new Line(modifiedLinePoints[0], modifiedLinePoints[1], 0);
    }

    @Override
    public void setModifiedDrawable(LineDrawable drawable, MouseEvent e) {
        super.setModifiedDrawable(drawable, e);

        var p = drawable.getPoints();
        modifiedLinePoints[0] = p[0];
        modifiedLinePoints[1] = p[1];

        updateCollisionLine();

        updateModifiedPointIndex(e.getX(), e.getY());

        isMouseNear = getModifiedDrawableToModify().isPointNear(e.getX(), e.getY());

        // If the user starts modifying with shift,
        // he has to press it again for precision, when dragging - acceptable
        precision = false;
    }

    @Override
    public void render() {
        getContext().getLineRasterizer().rasterize(constructLine());
        getContext().getFilledCircleRasterizer().rasterize(modifiedLinePoints[0].getX(), modifiedLinePoints[0].getY(), 7, isMouseNear?MOUSE_CAN_MOVE_POINT_COLOR:MOUSE_CANT_MOVE_POINT_COLOR);
        getContext().getFilledCircleRasterizer().rasterize(modifiedLinePoints[1].getX(), modifiedLinePoints[1].getY(), 7, isMouseNear?MOUSE_CAN_MOVE_POINT_COLOR:MOUSE_CANT_MOVE_POINT_COLOR);
    }

    @Override
    public void clear() {
        //TODO: disable modifying after 'C'
    }

    @Override
    public void disable() {
        super.disable();
        
        notifyDrawableModified(new LineDrawable(getContext(), constructLine()));
    }
}
