package view;

import view.sidebar.LineSettings;
import view.sidebar.PolygonSettings;
import view.sidebar.SidebarSettings;
import view.sidebar.ui.SidebarComponent;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    private final JComponent grid;
    private final ScrollPane scrollPane;
    private Setting currentSetting = Setting.POLYGON;

    public Sidebar(int width, int height){
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setPreferredSize(new Dimension(width, height));

        grid = new JPanel(new GridLayout(0, 2, 10, 7));

        this.scrollPane = new ScrollPane();
        var flow = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 7));
        flow.add(grid);
        scrollPane.add(flow);
        add(scrollPane);
        scrollPane.setPreferredSize(getPreferredSize());

        for(var s : Setting.values()){
            s.init(this::update);
        }

        update();
    }

    public void update(){
        grid.removeAll();
        for (var component : currentSetting.getComponents()){
            grid.add(component.getLeftComponent());
            grid.add(component.getRightComponent());
        }
        grid.revalidate();
        grid.repaint();
    }

    public void changeSetting(Setting setting){
        this.currentSetting = setting;
        update();
    }

    public enum Setting{
        NONE(null),
        LINE(new LineSettings()),
        POLYGON(new PolygonSettings());

        private final SidebarSettings sidebarSettings;

        Setting(SidebarSettings settings){
            this.sidebarSettings = settings;
        }

        public SidebarComponent[] getComponents(){
            return this.sidebarSettings==null? new SidebarComponent[0] : this.sidebarSettings.getComponents();
        }

        public SidebarSettings getSidebarSettings(){
            return sidebarSettings;
        }

        private void init(SidebarSettings.SidebarSettingRebuiltHandler handler){
            if(sidebarSettings!=null)
                sidebarSettings.setSidebarSettingModifiedHandler(handler);
        }
    }
}
