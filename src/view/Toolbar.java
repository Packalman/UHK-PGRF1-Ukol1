package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class Toolbar extends JToolBar {
    private final JButton selectButton;
    private final JButton lineButton;
    private final JButton polygonButton;
    private final JButton rectButton;//For textured rectangles, RGB..
    private final JButton seedFillButton;
    private final JButton cutButton;
    private final JButton clearButton;

    private final Map<ToolbarButtonType, JButton> typeToJButton;
    private ToolbarButtonType previousButton = ToolbarButtonType.SELECT;

    public Toolbar(){
        setLayout(new BorderLayout());
        this.setFloatable(false);
        JPanel toolbarButtonContainer = new JPanel(new FlowLayout());

        this.selectButton = new JButton("Select");
        this.lineButton = new JButton("Lines");
        this.polygonButton = new JButton("Polygons");
        this.rectButton = new JButton("Rectangles");
        this.seedFillButton = new JButton("Seed fill");
        this.cutButton = new JButton("Cut");
        this.clearButton = new JButton("Clear");

        typeToJButton = new LinkedHashMap<>();
        typeToJButton.put(ToolbarButtonType.SELECT, selectButton);
        typeToJButton.put(ToolbarButtonType.LINES, lineButton);
        typeToJButton.put(ToolbarButtonType.POLYGON, polygonButton);
        typeToJButton.put(ToolbarButtonType.RECTANGLES, rectButton);
        typeToJButton.put(ToolbarButtonType.SEED_FILL, seedFillButton);
        typeToJButton.put(ToolbarButtonType.CUT, cutButton);
        typeToJButton.put(ToolbarButtonType.CLEAR, clearButton);

        for (var s : typeToJButton.entrySet()){
            toolbarButtonContainer.add(s.getValue());
        }

        add(toolbarButtonContainer, BorderLayout.WEST);
    }

    public void init(ToolbarButtonListener listener, Canvas canvas){
        for (var s : typeToJButton.entrySet()){
            s.getValue().addActionListener((_)->handleButtonPressed(listener, s.getKey()));
        }

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_1 -> handleButtonPressed(listener, ToolbarButtonType.SELECT);
                    case KeyEvent.VK_2 -> handleButtonPressed(listener, ToolbarButtonType.LINES);
                    case KeyEvent.VK_3 -> handleButtonPressed(listener, ToolbarButtonType.POLYGON);
                    case KeyEvent.VK_4 -> handleButtonPressed(listener, ToolbarButtonType.RECTANGLES);
                    case KeyEvent.VK_5 -> handleButtonPressed(listener, ToolbarButtonType.SEED_FILL);
                    case KeyEvent.VK_6 -> handleButtonPressed(listener, ToolbarButtonType.CUT);
                    case KeyEvent.VK_7, KeyEvent.VK_C -> handleButtonPressed(listener, ToolbarButtonType.CLEAR);
                }
            }
        });

        handleButtonPressed(listener, previousButton);
    }

    private void handleButtonPressed(ToolbarButtonListener listener, ToolbarButtonType buttonType){
        if(buttonType!=ToolbarButtonType.CLEAR){
            if(previousButton!=null)
                typeToJButton.get(previousButton).setEnabled(true);
            typeToJButton.get(buttonType).setEnabled(false);
            previousButton = buttonType;
        }

        listener.click(buttonType);
    }

    public interface ToolbarButtonListener {
        void click(ToolbarButtonType button);
    }

    public enum ToolbarButtonType {
        SELECT,
        LINES,
        POLYGON,
        RECTANGLES,
        SEED_FILL,
        CUT,
        CLEAR
    }
}
