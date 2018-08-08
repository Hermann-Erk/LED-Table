package view;

import Control.Game;
import Control.LED_Control;
import model.Board;
import model.Direction;
import Control.ChangeDirectionAction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hermann on 02.08.2018.
 */
public class ButtonGrid extends JPanel {
    private JButton squares[][] = new JButton[14][14];
    private Board board;
    private int boardHeight;

    public ButtonGrid(Dimension SCREEN_SIZE, int CONTROL_PANEL_HEIGHT, Board board){
        this.board = board;
        boardHeight = SCREEN_SIZE.height - CONTROL_PANEL_HEIGHT - 80;
        this.initializeWindow(CONTROL_PANEL_HEIGHT);

        if(LED_Control.currentGame.equals(Game.SNAKE)) {
            this.setKeyBindings_SNAKE();
        }

        if(LED_Control.currentGame.equals(Game.PONG)) {
            this.setKeyBindings_PONG();
        }

        this.initializeContent();
    }

    public void initializeWindow(int CONTROL_PANEL_HEIGHT){
        this.setLayout(new GridLayout(14,14));
        this.setBounds(0, CONTROL_PANEL_HEIGHT, boardHeight, boardHeight);
        this.setVisible(true);
    }

    public void initializeContent(){

        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                squares[i][j] = new JButton();

                squares[i][j].setBackground(board.getColorToPixel(j,13-i));

                this.add(squares[i][j]);
            }
        }


    }


    public void paintComponent(Graphics og){
        super.paintComponent(og);
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                squares[i][j].setBackground(board.getColorToPixel(j,13-i));
            }
        }

    }

    private void setKeyBindings_SNAKE(){
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "goUp");
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "goDown");
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "goRight");
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "goLeft");
        this.getActionMap().put("goUp", new ChangeDirectionAction(Direction.UP));
        this.getActionMap().put("goDown", new ChangeDirectionAction(Direction.DOWN));
        this.getActionMap().put("goRight", new ChangeDirectionAction(Direction.RIGHT));
        this.getActionMap().put("goLeft", new ChangeDirectionAction(Direction.LEFT));
    }

    private void setKeyBindings_PONG(){
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressedLeft");
        this.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "releasedLeft");
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressedRight");
        this.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "releasedRight");
        this.getActionMap().put("pressedLeft", new ChangeDirectionAction(Direction.LEFT));
        this.getActionMap().put("releasedLeft", new ChangeDirectionAction(null));
        this.getActionMap().put("pressedRight", new ChangeDirectionAction(Direction.RIGHT));
        this.getActionMap().put("releasedRight", new ChangeDirectionAction(null));
    }


}