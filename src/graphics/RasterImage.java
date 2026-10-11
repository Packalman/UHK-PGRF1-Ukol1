package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RasterImage implements Raster<Integer>{
    private final BufferedImage buffer;

    public RasterImage(int width, int height) {
        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public int getWidth() {
        return buffer.getWidth();
    }

    @Override
    public int getHeight() {
        return buffer.getHeight();
    }

    @Override
    public void clear(Integer value) {
        Graphics graphics = buffer.getGraphics();
        graphics.setColor(new Color(value));
        graphics.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        graphics.dispose();
    }

    @Override
    public void present(Graphics graphics) {
        graphics.drawImage(buffer, 0, 0, null);
    }

    @Override
    public void setPixel(int x, int y, Integer value) {
        if(x>=0 && y>=0 && x<getWidth() && y<getHeight()){
            buffer.setRGB(x, y, value);
        }
    }
}
