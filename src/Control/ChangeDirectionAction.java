package Control;

import model.Direction;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Hermann on 02.08.2018.
 */
public class ChangeDirectionAction extends AbstractAction {
    private Direction dir;

    public ChangeDirectionAction (Direction dir){
        this.dir = dir;
    }

    public void actionPerformed(ActionEvent event){
        if(LED_Control.currentGame.equals(Game.SNAKE) && dir != null) {
            SnakeGame.snake1Direction = dir;
            SnakeGame.snake2Direction = dir;
        }
        if(LED_Control.currentGame.equals(Game.PONG)){
            PongGame.player1Direction = dir;
            if(LED_Control.numberOfPlayers == 2){
                PongGame.player2Direction = dir;
            }
        }
    }
}
