package ControllerInterface;

import Control.Game;
import Control.LED_Control;
import Control.PongGame;
import Control.SnakeGame;
import model.Direction;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.awt.event.ActionEvent;

/**
 * Created by Hermann on 15.08.2018.
 */
public class ControllerInterface {

    public static void controllerTest() {
        Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (int i = 0; i < ca.length; i++) {
            if(ca[i].getType() == Controller.Type.STICK){
                System.out.println("Joystick found.");
                ca[i].poll();
                Component[] components = ca[i].getComponents();
                StringBuffer buffer = new StringBuffer();
                for(int j=0;j<components.length;j++) {
                    if(j>0) {
                        buffer.append("\n");
                    }
                    buffer.append(components[j].getName() + " / " + components[j].getIdentifier());
                    buffer.append(": ");
                    if(components[j].isAnalog()) {
                        buffer.append(components[j].getPollData());
                    } else {
                        if(components[j].getPollData()==1.0f) {
                            buffer.append("On");
                        } else {
                            buffer.append("Off");
                        }
                    }
                }
                System.out.println(buffer.toString());
            }
            /* Get the name of the controller */
            System.out.println(ca[i].getName() + "  " + ca[i].getType().toString());
        }

    }

    public static void connectJoystick() {
        Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();

        Controller joy1 = null;
        Controller joy2 = null;

        int controllerCount = 0;
        for (int i = 0; i < ca.length; i++) {
            if(ca[i].getType() == Controller.Type.STICK){
                joy1 = ca[i];
                controllerCount = i;
                break;
            }
            /* Get the name of the controller */
            //System.out.println(ca[i].getName() + "  " + ca[i].getType().toString());
        }

        if(joy1 != null){
            ControllerPollThread controllerPoll = new ControllerPollThread(joy1, 1);
            Thread thread = new Thread(controllerPoll);
            thread.start();
            System.out.println("Controller for Player 1 connected.");

            for (int k = controllerCount+1; k < ca.length; k++) {
                if(ca[k].getType() == Controller.Type.STICK){
                    joy2 = ca[k];
                    break;
                }
            }
        }

        if(joy2 != null){
            ControllerPollThread controllerPoll = new ControllerPollThread(joy2, 2);
            Thread thread = new Thread(controllerPoll);
            thread.start();
            System.out.println("Controller for Player 2 connected.");
        }


    }

    public static void changeDirection(Direction dir, int player){
        if(LED_Control.currentGame.equals(Game.SNAKE) && dir != null) {
            if(player == 1) {
                SnakeGame.snake1Direction = dir;
            }
            if(LED_Control.numberOfPlayers == 2 && player == 2) {
                if(LED_Control.reversePlayer2Controls) {
                    SnakeGame.snake2Direction = dir.getOpposite();
                }else{
                    SnakeGame.snake2Direction = dir;
                }
            }
        }
        if(LED_Control.currentGame.equals(Game.PONG)){
            if(player == 1){
                PongGame.player1Direction = dir;
            }

            if(LED_Control.numberOfPlayers == 2 && player == 2){
                if (LED_Control.reversePlayer2Controls){
                    PongGame.player2Direction = dir.getOpposite();
                }else{
                    PongGame.player2Direction = dir;
                }
            }
        }
    }


}
