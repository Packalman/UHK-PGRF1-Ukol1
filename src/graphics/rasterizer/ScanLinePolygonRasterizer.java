package graphics.rasterizer;

import graphics.Raster;
import lib.utils.PolygonUtil;
import model.Line;
import model.Polygon;

public class ScanLinePolygonRasterizer implements FilledPolygonRasterizer{
    private final Raster<Integer> raster;
    private final PolygonOutlineRasterizer outlineRasterizer;

    public ScanLinePolygonRasterizer(Raster<Integer> raster, PolygonOutlineRasterizer outlineRasterizer) {
        this.raster = raster;
        this.outlineRasterizer = outlineRasterizer;
    }

    @Override
    public void rasterize(Polygon polygon, int fillColor) {
        rasterize(PolygonUtil.getLinesForRasterization(polygon.getLines()), polygon, fillColor);
    }

    @Override
    public void rasterize(Line[] preparedLines, Polygon polygon, int fillColor) {
        for (int currentY = polygon.getMinYBound(); currentY <= polygon.getMaxYBound(); currentY++) {
            var intersections = PolygonUtil.getLineIntersections(currentY, preparedLines);
            for (int i = 0; i < intersections.length-1; i++) {
                int start = intersections[i];
                int end = intersections[i+1];

                for (int j = start; j < end; j++) {
                    raster.setPixel(j, currentY, fillColor);
                }
            }
        }

        outlineRasterizer.rasterize(polygon);
    }
}
