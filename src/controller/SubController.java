package controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class SubController {
    private boolean enabled = false;
    public abstract String getName();

    public void enable(){
        enabled = true;
    }
    public void disable(){
        enabled = false;
    }

    public boolean isEnabled(){
        return enabled;
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

    public abstract void render();
    public abstract void clear();
}
