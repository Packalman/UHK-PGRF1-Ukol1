package controller.sub_controllers.drawable_creators;

import controller.Controller;
import graphics.drawable.SeedFillReplaceDrawable;
import graphics.rasterizer.RasterizerContext;
import model.Point;

import java.awt.event.MouseEvent;

public class SeedFillCreatorSubController extends CreatorSubController{
    private Point mousePoint;
    private int fillColor;
    private int colorBelowMousePoint;

    public SeedFillCreatorSubController(RasterizerContext context, Controller.DrawableSubmitter submitter) {
        super(context, submitter);
    }

    public void setFillColor(int fillColor){
        this.fillColor = fillColor;
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
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        submitDrawable(new SeedFillReplaceDrawable(getContext(), mousePoint, colorBelowMousePoint, fillColor));
    }

    @Override
    public void render() {
        if(mousePoint == null)
            return;

        int x = mousePoint.getX();
        int y = mousePoint.getY();

        colorBelowMousePoint = getContext().getRaster().getPixel(x,y);

        getContext().getFilledCircleRasterizer().rasterize(x, y, 7, fillColor);
    }

    @Override
    public void clear() {

    }
}
