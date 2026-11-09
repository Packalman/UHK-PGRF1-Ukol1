package graphics.drawable;

import graphics.filler.SeedFiller;
import model.Point;

public class SeedFillDrawable implements Drawable{
    private final SeedFiller seedFiller;
    private final Point point;
    private final int replaceColor;
    private final int fillColor;

    public SeedFillDrawable(SeedFiller seedFiller, Point point, int replaceColor, int fillColor) {
        this.seedFiller = seedFiller;
        this.point = point;
        this.replaceColor = replaceColor;
        this.fillColor = fillColor;
    }

    @Override
    public void draw(boolean selectMode) {
        seedFiller.fillReplaceBackground(point.getX(), point.getY(), replaceColor, fillColor);
    }

    @Override
    public Point[] getPoints() {
        return new Point[]{ point };
    }

    @Override
    public boolean isPointNear(int x, int y) {
        int px = point.getX();
        int py = point.getY();

        int dx = x - px;
        int dy = y - py;

        return (dx*dx+dy*dy)<(15*15);
    }
}
