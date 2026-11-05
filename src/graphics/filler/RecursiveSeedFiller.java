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
    public boolean fillReplaceBackground(int x1, int y1, int targetColor, int fillColor) {
        if(targetColor==fillColor || raster.getPixel(x1,y1)==null || raster.getPixel(x1,y1)!=targetColor)
            return false;

        fillRegion(x1, y1, targetColor, fillColor);

        return true;
    }

    @Override
    public boolean fillUntilBorder(int x1, int y1, int borderColor, int fillColor) {
        return false;
    }


    private void fillRegion(int x, int y, int targetColor, int fillColor){
        Integer color = raster.getPixel(x,y);
        if(color == null || color != targetColor){
            return;
        }

        raster.setPixel(x, y, fillColor);
        fillRegion(x+1, y, targetColor, fillColor);
        fillRegion(x-1, y, targetColor, fillColor);
        fillRegion(x, y+1, targetColor, fillColor);
        fillRegion(x, y-1, targetColor, fillColor);
    }
}
