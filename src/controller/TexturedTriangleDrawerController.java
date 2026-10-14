package controller;

import graphics.rasterizer.LineRasterizer;
import graphics.rasterizer.TextureTriangleRasterizer;
import model.Point;
import model.Vertex;
import model.VertexTriangle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TexturedTriangleDrawerController extends TriangleDrawerController {
    private final TextureTriangleRasterizer textureTriangleRasterizer;
    private final BufferedImage buffer;
    private final List<VertexTriangle> triangles;
    public TexturedTriangleDrawerController(LineRasterizer lineRasterizer, TextureTriangleRasterizer textureTriangleRasterizer) {
        super(lineRasterizer);
        this.textureTriangleRasterizer = textureTriangleRasterizer;
        this.triangles = new ArrayList<>();

        try {
            this.buffer = ImageIO.read(new File("./src/resources/crate.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void constructTriangle(Point a, Point b, Point c) {
        triangles.add(new VertexTriangle(
                new Vertex(a, 0, 0), new Vertex(b, 1, 0), new Vertex(c, 0, 1)
        ));
    }

    @Override
    public void render() {
        for (var t : triangles)
            textureTriangleRasterizer.rasterize(t, buffer);

        super.render();
    }

    @Override
    public String getName() {
        return "Textured triangles";
    }

    @Override
    public void clear() {
        super.clear();
        triangles.clear();
    }
}
