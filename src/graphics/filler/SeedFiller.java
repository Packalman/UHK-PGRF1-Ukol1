package graphics.filler;

public interface SeedFiller {
    boolean fillReplaceColor(int x1, int y1, int oldColor, int fillColor);
    boolean fillUntilBorder(int x1, int y1, int borderColor, int fillColor);
}
