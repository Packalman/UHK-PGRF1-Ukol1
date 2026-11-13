package controller.sub_controllers;

import controller.SubController;
import graphics.drawable.Drawable;
import graphics.rasterizer.RasterizerContext;
import view.Sidebar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

public class SelectorSubController extends SubController {
    private final List<Drawable> drawables;
    private final DrawableSelectedHandler drawableSelectedHandler;

    private static final int PREVIEW_CIRCLE_SIZE = 7;
    private static final int PREVIEW_COLOR = new Color(255, 0, 0).getRGB();

    private Drawable howerDrawable;


    public SelectorSubController(RasterizerContext context, List<Drawable> drawables, DrawableSelectedHandler drawableSelectedHandler) {
        super(context, Sidebar.Setting.NONE);
        this.drawables = drawables;
        this.drawableSelectedHandler = drawableSelectedHandler;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        for (int i = drawables.size()-1; i > -1; i--) {
            if(drawables.get(i).isPointNear(e.getX(), e.getY())){
                howerDrawable = drawables.get(i);
                return;
            }
        }
        howerDrawable = null;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        if(e.getKeyCode() == KeyEvent.VK_DELETE){
            if(howerDrawable!=null){
                drawables.remove(howerDrawable);
                howerDrawable = null;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if(howerDrawable!=null){
            drawableSelectedHandler.select(howerDrawable, e);
            clear();
        }
    }

    @Override
    public void render() {
        if(howerDrawable!=null){
            for (var point : howerDrawable.getPoints()){
                getContext().getFilledCircleRasterizer().rasterize(point.getX(), point.getY(), PREVIEW_CIRCLE_SIZE, PREVIEW_COLOR);
            }
        }
    }

    @Override
    public void clear() {
        howerDrawable = null;
    }

    public interface DrawableSelectedHandler{
        void select(Drawable drawable, MouseEvent e);
    }
}
