package view;

import graphics.Raster;
import graphics.RasterImage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Represents a raster drawing surface based on a Swing {@link JPanel}.
 * The canvas stores its image data in a {@link BufferedImage} and allows
 * individual pixels to be manipulated and rendered on the screen.
 *
 * @author PGRF FIM UHK
 * @version 2026
 */
public class Canvas extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(87, 126, 157);//0x2F2F2F

    private final RasterImage raster;
    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- Constructors -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

    public Canvas(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        raster = new RasterImage(width, height);
        clear();

        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {

            }
        }
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- Rendering -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
    public void clear() {
        raster.clear(BACKGROUND_COLOR.getRGB());
    }

    /**
     * Paints the current raster image onto this canvas. Swing calls this method automatically whenever the component
     * needs to be repainted.
     *
     * @param graphics graphics context used for painting the component
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        raster.present(graphics);
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Getters -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */
    public Raster<Integer> getRaster() {
        return raster;
    }

}
