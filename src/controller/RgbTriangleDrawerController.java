package controller;

import graphics.rasterizer.ColorTriangleRasterizer;
import graphics.rasterizer.LineRasterizer;
import model.Point;
import model.Vertex;
import model.VertexTriangle;

import java.util.ArrayList;
import java.util.List;

public class RgbTriangleDrawerController extends TriangleDrawerController {
    private final ColorTriangleRasterizer colorTriangleRasterizer;
    private final List<VertexTriangle> triangles;
    public RgbTriangleDrawerController(LineRasterizer lineRasterizer, ColorTriangleRasterizer colorTriangleRasterizer) {
        super(lineRasterizer);
        this.colorTriangleRasterizer = colorTriangleRasterizer;
        triangles = new ArrayList<>();
    }

    @Override
    public void constructTriangle(Point a, Point b, Point c) {
        triangles.add(new VertexTriangle(new Vertex(a, 255, 0, 0), new Vertex(b, 0, 255, 0), new Vertex(c, 0, 0, 255)));
    }

    @Override
    public String getName() {
        return "RGB triangles";
    }

    @Override
    public void render() {
        for (var t : triangles)
            colorTriangleRasterizer.rasterize(t);
        super.render();
    }

    @Override
    public void clear() {
        super.clear();
        triangles.clear();
    }
}
