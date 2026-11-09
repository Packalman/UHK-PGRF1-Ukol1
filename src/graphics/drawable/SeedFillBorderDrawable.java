package graphics.drawable;

import graphics.filler.SeedFiller;
import graphics.rasterizer.FilledCircleRasterizer;
import model.Point;

import java.awt.*;

public class SeedFillBorderDrawable extends SeedFillDrawable{

    public SeedFillBorderDrawable(SeedFiller seedFiller, FilledCircleRasterizer circleFiller, Color fillColor, Point point) {
        super(seedFiller, circleFiller, fillColor, point);
    }


}
