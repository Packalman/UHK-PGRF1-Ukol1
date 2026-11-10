package lib.utils;

import lib.transforms.Vec2D;
import model.Point;

public class LineUtil {
    private static final double unitDiagonal = Math.sin(Math.PI/4);
    private static final Vec2D[] axis = new Vec2D[]{
            new Vec2D(1, 0),
            new Vec2D(unitDiagonal, unitDiagonal),
            new Vec2D(0, 1),
            new Vec2D(-unitDiagonal, unitDiagonal),

            new Vec2D(-1, 0),
            new Vec2D(-unitDiagonal, -unitDiagonal),
            new Vec2D(0, -1),
            new Vec2D(unitDiagonal, -unitDiagonal),
    };

    public static Point getPointAxisLocked(Point startPoint, Point pointToLock){
        int x1 = startPoint.getX();
        int y1 = startPoint.getY();

        int x2 = pointToLock.getX();
        int y2 = pointToLock.getY();

        int dx = x2 - x1;
        int dy = y2 - y1;
        double length = Math.round(Math.sqrt((dx * dx) + (dy * dy)));

        double unitX = dx / length;
        double unitY = dy / length;

        double angle = Math.atan2(unitY, unitX)+Math.PI;
        angle = (angle + (0.125 + 1) * Math.PI) % (2 * Math.PI);

        int axisIndex = (int) Math.floor(angle / (2 * Math.PI) * 8);

        Vec2D v = axis[axisIndex].mul(length);

        return new Point((int) Math.round(v.getX()) + x1, (int) Math.round(v.getY()) + y1);
    }
}
