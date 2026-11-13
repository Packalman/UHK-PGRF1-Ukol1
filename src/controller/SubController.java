package controller;

import graphics.rasterizer.RasterizerContext;
import view.Sidebar;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class SubController {
    private final RasterizerContext context;
    private final Sidebar.Setting setting;

    public SubController(RasterizerContext context, Sidebar.Setting setting){
        this.context = context;
        this.setting = setting;
    }

    protected RasterizerContext getContext(){
        return this.context;
    }

    public final Sidebar.Setting getSetting(){
        return this.setting;
    }

    public void mouseClicked(MouseEvent e){

    }

    public void mousePressed(MouseEvent e){

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e){

    }

    public void mouseMoved(MouseEvent e){

    }

    public void keyPressed(KeyEvent e){

    }

    public void keyReleased(KeyEvent e){

    }

    public abstract void render();
    public abstract void clear();

    public void disable(){};
}
