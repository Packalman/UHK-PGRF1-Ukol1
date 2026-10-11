package graphics.rasterizer;

import model.Polygon;

public class PolygonOutlineRasterizer {
    private final LineRasterizer lineRasterizer;
    public PolygonOutlineRasterizer(LineRasterizer lineRasterizer){
        this.lineRasterizer = lineRasterizer;
    }

    public void rasterize(Polygon polygon){
        var lines = polygon.getLines();
        for (int i = 0; i < lines.size(); i++) {
            lineRasterizer.rasterize(lines.get(i));
        }
    }
}
