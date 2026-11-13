package view.sidebar.ui;

import javax.swing.*;
import java.awt.*;

public class ColorSelectComponent extends SidebarComponent {
    public ColorSelectComponent(String name, Color selectedColor, ColorSelectedHandler colorSelectedHandler){
        super(name);

        Box rightSide = Box.createHorizontalBox();
        JButton button = new JButton();
        rightSide.add(button);
        JPanel colorPreview = new JPanel();

        colorPreview.setMaximumSize(new Dimension(16, 16));
        colorPreview.setMinimumSize(new Dimension(16, 16));
        colorPreview.setPreferredSize(new Dimension(16, 16));
        colorPreview.setBackground(selectedColor);

        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(new JLabel("Choose"));
        buttonBox.add(Box.createHorizontalStrut(4));
        buttonBox.add(colorPreview);
        button.add(buttonBox);

        button.addActionListener((_)->{
            var color = JColorChooser.showDialog(null, name + " dialog", Color.BLACK);
            if(color!=null){
                colorPreview.setBackground(color);
                colorSelectedHandler.select(color);
            }
        });

        init(rightSide);
    }

    public interface ColorSelectedHandler{
        void select(Color color);
    }
}
