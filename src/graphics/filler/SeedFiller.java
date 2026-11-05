package graphics.filler;

public interface SeedFiller {
    boolean fillReplaceBackground(int x1, int y1, int targetColor, int fillColor);
    boolean fillUntilBorder(int x1, int y1, int borderColor, int fillColor);
}
