package controller.sub_controllers.drawable_creators;

import controller.Controller;
import graphics.drawable.LineDrawable;
import graphics.rasterizer.RasterizerContext;
import lib.utils.LineUtil;
import model.Line;
import model.Point;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LineCreatorSubController extends CreatorSubController {
    private Point previewStart, previewEnd;
    private int lineColor;
    private boolean precisionActive = false;

    public LineCreatorSubController(RasterizerContext context, Controller.DrawableSubmitter submitter) {
        super(context, submitter);
    }

    public void changeLineColor(int color){
        this.lineColor = color;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        previewStart = Point.fromMouseEvent(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        Point mousePoint = Point.fromMouseEvent(e);
        if(precisionActive && previewStart!=null) {
            previewEnd = LineUtil.getPointAxisLocked(previewStart, mousePoint);
        }
        else{
            previewEnd = mousePoint;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            precisionActive = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            precisionActive = false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(previewEnd != null && previewStart != null)
            submitDrawable(new LineDrawable(getContext(), new Line(previewStart, previewEnd, lineColor)));
        previewStart = null;
        previewEnd = null;
    }

    @Override
    public void render() {
        if (previewStart != null && previewEnd != null) {
            getContext().getLineRasterizer().rasterize(new Line(previewStart, previewEnd, lineColor));
        }
    }

    @Override
    public void clear() {
        previewStart = null;
        previewEnd = null;
        precisionActive = false;
    }
}
