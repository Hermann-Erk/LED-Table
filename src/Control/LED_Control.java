package Control;

import ArduinoInterface.*;
import ControllerInterface.ControllerInterface;
import PixelArtAndAnimation.PixelArtIntro;
import gnu.io.SerialPort;
import model.*;
import view.DebugGUI;

import java.awt.*;
import java.io.InputStream;
import java.io.OutputStream;

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
    public static boolean controller1IsConnected;

    public static final boolean enableDebugGUI = true;
    public static DebugGUI gui;

    public static int numberOfPlayers = 2;
    public static boolean reversePlayer2Controls = true;

    public static Board board = new Board();

    public static Game currentGame = null;


    public static void main(String[] args){
        initializeSerialConnections();
        initializeCommandSenders();

        SnakeGame.setCommandSender(commandSenderArduino);
        PongGame.setCommandSender(commandSenderArduino);
        PixelArtIntro.setCommandSender(commandSenderArduino);

        currentGame = Game.NONE;

        //ControllerInterface.controllerTest();
        ControllerInterface.connectJoystick();

        if(enableDebugGUI) {
            gui = new DebugGUI(board);
        }



        //PixelArtIntro.runIntro();
        SnakeGame.runSnake();
        //PongGame.runPong();
    }



    public static void playDeathAnimation(Pixel culprit){
        board.clearBoard();
        ExplosionCircle circle = new ExplosionCircle(culprit.color);
        for(int r = 0; r < 20; r++){
            for(int phi = 5; phi < 360; phi = phi + 10){
                Pixel pix = null;
                double phiRadiant = phi*Math.PI/180;
                if(r%2==0) {
                    pix = new Pixel((int) (culprit.column + r * Math.cos(phiRadiant)),
                            (int) (culprit.row + r * Math.sin(phiRadiant)),
                            culprit.color);
                }else{
                    pix = new Pixel((int) (culprit.column + r * Math.cos(phiRadiant)),
                            (int) (culprit.row + r * Math.sin(phiRadiant)),
                            Color.red);
                }
                 circle.addPixel(pix);
            }
            board.addGameObject(circle);
            board.updateBoard();
            updateDebugGUI();
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            board.clearBoard();
            circle.clear();
        }
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

    public static void updateDebugGUI(){
        if(enableDebugGUI){
            gui.getButtonGrid().invalidate();
            gui.getButtonGrid().repaint();
        }
    }

}
