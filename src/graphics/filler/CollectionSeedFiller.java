package graphics.filler;

import graphics.Raster;
import model.Point;

import java.util.ArrayDeque;
import java.util.Queue;

public class CollectionSeedFiller implements SeedFiller{
    private final Raster<Integer> raster;
    public CollectionSeedFiller(Raster<Integer> raster) {
        this.raster = raster;
    }

    @Override
    public boolean fillReplaceBackground(int x1, int y1, int targetColor, int fillColor) {
        if(raster.getPixel(x1, y1) == null || targetColor == fillColor || raster.getPixel(x1, y1) != targetColor)
            return false;

        Queue<Point> points = new ArrayDeque<>();
        points.add(new Point(x1, y1));

        while(!points.isEmpty()){
            var point = points.poll();
            int x = point.getX();
            int y = point.getY();
            var pixel = raster.getPixel(x, y);

            if (pixel!=null && pixel==targetColor)
            {
                raster.setPixel(x, y, fillColor);
                points.add(new Point(x+1, y));
                points.add(new Point(x-1, y));
                points.add(new Point(x, y+1));
                points.add(new Point(x, y-1));
            }
        }

        return true;
    }

    @Override
    public boolean fillUntilBorder(int x1, int y1, int borderColor, int fillColor) {
        if(raster.getPixel(x1, y1) == null || fillColor == borderColor || raster.getPixel(x1, y1) == fillColor)
            return false;

        Queue<Point> points = new ArrayDeque<>();
        points.add(new Point(x1, y1));

        while(!points.isEmpty()){
            var point = points.poll();
            int x = point.getX();
            int y = point.getY();
            var pixel = raster.getPixel(x, y);

            if (pixel!=null && pixel!=borderColor)
            {
                raster.setPixel(x, y, fillColor);
                points.add(new Point(x+1, y));
                points.add(new Point(x-1, y));
                points.add(new Point(x, y+1));
                points.add(new Point(x, y-1));
            }
        }

        return true;
    }
}
