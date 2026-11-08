package graphics.rasterizer;

import graphics.Raster;

public class RowFilledCircleRasterizer implements FilledCircleRasterizer{
    private final Raster<Integer> raster;

    public RowFilledCircleRasterizer(Raster<Integer> raster) {
        this.raster = raster;
    }

    @Override
    public void rasterize(int centerX, int centerY, int radius, int color) {
        int radiusSquared = radius*radius;
        for (int x = 1; x < radius; x++) {
            for (int y = 1; y < radius; y++) {

                if(x*x+y*y <= radiusSquared){
                    raster.setPixel(centerX + x, centerY + y, color);
                    raster.setPixel(centerX - x, centerY + y, color);
                    raster.setPixel(centerX - x, centerY - y, color);
                    raster.setPixel(centerX + x, centerY - y, color);
                }
            }
            raster.setPixel(centerX + x, centerY, color);
            raster.setPixel(centerX - x, centerY, color);
            raster.setPixel(centerX, centerY + x, color);
            raster.setPixel(centerX, centerY - x, color);
        }
        raster.setPixel(centerX, centerY, color);
    }
}
