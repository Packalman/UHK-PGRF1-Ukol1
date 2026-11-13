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
import view.Sidebar;
import view.Toolbar;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

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
    private final DrawableSubmitter drawableSubmitter;
    private final SubControllerManager subControllerManager;


    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- Constructors -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

    public Controller(Canvas canvas, Toolbar toolbar, Sidebar sidebar) {
        this.canvas = canvas;
        this.toolbar = toolbar;
        this.rasterizerContext = new RasterizerContext(canvas.getRaster());

        Map<ControllerType, SubController> controllers = new Hashtable<>();
        this.drawableSubmitter = new DrawableSubmitter(drawables);

        controllers.put(ControllerType.SELECT, new SelectorSubController(rasterizerContext, drawables, this::selectedDrawableChanged));
        controllers.put(ControllerType.LINES, new LineCreatorSubController(rasterizerContext, drawableSubmitter));
        controllers.put(ControllerType.POLYGONS, new PolygonCreatorSubController(rasterizerContext, drawableSubmitter));
        controllers.put(ControllerType.MODIFY_LINE, new DrawableLineModifier(rasterizerContext, this::handleDrawableModified));
        controllers.put(ControllerType.SEED_FILL, new SeedFillCreatorSubController(rasterizerContext, drawableSubmitter));
        controllers.put(ControllerType.MODIFY_SEED_FILL, new SeedFillModifier(rasterizerContext, this::handleDrawableModified));

        this.subControllerManager = new SubControllerManager(controllers, this::render, sidebar::changeSetting);
        subControllerManager.addListener(canvas);
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Main functions -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
    public void init() {
        canvas.clear();
        toolbar.init(this::toolbarButtonPressed, canvas);
    }

    private void toolbarButtonPressed(Toolbar.ToolbarButtonType toolbarButton) {
        canvas.grabFocus();
        if(toolbarButton == Toolbar.ToolbarButtonType.CLEAR){
            clear();
        }
        else{
            switch (toolbarButton){
                case SELECT -> subControllerManager.setActiveController(ControllerType.SELECT);
                case LINES -> subControllerManager.setActiveController(ControllerType.LINES);
                case POLYGON -> subControllerManager.setActiveController(ControllerType.POLYGONS);
                case SEED_FILL -> subControllerManager.setActiveController(ControllerType.SEED_FILL);
            }
        }
    }

    private void selectedDrawableChanged(Drawable drawable, MouseEvent e){
        if(drawable instanceof LineDrawable lineDrawable){
            subControllerManager.setActiveController(ControllerType.MODIFY_LINE);
            ((DrawableLineModifier)subControllerManager.getActiveSubController()).setModifiedDrawable(lineDrawable, e);
            drawables.remove(lineDrawable);

            render();
        }
        if(drawable instanceof SeedFillDrawable seedFillDrawable){
            subControllerManager.setActiveController(ControllerType.MODIFY_SEED_FILL);
            ((SeedFillModifier)subControllerManager.getActiveSubController()).setModifiedDrawable(seedFillDrawable, e);
            drawables.remove(seedFillDrawable);

            render();
        }
    }

    private void handleDrawableModified(Drawable... newDrawables){
        subControllerManager.setActiveController(ControllerType.SELECT);
        drawables.addAll(Arrays.asList(newDrawables));
        render();
    }

    private void clear(){
        subControllerManager.clear();

        drawables.clear();
    }


    private void render(){
        canvas.clear();
        boolean selectMode = subControllerManager.getActiveSubControllerType() == ControllerType.SELECT;
        for (var d : drawables){
            d.draw(selectMode);
        }

        subControllerManager.render();

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

    private static class SubControllerManager {
        private final Map<ControllerType, SubController> controllers;
        private final InputEventReceiver inputNotifier;
        private final ActiveSettingChangedNotifier settingNotifier;
        private ControllerType controllerType;

        public SubControllerManager(Map<ControllerType, SubController> controllers, InputEventReceiver inputNotifier, ActiveSettingChangedNotifier settingNotifier) {

            controllerType = ControllerType.SELECT;
            this.controllers = controllers;
            this.inputNotifier = inputNotifier;
            this.settingNotifier = settingNotifier;
        }

        public void setActiveController(ControllerType controller){
            if(controllerType!=controller){
                getActiveSubController().disable();
                this.controllerType = controller;
                settingNotifier.activeSettingChanged(getActiveSubController().getSetting());
            }
        }

        public ControllerType getActiveSubControllerType(){
            return this.controllerType;
        }

        public SubController getActiveSubController(){
            return controllers.get(this.controllerType);
        }

        public void addListener(Component component){
            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    getActiveSubController().mousePressed(e);
                    inputNotifier.notifyInputReceived();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    getActiveSubController().mouseReleased(e);
                    inputNotifier.notifyInputReceived();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    getActiveSubController().mouseClicked(e);
                    inputNotifier.notifyInputReceived();
                }
            });
            component.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                    getActiveSubController().keyPressed(e);
                    inputNotifier.notifyInputReceived();
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    super.keyReleased(e);
                    getActiveSubController().keyReleased(e);
                    inputNotifier.notifyInputReceived();
                }
            });
            component.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    super.mouseMoved(e);
                    if(!component.hasFocus())
                        component.requestFocus();
                    getActiveSubController().mouseMoved(e);
                    inputNotifier.notifyInputReceived();
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    super.mouseDragged(e);
                    getActiveSubController().mouseDragged(e);
                    inputNotifier.notifyInputReceived();
                }
            });
        }

        public void render(){
            getActiveSubController().render();
        }

        public void clear(){
            for (var k : controllers.entrySet()){
                k.getValue().clear();
            }
        }

        public interface InputEventReceiver{
            void notifyInputReceived();
        }

        public interface ActiveSettingChangedNotifier {
            void activeSettingChanged(Sidebar.Setting setting);
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