package controller;

import graphics.Raster;
import graphics.filler.SeedFiller;
import model.Point;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SeedFillController extends SubController {

    private final SeedFiller seedFiller;
    private final Raster<Integer> raster;
    private Point seedPoint;

    public SeedFillController(Raster<Integer> raster, SeedFiller seedFiller) {
        this.raster = raster;
        this.seedFiller = seedFiller;
    }

    @Override
    public String getName() {
        return "Fill";
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            seedPoint = Point.fromMouseEvent(e);
        }
    }

    @Override
    public void render() {
        if(seedPoint!=null){
            seedFiller.fillReplaceBackground(seedPoint.getX(), seedPoint.getY(), raster.getPixel(seedPoint.getX(), seedPoint.getY()), Color.BLACK.getRGB());
        }
    }

    @Override
    public void clear() {
        seedPoint = null;
    }
}
