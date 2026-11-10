package graphics.rasterizer;

import graphics.Raster;
import graphics.filler.CollectionSeedFiller;
import graphics.filler.SeedFiller;

public class RasterizerContext {
    private final Raster<Integer> raster;
    private final LineRasterizer lineRasterizer;
    private final PolygonOutlineRasterizer polygonOutlineRasterizer;
    private final FilledPolygonRasterizer filledPolygonRasterizer;
    private final FilledCircleRasterizer filledCircleRasterizer;
    private final SeedFiller seedFiller;

    public RasterizerContext(Raster<Integer> raster) {
        this.raster = raster;

        lineRasterizer = new TrivialLineRasterizer(raster);
        polygonOutlineRasterizer = new PolygonOutlineRasterizer(lineRasterizer);
        filledPolygonRasterizer = new ScanLinePolygonRasterizer(raster, polygonOutlineRasterizer);
        filledCircleRasterizer = new RowFilledCircleRasterizer(raster);
        seedFiller = new CollectionSeedFiller(raster);
    }

    public Raster<Integer> getRaster() {
        return raster;
    }

    public LineRasterizer getLineRasterizer() {
        return lineRasterizer;
    }

    public PolygonOutlineRasterizer getPolygonOutlineRasterizer() {
        return polygonOutlineRasterizer;
    }

    public FilledPolygonRasterizer getFilledPolygonRasterizer() {
        return filledPolygonRasterizer;
    }

    public FilledCircleRasterizer getFilledCircleRasterizer() {
        return filledCircleRasterizer;
    }

    public SeedFiller getSeedFiller() {
        return seedFiller;
    }
}
