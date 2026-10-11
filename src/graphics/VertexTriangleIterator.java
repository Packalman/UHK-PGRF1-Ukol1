package graphics;

import model.Point;
import model.VertexTriangle;

public class VertexTriangleIterator {
    public static void iterate(VertexTriangle triangle, TriangleVertexDrawer drawer) {
        Point a = triangle.getVertexA().getPoint();
        Point b = triangle.getVertexB().getPoint();
        Point c = triangle.getVertexC().getPoint();

        int triangleArea = Math.abs(crossZ(vectorize(a, b), vectorize(a, c)))/2;

        TriangleIterator.iterate(
                a, b, c,
                (x, y)->{
                    Point p = new Point(x, y);
                    //barrycentric coordinates?
                    int aArea = Math.abs(crossZ(vectorize(p, b), vectorize(p, c)))/2;
                    int bArea = Math.abs(crossZ(vectorize(p, a), vectorize(p, c)))/2;
                    //int cArea = Math.abs(crossZ(vectorize(p, a), vectorize(p, b)))/2;
                    double weightA = aArea/(double)triangleArea;
                    double weightB = bArea/(double)triangleArea;
                    double weightC = 1.0-weightA-weightB;

                    drawer.draw(x, y, weightA, weightB, weightC);
                });
    }

    private static Point vectorize(Point toZero, Point toValue){
        return new Point(toValue.getX()-toZero.getX(), toValue.getY()-toZero.getY());
    }

    private static int crossZ(Point a, Point b){
        //https://en.wikipedia.org/wiki/Cross_product
        return a.getX()*b.getY()-a.getY()*b.getX();
    }

    public interface TriangleVertexDrawer{
        void draw(int x, int y, double weightA, double weightB, double weightC);
    }
}
