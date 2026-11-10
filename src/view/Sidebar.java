package view;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    private final Panel panel;

    public Sidebar(int width, int height){
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        setPreferredSize(new Dimension(width, height));

        this.panel = new Panel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (int i = 1; i <= 100; i++) {
            var label = new JLabel(i + " label");
            panel.add(label);
        }

        var scrollPane = new ScrollPane();
        scrollPane.add(panel);

        add(scrollPane);

        scrollPane.setPreferredSize(getPreferredSize());
    }

    public void clearPanel(){
        panel.removeAll();
    }

    public void addToPanel(Component component){
        panel.add(component);
    }
}
