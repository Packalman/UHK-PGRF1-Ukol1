package controller.sub_controllers.drawable_creators;

import controller.Controller;
import graphics.drawable.LineDrawable;
import graphics.rasterizer.LineRasterizer;
import lib.transforms.Vec2D;
import model.Line;
import model.Point;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LineCreatorSubController extends CreatorSubController {
    private final LineRasterizer liner;
    private Point previewStart, previewEnd;
    private int lineColor;
    private boolean precisionActive = false;

    private static final double unitDiagonal = Math.sin(Math.PI/4);
    private Vec2D[] axis = new Vec2D[]{
            new Vec2D(1, 0),
            new Vec2D(unitDiagonal, unitDiagonal),
            new Vec2D(0, 1),
            new Vec2D(-unitDiagonal, unitDiagonal),

            new Vec2D(-1, 0),
            new Vec2D(-unitDiagonal, -unitDiagonal),
            new Vec2D(0, -1),
            new Vec2D(unitDiagonal, -unitDiagonal),
    };

    public LineCreatorSubController(Controller.DrawableSubmitter submitter, LineRasterizer liner) {
        super(submitter);
        this.liner = liner;
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
        int x2 = e.getX();
        int y2 = e.getY();
        if(precisionActive && previewStart!=null) {
            int x1 = previewStart.getX();
            int y1 = previewStart.getY();

            int dx = x2 - x1;
            int dy = y2 - y1;
            double length = Math.round(Math.sqrt((dx * dx) + (dy * dy)));

            double unitX = dx / length;
            double unitY = dy / length;

            double angle = Math.atan2(unitY, unitX)+Math.PI;
            angle = (angle + (0.125 + 1) * Math.PI) % (2 * Math.PI);

            int axisIndex = (int) Math.floor(angle / (2 * Math.PI) * 8);

            Vec2D v = axis[axisIndex].mul(length);
            previewEnd = new Point((int) Math.round(v.getX()) + x1, (int) Math.round(v.getY()) + y1);
        }
        else{
            previewEnd = new Point(x2, y2);
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
            submitDrawable(new LineDrawable(liner, new Line(previewStart, previewEnd, lineColor)));
        previewStart = null;
        previewEnd = null;
    }

    @Override
    public void render() {
        if (previewStart != null && previewEnd != null) {
            liner.rasterize(new Line(previewStart, previewEnd, lineColor));
        }
    }

    @Override
    public void clear() {
        previewStart = null;
        previewEnd = null;
        precisionActive = false;
    }
}
