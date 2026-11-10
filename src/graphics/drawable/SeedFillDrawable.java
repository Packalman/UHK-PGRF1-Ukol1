package graphics.drawable;

import graphics.rasterizer.RasterizerContext;
import model.Point;

import java.awt.*;


public abstract class SeedFillDrawable implements Drawable {
    private final RasterizerContext context;
    private final int fillColorInt;
    private final int outlineColorInt;
    private final Point point;

    public SeedFillDrawable(RasterizerContext context, Color fillColor, Point point){
        this.context = context;
        this.fillColorInt = fillColor.getRGB();
        this.point = point;

        this.outlineColorInt = new Color(
                fillColor.getRed()+128%256,
                fillColor.getBlue()+128%256,
                fillColor.getBlue()+128%256)
                .getRGB();
    }

    protected RasterizerContext getContext(){
        return this.context;
    }

    /// needs to be called after seed fill in implementation class is done
    @Override
    public void draw(boolean selectMode) {
        if(selectMode){
            int x = point.getX();
            int y = point.getY();
            context.getFilledCircleRasterizer().rasterize(x, y, 7, outlineColorInt);
            context.getFilledCircleRasterizer().rasterize(x, y, 6, getFillColorInt());
        }
    }

    public int getFillColorInt(){
        return this.fillColorInt;
    }

    public int getX(){
        return point.getX();
    }

    public int getY(){
        return point.getY();
    }

    @Override
    public Point[] getPoints() {
        return new Point[]{point};
    }

    @Override
    public boolean isPointNear(int x, int y) {
        int px = point.getX();
        int py = point.getY();

        int dx = x - px;
        int dy = y - py;

        return (dx*dx+dy*dy)<(100);
    }
}
