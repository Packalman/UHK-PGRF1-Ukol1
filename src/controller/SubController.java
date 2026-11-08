package controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class SubController {
    public String getName(){return "";}

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
}
