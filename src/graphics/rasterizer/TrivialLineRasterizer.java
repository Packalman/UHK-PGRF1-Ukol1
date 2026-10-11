package graphics.rasterizer;

import graphics.Raster;
import model.Line;

public class TrivialLineRasterizer implements LineRasterizer{
    private final Raster<Integer> raster;

    public TrivialLineRasterizer(Raster<Integer> raster) {
        this.raster = raster;
    }

    @Override
    public void rasterize(Line line) {

        rasterize(line.getPointA().getX(), line.getPointB().getX(), line.getPointA().getY(), line.getPointB().getY(), line.getColor());
    }

    private void rasterize(int x1, int x2, int y1, int y2, int color){
        //y=kx+q je rovnice
        if(x1==x2){//Vertikála
            if(y1>y2){
                int tmp = y1;
                y1 = y2;
                y2 = tmp;
            }
            for (int y = y1; y < y2; y++) {
                raster.setPixel(x1, y, color);
            }
        }
        else{
            float k = (y2-y1)/(float)(x2-x1);
            float q = y1-k*x1;

            boolean axisX = Math.abs(x1-x2)>Math.abs(y1-y2);

            if(axisX) {
                if (x1 > x2) {
                    int tmp = x1;
                    x1 = x2;
                    x2 = tmp;
                }

                for (int x = x1; x <= x2; x++) {
                    float y = k * x + q;
                    raster.setPixel(x, Math.round(y), color);
                }
            }
            else{
                if(y1>y2){
                    int tmp = y1;
                    y1 = y2;
                    y2 = tmp;
                }

                for (int y = y1; y <= y2; y++) {
                    float x = (y - q)/k;
                    raster.setPixel(Math.round(x), y, color);
                }
            }
        }
    }
}
