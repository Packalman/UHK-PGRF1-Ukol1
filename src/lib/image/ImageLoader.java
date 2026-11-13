package lib.image;

import graphics.Raster;
import graphics.RasterImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageLoader {
    public static Raster<Integer> load(Path path){
        if(!Files.isDirectory(path)){
            try{
                BufferedImage buffer = ImageIO.read(path.toFile());
                return new RasterImage(buffer);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return new RasterImage(10, 10);
    }
}
