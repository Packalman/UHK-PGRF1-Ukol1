package lib.utils;

import model.Line;
import model.Point;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PolygonUtil {
    public static Line[] getLinesForRasterization(Line[] lines){
        List<Line> result = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            var a = lines[i].getPointA();
            var b = lines[i].getPointB();
            int x1 = a.getX();
            int y1 = a.getY();
            int x2 = b.getX();
            int y2 = b.getY();

            if(y1!=y2){//vyfiltrování vodorovného úseku
                //reorientace hrany shora dolů
                if(y1>y2){
                    int tmpX = x1;
                    int tmpY = y1;
                    x1 = x2;
                    y1 = y2;
                    x2 = tmpX;
                    y2 = tmpY;
                }

                int dx = x2-x1;
                int dy = y2-y1;

                int length = Math.max(Math.abs(dx), Math.abs(dy));

                int ddx = (int)Math.round(dx/(double)length);
                int ddy = (int)Math.round(dy/(double)length);

                result.add(new Line(new Point(x1, y1), new Point(x2-ddx, y2-ddy), lines[i].getColor()));
            }
        }

        return result.toArray(new Line[0]);
    }

    /// This method presumes, that lines inside preparedLines are in a format of higher point first
    public static int[] getLineIntersections(int y, Line[] preparedLines){
        Queue<Integer> intersections = new ArrayDeque<>();
        for (int i = 0; i < preparedLines.length; i++) {
            var l = preparedLines[i];
            int yValue = i+y;
            if(l.containsY(yValue)){
                int x1 = l.getPointA().getX();
                int y1 = l.getPointA().getY();
                int x2 = l.getPointB().getX();
                int y2 = l.getPointB().getY();

                int dx = x2-x1;
                int dy = y2-y1;

                assert dy>0;

                //0 -> y -> dy
                double x = (dx/(double)dy)*y;
                intersections.add((int)Math.round(x));

                //intersections.add()
            }
        }

        return intersections.stream().mapToInt(x->x).toArray();
    }
}
