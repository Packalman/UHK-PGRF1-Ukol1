package graphics.filler;

import graphics.Raster;

/// Too much recursion
@Deprecated
public class RecursiveSeedFiller implements SeedFiller{
    private final Raster<Integer> raster;

    public RecursiveSeedFiller(Raster<Integer> raster) {
        this.raster = raster;
    }

    @Override
    public boolean fillReplaceColor(int x1, int y1, int oldColor, int fillColor) {
        if(oldColor == fillColor || raster.getPixel(x1,y1)==null || raster.getPixel(x1,y1) != oldColor)
            return false;

        fillRegion(x1, y1, oldColor, fillColor);

        return true;
    }

    @Override
    public boolean fillUntilBorder(int x1, int y1, int borderColor, int fillColor) {
        return false;
    }


    private void fillRegion(int x, int y, int oldColor, int fillColor){
        Integer color = raster.getPixel(x,y);
        if(color == null || color != oldColor){
            return;
        }

        raster.setPixel(x, y, fillColor);
        fillRegion(x+1, y, oldColor, fillColor);
        fillRegion(x-1, y, oldColor, fillColor);
        fillRegion(x, y+1, oldColor, fillColor);
        fillRegion(x, y-1, oldColor, fillColor);
    }
}
