package controller;


import graphics.rasterizer.LineRasterizer;
import graphics.rasterizer.PolygonOutlineRasterizer;
import model.Point;
import model.Polygon;

import java.util.ArrayList;
import java.util.List;

public class PolygonDrawerController extends SubController {
    private final PolygonOutlineRasterizer polygonRasterizer;
    private final LineRasterizer lineRasterizer;
    private final List<Polygon> completedPolygons;
    private final List<Point> polygonPoints;

    public PolygonDrawerController(PolygonOutlineRasterizer polygonRasterizer, LineRasterizer lineRasterizer){
        this.polygonRasterizer = polygonRasterizer;
        this.lineRasterizer = lineRasterizer;
        completedPolygons = new ArrayList<>();
        polygonPoints = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "Polygons";
    }

    @Override
    public void render() {

    }

    @Override
    public void clear() {

    }
}
