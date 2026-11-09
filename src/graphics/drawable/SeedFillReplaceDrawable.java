package graphics.drawable;

import graphics.filler.SeedFiller;
import graphics.rasterizer.FilledCircleRasterizer;
import model.Point;

import java.awt.*;

public class SeedFillReplaceDrawable extends SeedFillDrawable{
    private final int oldColor;

    public SeedFillReplaceDrawable(SeedFiller seedFiller, FilledCircleRasterizer circleFiller, Point point, int oldColor, int fillColor) {
        super(seedFiller, circleFiller, new Color(fillColor), point);
        this.oldColor = oldColor;
    }

    @Override
    public void draw(boolean selectMode) {
        getSeedFiller().fillReplaceColor(getX(), getY(), oldColor, getFillColorInt());

        super.draw(selectMode);
    }
}
