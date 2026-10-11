package model;

public class VertexTriangle {
    private final Vertex vertexA;
    private final Vertex vertexB;
    private final Vertex vertexC;

    public VertexTriangle(Vertex vertexA, Vertex vertexB, Vertex vertexC) {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.vertexC = vertexC;
    }

    public Vertex getVertexA() {
        return vertexA;
    }

    public Vertex getVertexB() {
        return vertexB;
    }

    public Vertex getVertexC() {
        return vertexC;
    }
}
