package Control;

import ArduinoInterface.CommandSenderInterface;
import model.*;
import view.DebugGUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Control.LED_Control.board;

/**
 * Created by Hermann on 07.08.2018.
 */
public abstract class SnakeGame {
    private static CommandSenderInterface commandSenderArduino;

    static boolean foodAvailable = false;
    public static Direction snake1Direction = Direction.UP;
    public static Direction snake2Direction = Direction.DOWN;

    public static Board board = LED_Control.board;


    public static void runSnake(){
        LED_Control.currentGame = Game.SNAKE;
        //LED_Control.gui.getButtonGrid().updateKeyBindings();

        //BoardTransformerWS2812B.printIntArray(BoardTransformerWS2812B.transformToIntArray(board.board));
        CommandSenderThread commando = new CommandSenderThread(board, commandSenderArduino);
        Thread thread = new Thread(commando);
        thread.start();

        while(true) {
            ArrayList<Pixel> snake1Pixel = new ArrayList<>();
            snake1Pixel.add(new Pixel(7, 3));
            snake1Pixel.add(new Pixel(7, 2));
            snake1Pixel.add(new Pixel(7, 1));

            board.addGameObject(new Snake(snake1Pixel, Color.GREEN, Direction.UP));
            snake1Direction = Direction.UP;
            foodAvailable = false;

            ArrayList<Pixel> snake2Pixel = new ArrayList<>();
            snake2Pixel.add(new Pixel(6, 10));
            snake2Pixel.add(new Pixel(6, 11));
            snake2Pixel.add(new Pixel(6, 12));

            if(LED_Control.numberOfPlayers == 2) {
                board.addGameObject(new Snake(snake2Pixel, Color.MAGENTA, Direction.DOWN));
            }

            snake2Direction = Direction.DOWN;

            board.updateBoard();


            while (true) {
                try {
                    Thread.sleep(230);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                board.gameObjects.get(0).move(snake1Direction);

                if (board.gameObjects.size() > 1) {
                    board.gameObjects.get(1).move(snake2Direction);
                }

                spawnFood();
                board.didSnakeEat();

                Pixel culprit = board.isGameOver_SNAKE();
                if (culprit != null) {
                    System.out.println("GAME OVER");
                    LED_Control.playDeathAnimation(culprit);
                    break;
                }

                board.updateBoard();

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

    public static void spawnFood(){
        Random rand = new Random();

        int foodColumn = rand.nextInt(12) + 1;
        int foodRow = rand.nextInt(12) + 1;

        if (rand.nextInt(100)+1 <= Constants.foodSpawn && !foodAvailable){
            boolean collidesWithSnake = false;

            for(Pixel pix : board.getSnakePixel()){
                if(foodColumn == pix.column && foodRow == pix.row){
                    collidesWithSnake = true;
                }
            }

            if(!collidesWithSnake){
                board.addGameObject(new SnakeFood(new Pixel(foodColumn, foodRow)));
                foodAvailable = true;
            }
        }
    }

    public static void setCommandSender(CommandSenderInterface cmdSender){
        commandSenderArduino = cmdSender;
    }

    public static void setFoodEaten(){
        foodAvailable = false;
    }
}
