package view;

import controller.SubController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Toolbar extends JToolBar {
    private final JComboBox<String> drawModeSelect;

    public Toolbar(){
        setLayout(new BorderLayout());
        add(new JLabel("Toolbar"), BorderLayout.WEST);

        drawModeSelect = new JComboBox<>();

        JPanel rightSide = new JPanel(new FlowLayout());
        rightSide.add(new JLabel("Drawer:"));
        rightSide.add(drawModeSelect);

        add(rightSide, BorderLayout.EAST);
    }

    public void init(ComboBoxSelectChangeHandler drawModeSelectHandler, Collection<String> items){
        for (String item : items)
            drawModeSelect.addItem(item);

        drawModeSelect.addItemListener(
                (_) -> drawModeSelectHandler.selectionChanged(drawModeSelect.getSelectedIndex()));
    }

    public interface ComboBoxSelectChangeHandler {
        void selectionChanged(int index);
    }
}
