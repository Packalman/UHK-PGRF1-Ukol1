package controller;

import graphics.rasterizer.*;
import view.Canvas;
import view.Toolbar;

import java.awt.event.*;

/**
 * Handles user input and controls the application flow related to the {@link Canvas}.
 * The controller coordinates input events, canvas operations, and rendering updates.
 *
 * @author PGRF FIM UHK
 * @version 2026
 */
public class Controller {

    private final Canvas canvas;
    private final Toolbar toolbar;

    private final LineRasterizer lineRasterizer;
    private final PolygonOutlineRasterizer polygonRasterizer;
    private final FilledTriangleRasterizer filledTriangleRasterizer;
    private final ColorTriangleRasterizer colorTriangleRasterizer;
    private final TextureTriangleRasterizer textureTriangleRasterizer;

    public static final int LINE_INTERACTION_DISTANCE = 20;
    public static final int LINE_INTERACTION_DISTANCE_SQUARED = LINE_INTERACTION_DISTANCE * LINE_INTERACTION_DISTANCE;


    private java.util.List<SubController> controllers;

    private int selectedControllerIndex = 0;


    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- Constructors -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

    public Controller(Canvas canvas, Toolbar toolbar) {
        this.canvas = canvas;
        this.toolbar = toolbar;
        this.lineRasterizer = new TrivialLineRasterizer(canvas.getRaster());
        this.polygonRasterizer = new PolygonOutlineRasterizer(lineRasterizer);
        this.filledTriangleRasterizer = new FilledTriangleRasterizer(canvas.getRaster());
        this.colorTriangleRasterizer = new ColorTriangleRasterizer(canvas.getRaster());
        this.textureTriangleRasterizer = new TextureTriangleRasterizer(canvas.getRaster());
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Main functions -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
    public void init() {
        canvas.clear();

        controllers = java.util.List.of(
                new LineDrawerController(lineRasterizer),
                new PolygonDrawerController(polygonRasterizer, lineRasterizer),
                new FilledTriangleDrawerController(lineRasterizer, filledTriangleRasterizer),
                new RgbTriangleDrawerController(lineRasterizer, colorTriangleRasterizer),
                new TexturedTriangleDrawerController(lineRasterizer, textureTriangleRasterizer)
        );

        toolbar.init(this::selectedControllerChange, controllers.stream().map(SubController::getName).toList());
        controllers.get(0).enable();

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                controllers.get(selectedControllerIndex).mousePressed(e);
                render();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                controllers.get(selectedControllerIndex).mouseReleased(e);
                render();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                controllers.get(selectedControllerIndex).mouseClicked(e);
                render();
            }
        });
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                controllers.get(selectedControllerIndex).keyPressed(e);

                if(e.getKeyCode() == KeyEvent.VK_C){
                    for (var c : controllers)
                        c.clear();
                }

                render();
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                controllers.get(selectedControllerIndex).mouseMoved(e);
                render();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                controllers.get(selectedControllerIndex).mouseDragged(e);
                render();
            }
        });
    }

    private void selectedControllerChange(int index){
        controllers.get(selectedControllerIndex).disable();
        controllers.get(index).enable();
        selectedControllerIndex = index;

        canvas.grabFocus();

        render();
    }

    public void render(){
        canvas.clear();

        for (var c : controllers){
            c.render();
        }

        canvas.repaint();
    }
}