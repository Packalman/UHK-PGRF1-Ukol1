package controller;

import graphics.rasterizer.FilledTriangleRasterizer;
import graphics.rasterizer.LineRasterizer;
import model.BasicTriangle;
import model.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FilledTriangleDrawerController extends TriangleDrawerController{
    private static final int FILL_COLOR = new Color(255, 151, 32).getRGB();
    private final FilledTriangleRasterizer triangleRasterizer;
    private final List<BasicTriangle> triangles;
    public FilledTriangleDrawerController(LineRasterizer lineRasterizer, FilledTriangleRasterizer triangleRasterizer){
        super(lineRasterizer);
        this.triangleRasterizer = triangleRasterizer;

        triangles = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "Filled triangles";
    }

    @Override
    public void constructTriangle(Point a, Point b, Point c) {
        triangles.add(new BasicTriangle(a, b, c, FILL_COLOR));
    }

    @Override
    public void render() {
        for(var t : triangles)
            triangleRasterizer.rasterize(t);

        super.render();
    }

    @Override
    public void clear() {
        super.clear();
        triangles.clear();
    }
}
