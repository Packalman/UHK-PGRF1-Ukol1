package controller.sub_controllers.drawable_modifier;

import graphics.drawable.SeedFillDrawable;
import graphics.drawable.SeedFillReplaceDrawable;
import graphics.rasterizer.RasterizerContext;
import model.Point;

import java.awt.event.MouseEvent;

public class SeedFillModifier extends DrawableModifier<SeedFillDrawable> {
    private int oldColor;
    private Point mousePoint;

    public SeedFillModifier(RasterizerContext context, DrawableModifiedNotifier drawableModifiedNotifier) {
        super(context, drawableModifiedNotifier);
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
        notifyDrawableModified(new SeedFillReplaceDrawable(getContext(), mousePoint, oldColor, getModifiedDrawableToModify().getFillColorInt()));
    }

    @Override
    public void setModifiedDrawable(SeedFillDrawable drawable, MouseEvent e) {
        super.setModifiedDrawable(drawable, e);

        mousePoint = Point.fromMouseEvent(e);
    }

    @Override
    public void render() {
        oldColor = getContext().getRaster().getPixel(mousePoint.getX(), mousePoint.getY());

        getContext().getFilledCircleRasterizer().rasterize(mousePoint.getX(), mousePoint.getY(), 10, getModifiedDrawableToModify().getFillColorInt());
    }

    @Override
    public void clear() {

    }
}
