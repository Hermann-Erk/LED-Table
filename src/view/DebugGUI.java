package view;

import model.Board;
import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hermann on 02.08.2018.
 */
public class DebugGUI extends JFrame {
    private static ButtonGrid buttonGrid;
    private Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private final int CONTROL_PANEL_HEIGHT = 120;
    private static Board board;

    public DebugGUI(Board newBoard) {
        super("LED Table: Debug GUI");
        board = newBoard;
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

    public static void setBoard(Board newBoard){
        board = newBoard;
        buttonGrid.setBoard(newBoard);
    }

}
