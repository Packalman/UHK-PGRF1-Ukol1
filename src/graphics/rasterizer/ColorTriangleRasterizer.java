package graphics.rasterizer;

import graphics.Raster;
import graphics.VertexTriangleIterator;
import model.Vertex;
import model.VertexTriangle;

import java.awt.*;
import java.util.Arrays;

public class ColorTriangleRasterizer {
    private final Raster<Integer> raster;
    public ColorTriangleRasterizer(Raster<Integer> raster) {
        this.raster = raster;
    }

    public void rasterize(VertexTriangle triangle){
        VertexTriangleIterator.iterate(triangle, (x, y, a, b, c)->{
            raster.setPixel(x, y,
                    addColors(
                            multiplyColor(vertAttrToColor(triangle.getVertexA()), a),
                            multiplyColor(vertAttrToColor(triangle.getVertexB()), b),
                            multiplyColor(vertAttrToColor(triangle.getVertexC()), c)
                    ).getRGB());
        });
    }

    private Color vertAttrToColor(Vertex vertex){
        return new Color((int)vertex.getProperty(0), (int)vertex.getProperty(1), (int)vertex.getProperty(2));
    }

    private Color multiplyColor(Color c, double multiplier) {
        return new Color(
                (int) Math.clamp(c.getRed() * multiplier, 0, 255),
                (int) Math.clamp(c.getGreen() * multiplier, 0, 255),
                (int) Math.clamp(c.getBlue() * multiplier, 0, 255)
        );
    }

    private Color addColors(Color... colors){
        int r = Arrays.stream(colors).mapToInt(Color::getRed).sum();
        int g = Arrays.stream(colors).mapToInt(Color::getGreen).sum();
        int b = Arrays.stream(colors).mapToInt(Color::getBlue).sum();
        return new Color(Math.clamp(r, 0, 255), Math.clamp(g, 0, 255), Math.clamp(b, 0, 255));
    }
}
