package graphics.rasterizer;

import graphics.Raster;
import graphics.VertexTriangleIterator;
import model.VertexTriangle;

import java.awt.image.BufferedImage;

public class TextureTriangleRasterizer {
    private final Raster<Integer> raster;

    public TextureTriangleRasterizer(Raster<Integer> raster) {
        this.raster = raster;
    }

    public void rasterize(VertexTriangle triangle, BufferedImage source){
        VertexTriangleIterator.iterate(triangle, (x, y, a, b, c)->{
            double u = triangle.getVertexA().getProperty(0)*a + triangle.getVertexB().getProperty(0)*b + triangle.getVertexC().getProperty(0)*c;
            double v = triangle.getVertexA().getProperty(1)*a + triangle.getVertexB().getProperty(1)*b + triangle.getVertexC().getProperty(1)*c;

            raster.setPixel(x, y, source.getRGB(
                    (int)Math.round(Math.clamp(u*(source.getWidth()-1), 0, source.getWidth()-1)),
                    (int)Math.round(Math.clamp(v*(source.getHeight()-1), 0, source.getHeight()-1))
            ));
        });
    }
}
