package graphics.rasterizer;

import graphics.Raster;
import graphics.TriangleIterator;
import model.BasicTriangle;

public class FilledTriangleRasterizer {
    Raster<Integer> raster;
    public FilledTriangleRasterizer(Raster<Integer> raster){
        this.raster = raster;
    }

    public void raster(BasicTriangle triangle){
        TriangleIterator.iterate(triangle.getPointA(), triangle.getPointB(), triangle.getPointC(), (x, y)->{
            raster.setPixel(x, y, triangle.getColor());
        });
    }
}
