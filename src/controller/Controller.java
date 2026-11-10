package controller;

import controller.sub_controllers.drawable_creators.LineCreatorSubController;
import controller.sub_controllers.SelectorSubController;
import controller.sub_controllers.drawable_creators.PolygonCreatorSubController;
import controller.sub_controllers.drawable_creators.SeedFillCreatorSubController;
import controller.sub_controllers.drawable_modifier.DrawableLineModifier;
import controller.sub_controllers.drawable_modifier.SeedFillModifier;
import graphics.drawable.Drawable;
import graphics.drawable.LineDrawable;
import graphics.drawable.SeedFillDrawable;
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

    private final RasterizerContext rasterizerContext;

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
        this.rasterizerContext = new RasterizerContext(canvas.getRaster());

        this.controllers = new Hashtable<>();
        drawableSubmitter = new DrawableSubmitter(drawables);
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Main functions -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
    public void init() {
        canvas.clear();

        toolbar.init(this::toolbarButtonPressed, canvas);

        controllers.put(ControllerType.SELECT, new SelectorSubController(rasterizerContext, drawables, this::selectedDrawableChanged));
        controllers.put(ControllerType.LINES, new LineCreatorSubController(rasterizerContext, drawableSubmitter));
        controllers.put(ControllerType.POLYGONS, new PolygonCreatorSubController(rasterizerContext, drawableSubmitter));
        controllers.put(ControllerType.MODIFY_LINE, new DrawableLineModifier(rasterizerContext, this::handleDrawableModified));
        controllers.put(ControllerType.SEED_FILL, new SeedFillCreatorSubController(rasterizerContext, drawableSubmitter));
        controllers.put(ControllerType.MODIFY_SEED_FILL, new SeedFillModifier(rasterizerContext, this::handleDrawableModified));

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
                case SEED_FILL -> activeController = ControllerType.SEED_FILL;
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
        if(drawable instanceof SeedFillDrawable seedFillDrawable){
            activeController = ControllerType.MODIFY_SEED_FILL;
            ((SeedFillModifier)controllers.get(activeController)).setModifiedDrawable(seedFillDrawable, e);
            drawables.remove(seedFillDrawable);

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
        boolean selectMode = activeController == ControllerType.SELECT;
        for (var d : drawables){
            d.draw(selectMode);
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
        MODIFY_SEED_FILL,
        LINES,
        POLYGONS,
        RECTANGLES,
        SEED_FILL,
    }
}