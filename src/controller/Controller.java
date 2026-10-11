package controller;

import graphics.rasterizer.*;
import model.*;
import model.Point;
import model.Polygon;
import view.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles user input and controls the application flow related to the {@link Canvas}.
 * The controller coordinates input events, canvas operations, and rendering updates.
 *
 * @author PGRF FIM UHK
 * @version 2026
 */
public class Controller {

    private final Canvas canvas;
    private final LineRasterizer lineRasterizer;
    private final PolygonOutlineRasterizer polygonRasterizer;
    private final ColorTriangleRasterizer colorTriangleRasterizer;
    private final TextureTriangleRasterizer textureTriangleRasterizer;

    private final int POLYGON_PREVIEW_COLOR = Color.RED.getRGB();
    private final int POLYGON_COLOR = Color.WHITE.getRGB();

    private final java.util.List<Line> previewLines = new ArrayList<>();
    private final java.util.List<Point> polyPoints = new ArrayList<>();
    private Polygon polygon = new Polygon(POLYGON_COLOR);


    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- Constructors -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

    public Controller(Canvas canvas) {
        this.canvas = canvas;
        this.lineRasterizer = new TrivialLineRasterizer(canvas.getRaster());
        this.polygonRasterizer = new PolygonOutlineRasterizer(lineRasterizer);
        this.colorTriangleRasterizer = new ColorTriangleRasterizer(canvas.getRaster());
        this.textureTriangleRasterizer = new TextureTriangleRasterizer(canvas.getRaster());
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Main functions -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    private java.util.List<Point> polygonPoints = new ArrayList<>();

    float rotation = 0;

    public void init() {
        canvas.clear();

        /*
        Point a = new Point(20, 300);
        Point b = new Point(170, 20);
        Point c = new Point(320, 300);
        Point d = new Point(470, 20);

        colorTriangleRasterizer.rasterize(new VertexTriangle(new Vertex(a, 255, 0, 0), new Vertex(b, 0, 255, 0), new Vertex(c, 0, 0, 255)));
        colorTriangleRasterizer.rasterize(new VertexTriangle(new Vertex(d, 0, 0, 0), new Vertex(b, 0, 255, 0), new Vertex(c, 0, 0, 255)));
        */

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(e.getButton() == MouseEvent.BUTTON3){

                }
                else if(e.getButton() == MouseEvent.BUTTON1){
                    polygonPoints.add(new Point(e.getX(), e.getY()));
                    polygon = new Polygon(POLYGON_COLOR, polygonPoints);
                    render();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }
        });
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_C){

                }
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (polygonPoints.size()>0){
                    previewLines.clear();
                    previewLines.add(new Line(polygonPoints.get(0), new Point(e.getX(), e.getY()), POLYGON_PREVIEW_COLOR));

                    if(polygonPoints.size()>1){
                        previewLines.add(new Line(polygonPoints.get(polygonPoints.size()-1), new Point(e.getX(), e.getY()), POLYGON_PREVIEW_COLOR));
                    }
                }
                render();
            }
        });
    }

    public void render(){
        canvas.clear();

        polygonRasterizer.rasterize(polygon);

        for (var line : previewLines){
            lineRasterizer.rasterize(line);
        }

        canvas.repaint();
    }

    private void drawTexture(Point a, Point b, Point c, Point d){
        Point[] texturePoints = new Point[]{
                a, b, c,
                a, c, d
        };
        try {
            var buffer = ImageIO.read(new File("./src/resources/crate.png"));
            textureTriangleRasterizer.rasterize(new VertexTriangle(new Vertex(texturePoints[0], 0, 1), new Vertex(texturePoints[1], 0, 0), new Vertex(texturePoints[2], 1, 0)), buffer);
            textureTriangleRasterizer.rasterize(new VertexTriangle(new Vertex(texturePoints[3], 0, 1), new Vertex(texturePoints[4], 1, 0), new Vertex(texturePoints[5], 1, 1)), buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}