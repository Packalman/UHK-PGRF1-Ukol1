package view.sidebar;

import graphics.Raster;
import view.sidebar.ui.ColorSelectComponent;
import view.sidebar.ui.OptionSelectComponent;
import view.sidebar.ui.ChoosePathComponent;
import view.sidebar.ui.SidebarComponent;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolygonSettings extends SidebarSettings{
    private PolygonFillType fillType = PolygonFillType.NONE;
    private Color fillColor = Color.YELLOW;
    private Color outlineColor = Color.BLACK;
    private Path imagePath;
    private Raster<Integer> pattern;

    @Override
    public SidebarComponent[] getComponents() {
        List<SidebarComponent> components = new ArrayList();
        components.add(
                new ColorSelectComponent("Outline color", outlineColor, (c)->outlineColor=c));
        components.add(
                new OptionSelectComponent("Fill type", fillType.index, this::handleFillTypeChange,
                        Arrays.stream(PolygonFillType.values()).map(x->x.name).toArray(String[]::new)));
        switch (fillType){
            case COLOR -> components.add(new ColorSelectComponent("Fill color", fillColor, (c)->fillColor=c));
            case TEXTURE -> components.add(new ChoosePathComponent("Choose path", (_)->{}));
        }
        return components.toArray(SidebarComponent[]::new);
    }

    private void handleFillTypeChange(int i, String s) {
        this.fillType = PolygonFillType.values()[i];
        settingsChangedNotify();
    }

    public PolygonFillType getFillType() {
        return fillType;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public Path getImagePath() {
        return imagePath;
    }

    public Raster<Integer> getPattern() {
        return pattern;
    }

    public enum PolygonFillType{
        NONE("None",0),
        COLOR("Color",1),
        PATTERN("Pattern",2),
        TEXTURE("Texture",3);

        public final int index;
        private final String name;

        PolygonFillType(String name, int index){
            this.name = name;
            this.index = index;
        }
    }
}
