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
        LED_Control.snake1Direction = dir;
    }
}
