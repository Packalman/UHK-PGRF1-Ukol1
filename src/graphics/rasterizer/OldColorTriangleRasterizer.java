package graphics.rasterizer;

import graphics.Raster;
import graphics.TriangleIterator;
import model.Point;
import model.Vertex;
import model.VertexTriangle;

import java.awt.*;
import java.util.Arrays;

@Deprecated
public class OldColorTriangleRasterizer {
    private final Raster<Integer> raster;
    public OldColorTriangleRasterizer(Raster<Integer> raster) {
        this.raster = raster;
    }

    public void rasterize(VertexTriangle triangle) {
        Point a = triangle.getVertexA().getPoint();
        Point b = triangle.getVertexB().getPoint();
        Point c = triangle.getVertexC().getPoint();

        int triangleArea = Math.abs(crossZ(vectorize(a, b), vectorize(a, c)))/2;

        TriangleIterator.iterate(
                a, b, c,
                (x, y)->{
                    Point p = new Point(x, y);
                    //barrycentric coordinates?
                    int aArea = Math.abs(crossZ(vectorize(p, b), vectorize(p, c)))/2;
                    int bArea = Math.abs(crossZ(vectorize(p, a), vectorize(p, c)))/2;
                    //int cArea = Math.abs(crossZ(vectorize(p, a), vectorize(p, b)))/2;
                    double aMul = aArea/(double)triangleArea;
                    double bMul = bArea/(double)triangleArea;
                    double cMul = 1.0-aMul-bMul;

                    raster.setPixel(x, y, addColors(
                            multiplyColor(vertAttrToColor(triangle.getVertexA()), aMul),
                            multiplyColor(vertAttrToColor(triangle.getVertexB()), bMul),
                            multiplyColor(vertAttrToColor(triangle.getVertexC()), cMul)).getRGB());

                    //System.out.printf("%s vs\t%s\t%s\t%s\tleftover\t%s\n", triangleArea, aArea, bArea, cArea, (triangleArea-aArea-bArea-cArea));
                });
    }

    private Point vectorize(Point toZero, Point toValue){
        return new Point(toValue.getX()-toZero.getX(), toValue.getY()-toZero.getY());
    }

    private int crossZ(Point a, Point b){
        //https://en.wikipedia.org/wiki/Cross_product
        return a.getX()*b.getY()-a.getY()*b.getX();
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
