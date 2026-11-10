package graphics.drawable;

import graphics.rasterizer.RasterizerContext;
import model.Point;

import java.awt.*;

public class SeedFillReplaceDrawable extends SeedFillDrawable{
    private final int oldColor;

    public SeedFillReplaceDrawable(RasterizerContext context, Point point, int oldColor, int fillColor) {
        super(context, new Color(fillColor), point);
        this.oldColor = oldColor;
    }

    @Override
    public void draw(boolean selectMode) {
        getContext().getSeedFiller().fillReplaceColor(getX(), getY(), oldColor, getFillColorInt());

        super.draw(selectMode);
    }
}
