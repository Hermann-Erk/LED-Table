package ControllerInterface;

import model.Direction;
import net.java.games.input.Component;
import net.java.games.input.Controller;

/**
 * Created by Hermann on 15.08.2018.
 */
public class ControllerPollThread implements Runnable {
    public Controller joystick;
    public int player = 0;

    public ControllerPollThread(Controller newJoystick, int player){
        this.joystick = newJoystick;
        this.player = player;
    }

    @Override
    public void run() {
        while (true) {
            joystick.poll();
            Component[] components = joystick.getComponents();
            boolean directionYactive = false;
            boolean directionXactive = false;

            for (Component component : components) {
                float pollData = component.getPollData();
                if (component.getName().equals("Y-Achse")) {
                    if (pollData == 1.0f) {
                        ControllerInterface.changeDirection(Direction.DOWN, player);
                        directionYactive = true;
                    }
                    if (pollData == -1.0f) {
                        ControllerInterface.changeDirection(Direction.UP, player);
                        directionYactive = true;
                    }
                }
                if (component.getName().equals("X-Achse")) {
                    if (pollData == 1.0f) {
                        ControllerInterface.changeDirection(Direction.RIGHT, player);
                        directionXactive = true;
                    }
                    if (pollData == -1.0f) {
                        ControllerInterface.changeDirection(Direction.LEFT, player);
                        directionXactive = true;
                    }
                }
            }

            if(!directionXactive && !directionYactive){
                ControllerInterface.changeDirection(null,player);
            }


            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}

