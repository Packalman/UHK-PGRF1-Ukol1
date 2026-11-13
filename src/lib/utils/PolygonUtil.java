package lib.utils;

import model.Line;
import model.Point;

import java.util.*;

public class PolygonUtil {
    public static ShortenedLine[] getShortenedLines(Line[] lines){
        List<ShortenedLine> result = new ArrayList<>();

        for (var l : lines) {
            Point a = l.getPointA();
            Point b = l.getPointB();
            if(a.getY()!=b.getY()){
                if(a.getY()>b.getY()){
                    Point tmp = a;
                    a = b;
                    b = tmp;
                }
                result.add(new ShortenedLine(new Line(a, b, l.getColor())));
            }
        }

        return result.toArray(ShortenedLine[]::new);
    }

    public static int[] getLineIntersections(int y, ShortenedLine[] shortenedLines){
        Queue<Integer> intersections = new ArrayDeque<>();
        for (int i = 0; i < shortenedLines.length; i++) {
            var line = shortenedLines[i];
            if(line.containsY(y)){
                intersections.add(line.getIntersection(y));
            }
        }

        return intersections.stream().mapToInt(x->x).sorted().toArray();
    }

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
                //zkrácení čar
                int dx = x2-x1;
                int dy = y2-y1;

                int ddx = (int)Math.round(dx/(double)dy);
                int ddy = 1;

                if(y1!=y2-ddy)
                    result.add(new Line(new Point(x1, y1), new Point(x2-ddx, y2-ddy), lines[i].getColor()));
            }
        }

        return result.toArray(new Line[0]);
    }

    /// This method presumes, that lines inside preparedLines are in a format of higher point first
    public static int[] getLineIntersections(int y, Line[] preparedLines){
        Queue<Integer> intersections = new ArrayDeque<>();
        for (int i = 0; i < preparedLines.length; i++) {
            var line = preparedLines[i];
            if(line.containsY(y)){
                int x1 = line.getPointA().getX();
                int y1 = line.getPointA().getY();
                int x2 = line.getPointB().getX();
                int y2 = line.getPointB().getY();

                int dx = x2-x1;
                int dy = y2-y1;

                //0 -> y -> dy
                double x = (y-y1)/(double)dy*(double)dx;
                intersections.add((int)Math.round(x+x1));
            }
        }

        return intersections.stream().mapToInt(x->x).sorted().toArray();
    }

    public static class ShortenedLine{
        private final Line line;
        private final int maxY;
        private final int minY;

        public ShortenedLine(Line line){
            this.line = line;

            minY = Math.min(line.getPointA().getY(), line.getPointB().getY());
            maxY = Math.max(line.getPointA().getY(), line.getPointB().getY())-1;
        }

        public boolean containsY(int y){
            return (maxY>=y)&&(y>=minY);
        }

        /// returns X coordinate of an intersection
        public int getIntersection(int y){
            int x1 = line.getPointA().getX();
            int x2 = line.getPointB().getX();
            int y1 = line.getPointA().getY();
            int y2 = line.getPointB().getY();
            int dx = x2-x1;
            int dy = y2-y1;

            double x = (y-y1)/(double)dy*(double)dx;
            return (int)Math.round(x+x1);
        }
    }
}
