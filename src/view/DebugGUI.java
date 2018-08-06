package view;

import model.Board;
import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hermann on 02.08.2018.
 */
public class DebugGUI extends JFrame {
    private ButtonGrid buttonGrid;
    // Screen size is in capital letters due to its constant behaviour
    private Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private final int CONTROL_PANEL_HEIGHT = 120;

    public DebugGUI(Board board) {
        super("LED Table: Debug GUI");
        this.initializeWindow();
        buttonGrid = new ButtonGrid(SCREEN_SIZE,CONTROL_PANEL_HEIGHT,board);
        this.getContentPane().add(buttonGrid);

        this.pack();
        this.setVisible(true);
    }

    private void initializeWindow(){
        this.setLayout(null);
        this.setPreferredSize(SCREEN_SIZE);
        // TODO Windowlistener?
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);	// for now...
        // TODO custom cursor?
    }

    public ButtonGrid getButtonGrid(){
        return buttonGrid;
    }
}
