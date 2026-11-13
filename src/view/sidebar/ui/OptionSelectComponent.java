package view.sidebar.ui;

import javax.swing.*;
import java.awt.event.ItemEvent;

public class OptionSelectComponent extends SidebarComponent {
    private final JComboBox<String> comboBox;
    private final OptionSelectedHandler handler;

    public OptionSelectComponent(String name, int selectedIndex, OptionSelectedHandler handler, String... options){
        super(name);
        this.comboBox = new JComboBox<>(options);
        this.handler = handler;

        comboBox.setSelectedIndex(selectedIndex);
        comboBox.addItemListener(this::handleItemSelected);

        init(comboBox);
    }

    private void handleItemSelected(ItemEvent e) {
        handler.select(comboBox.getSelectedIndex(), (String) comboBox.getSelectedItem());
    }

    public interface OptionSelectedHandler{
        void select(int optionIndex, String optionName);
    }
}
