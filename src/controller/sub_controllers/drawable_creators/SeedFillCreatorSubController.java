package controller.sub_controllers.drawable_creators;

import controller.Controller;
import graphics.Raster;
import graphics.drawable.SeedFillReplaceDrawable;
import graphics.filler.SeedFiller;
import graphics.rasterizer.FilledCircleRasterizer;
import model.Point;

import java.awt.event.MouseEvent;

public class SeedFillCreatorSubController extends CreatorSubController{
    private final Raster<Integer> raster;
    private final SeedFiller seedFiller;
    private final FilledCircleRasterizer filledCircleRasterizer;
    private Point mousePoint;
    private int fillColor;
    private int colorBelowMousePoint;

    public SeedFillCreatorSubController(Raster<Integer> raster, Controller.DrawableSubmitter submitter, SeedFiller seedFiller, FilledCircleRasterizer filledCircleRasterizer) {
        super(submitter);
        this.raster = raster;
        this.seedFiller = seedFiller;
        this.filledCircleRasterizer = filledCircleRasterizer;
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
        submitDrawable(new SeedFillReplaceDrawable(seedFiller, filledCircleRasterizer, mousePoint, colorBelowMousePoint, fillColor));
    }

    @Override
    public void render() {
        if(mousePoint == null)
            return;

        int x = mousePoint.getX();
        int y = mousePoint.getY();

        colorBelowMousePoint = raster.getPixel(x,y);

        filledCircleRasterizer.rasterize(x, y, 7, fillColor);
    }

    @Override
    public void clear() {

    }
}
