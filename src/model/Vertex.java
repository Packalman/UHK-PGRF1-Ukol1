package model;

public class Vertex {
    private final Point point;
    private final double[] additionalProperties;

    public Vertex(int x, int y, double... additionalProperties){
        this(new Point(x, y), additionalProperties);
    }

    public Vertex(Point point, double... additionalProperties) {
        this.point = point;
        this.additionalProperties = additionalProperties;
    }

    public Point getPoint() {
        return point;
    }

    public double getProperty(int index) {
        return additionalProperties[index];
    }
}
