package model;

import java.awt.event.MouseEvent;

public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point fromMouseEvent(MouseEvent mouseEvent){
        return new Point(mouseEvent.getX(), mouseEvent.getY());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
