package controller.sub_controllers.drawable_modifier;

import graphics.Raster;
import graphics.drawable.SeedFillDrawable;
import graphics.drawable.SeedFillReplaceDrawable;
import graphics.filler.SeedFiller;
import graphics.rasterizer.FilledCircleRasterizer;
import model.Point;

import java.awt.event.MouseEvent;

//TODO: seed-fill modifier
public class SeedFillModifier extends DrawableModifier<SeedFillDrawable> {
    private final Raster<Integer> raster;
    private final FilledCircleRasterizer filledCircleRasterizer;
    private final SeedFiller seedFiller;
    private int oldColor;
    private Point mousePoint;

    public SeedFillModifier(Raster<Integer> raster, FilledCircleRasterizer filledCircleRasterizer, SeedFiller seedFiller, DrawableModifiedNotifier drawableModifiedNotifier) {
        super(drawableModifiedNotifier);
        this.raster = raster;
        this.filledCircleRasterizer = filledCircleRasterizer;
        this.seedFiller = seedFiller;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        mousePoint = Point.fromMouseEvent(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        mousePoint = Point.fromMouseEvent(e);
        notifyDrawableModified(new SeedFillReplaceDrawable(seedFiller, filledCircleRasterizer, mousePoint, oldColor, getModifiedDrawableToModify().getFillColorInt()));
    }

    @Override
    public void setModifiedDrawable(SeedFillDrawable drawable, MouseEvent e) {
        super.setModifiedDrawable(drawable, e);

        mousePoint = Point.fromMouseEvent(e);
    }

    @Override
    public void render() {
        oldColor = raster.getPixel(mousePoint.getX(), mousePoint.getY());

        filledCircleRasterizer.rasterize(mousePoint.getX(), mousePoint.getY(), 10, getModifiedDrawableToModify().getFillColorInt());
    }

    @Override
    public void clear() {

    }
}
