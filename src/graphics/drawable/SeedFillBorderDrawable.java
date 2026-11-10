package graphics.drawable;


import graphics.rasterizer.RasterizerContext;
import model.Point;

import java.awt.*;

public class SeedFillBorderDrawable extends SeedFillDrawable{


    public SeedFillBorderDrawable(RasterizerContext context, Color fillColor, Point point) {
        super(context, fillColor, point);
    }
}
