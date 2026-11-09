package controller;

import controller.sub_controllers.drawable_creators.LineCreatorSubController;
import controller.sub_controllers.SelectorSubController;
import controller.sub_controllers.drawable_creators.PolygonCreatorSubController;
import controller.sub_controllers.drawable_modifier.DrawableLineModifier;
import graphics.drawable.Drawable;
import graphics.drawable.LineDrawable;
import graphics.filler.CollectionSeedFiller;
import graphics.filler.SeedFiller;
import graphics.rasterizer.*;
import view.Canvas;
import view.Toolbar;

import java.awt.event.*;
import java.util.*;

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
    private final FilledCircleRasterizer filledCircleRasterizer;
    private final FilledTriangleRasterizer filledTriangleRasterizer;
    private final ColorTriangleRasterizer colorTriangleRasterizer;
    private final TextureTriangleRasterizer textureTriangleRasterizer;
    private final SeedFiller seedFiller;

    public static final int LINE_INTERACTION_DISTANCE = 20;
    public static final int LINE_INTERACTION_DISTANCE_SQUARED = LINE_INTERACTION_DISTANCE * LINE_INTERACTION_DISTANCE;


    private List<Drawable> drawables = new ArrayList<>();
    private DrawableSubmitter drawableSubmitter;

    private ControllerType activeController;
    private final Dictionary<ControllerType, SubController> controllers;


    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- Constructors -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

    public Controller(Canvas canvas, Toolbar toolbar) {
        this.canvas = canvas;
        this.toolbar = toolbar;
        this.lineRasterizer = new TrivialLineRasterizer(canvas.getRaster());
        this.polygonRasterizer = new PolygonOutlineRasterizer(lineRasterizer);
        this.filledCircleRasterizer = new RowFilledCircleRasterizer(canvas.getRaster());
        this.filledTriangleRasterizer = new FilledTriangleRasterizer(canvas.getRaster());
        this.colorTriangleRasterizer = new ColorTriangleRasterizer(canvas.getRaster());
        this.textureTriangleRasterizer = new TextureTriangleRasterizer(canvas.getRaster());
        this.seedFiller = new CollectionSeedFiller(canvas.getRaster());

        this.controllers = new Hashtable<>();
        drawableSubmitter = new DrawableSubmitter(drawables);
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Main functions -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
    public void init() {
        canvas.clear();

        toolbar.init(this::toolbarButtonPressed, canvas);

        controllers.put(ControllerType.SELECT, new SelectorSubController(filledCircleRasterizer, drawables, this::selectedDrawableChanged));
        controllers.put(ControllerType.LINES, new LineCreatorSubController(drawableSubmitter, lineRasterizer));
        controllers.put(ControllerType.POLYGONS, new PolygonCreatorSubController(drawableSubmitter, lineRasterizer, polygonRasterizer));
        controllers.put(ControllerType.MODIFY_LINE, new DrawableLineModifier(filledCircleRasterizer, lineRasterizer, this::handleDrawableModified));

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                controllers.get(activeController).mousePressed(e);
                render();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                controllers.get(activeController).mouseReleased(e);
                render();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                controllers.get(activeController).mouseClicked(e);
                render();
            }
        });
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                controllers.get(activeController).keyPressed(e);
                render();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                controllers.get(activeController).keyReleased(e);
                render();
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                controllers.get(activeController).mouseMoved(e);
                render();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                controllers.get(activeController).mouseDragged(e);
                render();
            }
        });
    }

    private void toolbarButtonPressed(Toolbar.ToolbarButtonType toolbarButton) {
        canvas.grabFocus();
        if(toolbarButton == Toolbar.ToolbarButtonType.CLEAR){
            clear();
        }
        else{
            if(activeController!=null)
                controllers.get(activeController).disable();
            switch (toolbarButton){
                case SELECT -> activeController = ControllerType.SELECT;
                case LINES -> activeController = ControllerType.LINES;
                case POLYGON -> activeController = ControllerType.POLYGONS;
            }
        }
    }

    private void selectedDrawableChanged(Drawable drawable, MouseEvent e){
        if(drawable instanceof LineDrawable lineDrawable){
            activeController = ControllerType.MODIFY_LINE;
            ((DrawableLineModifier)controllers.get(activeController)).setModifiedDrawable(lineDrawable, e);
            drawables.remove(lineDrawable);

            render();
        }
    }

    private void handleDrawableModified(Drawable... newDrawables){
        activeController = ControllerType.SELECT;
        drawables.addAll(Arrays.asList(newDrawables));
        render();
    }

    private void clear(){
        var c = controllers.elements();
        while (c.hasMoreElements()){
            c.nextElement().clear();
        }
        drawables.clear();
    }


    private void render(){
        canvas.clear();

        for (var d : drawables){
            d.draw();
        }

        controllers.get(activeController).render();

        canvas.repaint();
    }

    public class DrawableSubmitter{
        private final List<Drawable> drawables;

        public DrawableSubmitter(List<Drawable> drawables) {
            this.drawables = drawables;
        }

        public void submit(Drawable drawable){
            drawables.add(drawable);
        }
    }

    private enum ControllerType {
        SELECT,
        MODIFY_LINE,
        MODIFY_POLYGONS,
        LINES,
        POLYGONS,
        RECTANGLES
    }
}