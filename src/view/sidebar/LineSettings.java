package view.sidebar;

import view.sidebar.ui.ColorSelectComponent;
import view.sidebar.ui.SidebarComponent;

import java.awt.*;

public class LineSettings extends SidebarSettings{
    private Color color = Color.BLACK;

    @Override
    public SidebarComponent[] getComponents() {
        return new SidebarComponent[]{
                new ColorSelectComponent("Color", color, (c)->color=c)
        };
    }

    public Color getColor(){
        return this.color;
    }
}
