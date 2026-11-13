package view.sidebar.ui;

import javax.swing.*;
import java.awt.*;

public class SidebarComponent {
    private final Component leftComponent;
    private Component rightComponent;

    public SidebarComponent(String name){
        this.leftComponent = new JLabel(name);
    }

    public SidebarComponent(String name, Component right){
        this(name);
        init(right);
    }

    protected final void init(Component right){
        this.rightComponent = right;
    }

    public Component getLeftComponent() {
        return leftComponent;
    }

    public Component getRightComponent() {
        return rightComponent;
    }
}
