package Control;

import ArduinoInterface.CommandSenderInterface;
import model.*;
import view.DebugGUI;

import java.awt.*;
import java.util.ArrayList;


/**
 * Created by Hermann on 07.08.2018.
 */
public class PongGame {
    private static CommandSenderInterface commandSenderArduino;
    public static Direction player1Direction = null;
    public static Direction player2Direction = null;
    public static PongPlayer playerDOWN;
    public static PongPlayer playerUP;
    public static Board board = LED_Control.board;

    public static void runPong() {
        LED_Control.currentGame = Game.PONG;
        //LED_Control.gui.getButtonGrid().updateKeyBindings();

        //BoardTransformerWS2812B.printIntArray(BoardTransformerWS2812B.transformToIntArray(board.board));
        CommandSenderThread commando = new CommandSenderThread(board, commandSenderArduino);
        Thread thread = new Thread(commando);
        thread.start();
        while(true) {
            Pixel ballPixel = new Pixel(7, 7, Color.RED);
            PongBall ball = new PongBall(1.75f, 1.75f, 0.0002f, 0.0005f);
            board.addGameObject(ball);

            ArrayList<Pixel> player1Pixel = new ArrayList<>();
            player1Pixel.add(new Pixel(4, 0));
            player1Pixel.add(new Pixel(5, 0));
            player1Pixel.add(new Pixel(6, 0));
            player1Pixel.add(new Pixel(7, 0));

            playerDOWN = new PongPlayer(player1Pixel, Color.GREEN);
            board.addGameObject(playerDOWN);

            ArrayList<Pixel> player2Pixel = new ArrayList<>();
            player2Pixel.add(new Pixel(4, 13));
            player2Pixel.add(new Pixel(5, 13));
            player2Pixel.add(new Pixel(6, 13));
            player2Pixel.add(new Pixel(7, 13));

            playerUP = new PongPlayer(player2Pixel, Color.MAGENTA);

            if(LED_Control.numberOfPlayers == 2){
                board.addGameObject(playerUP);
            }

            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Pixel culprit = board.checkPongBoardForCollisions();
                if (culprit != null) {
                    System.out.println("GAME OVER");
                    LED_Control.playDeathAnimation(culprit);
                    break;
                }
                ;

                board.gameObjects.get(0).move(Direction.UP);

                board.gameObjects.get(1).move(player1Direction);

                if(LED_Control.numberOfPlayers==2) {
                    board.gameObjects.get(2).move(player2Direction);
                }

                board.updatePongBoard();
                //board.updatePongBoardSmooth();

                if (LED_Control.enableDebugGUI) {
                    LED_Control.updateDebugGUI();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static void setCommandSender(CommandSenderInterface cmdSender){
        commandSenderArduino = cmdSender;
    }
}
