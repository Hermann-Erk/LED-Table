package Control;

import ArduinoInterface.*;
import gnu.io.SerialPort;
import model.*;
import view.DebugGUI;

import java.awt.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Hermann on 02.08.2018.
 */
public abstract class LED_Control {
    private static SerialConnectionHandler connectionHandlerArduino = new SerialConnectionHandler();
    private static SerialPort serialPortArduino;
    private static InputStream inputStreamArduino;
    private static OutputStream outputStreamArduino;
    private static CommandSenderInterface commandSenderArduino;
    public static boolean arduinoIsConnected;


    static Direction snake1Direction = Direction.UP;
    static Direction snake2Direction = Direction.DOWN;
    static Board board;
    static boolean foodAvailable = false;

    public static void main(String[] args){
        //TODO everything.
        initializeSerialConnections();
        initializeCommandSenders();
        runSnake();
    }

    public static void runSnake(){
        board = new Board();


        ArrayList<Pixel> snake1Pixel = new ArrayList<>();
        snake1Pixel.add(new Pixel(7, 3));
        snake1Pixel.add(new Pixel(7, 2));
        snake1Pixel.add(new Pixel(7, 1));

        board.addGameObject(new Snake(snake1Pixel, Color.GREEN, Direction.UP));
        board.updateBoard();

        DebugGUI gui = new DebugGUI(board);
        //BoardTransformerWS2812B.printIntArray(BoardTransformerWS2812B.transformToIntArray(board.board));
        CommandSenderThread commando = new CommandSenderThread(board, commandSenderArduino);
        Thread thread = new Thread(commando);
        thread.start();

        while(true){
            try {
                Thread.sleep(260);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            board.gameObjects.get(0).move(snake1Direction);

            if (board.gameObjects.size() > 1) {
                board.gameObjects.get(1).move(snake2Direction);
            }

            spawnFood();
            board.didSnakeEat();
            if(board.isGameOver_SNAKE()){
                System.out.println("GAME OVER");
            }

            board.updateBoard();
            gui.getButtonGrid().invalidate();
            gui.getButtonGrid().repaint();

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

    public static void setFoodEaten(){
        foodAvailable = false;
    }

    public static void initializeSerialConnections(){
        try {
            serialPortArduino = connectionHandlerArduino.initializeConnection(Constants.portArduino);
            inputStreamArduino = connectionHandlerArduino.getInputStream();
            outputStreamArduino = connectionHandlerArduino.getOutputStream();
            arduinoIsConnected = true;
        } catch (Exception e){
            // If there is no connection, a NullPointrException is caught
            //e.printStackTrace();
            arduinoIsConnected = false;
        }
    };

    public static void disconnect() {
        try {
            connectionHandlerArduino.closeConnection();
        } catch (NullPointerException e) {
            System.out.println("WARNING: System tried to disconnect from (front) Arduino, but there was no connection to close.");
        }
    }

    public static void reConnect(){
        disconnect();
        initializeSerialConnections();
    }

    public static void initializeCommandSenders(){
        if (outputStreamArduino != null && outputStreamArduino != null) {
            commandSenderArduino = new CommandSender(outputStreamArduino, inputStreamArduino);
            System.out.println("The command sender (front) has been initialized.");
        } else {
            commandSenderArduino = new CommandSenderDummy();
            System.out.println("No command sender (front) has been initialized.");
        }

    }

}
