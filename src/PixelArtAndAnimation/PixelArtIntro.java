package PixelArtAndAnimation;

import ArduinoInterface.BoardTransformerWS2812B;
import ArduinoInterface.CommandSenderInterface;
import Control.CommandSenderThread;
import Control.Constants;
import Control.Game;
import Control.LED_Control;
import model.Board;
import view.DebugGUI;

/**
 * Created by Hermann on 13.08.2018.
 */
public class PixelArtIntro {
    private static CommandSenderInterface commandSenderArduino;
    public static Board board = LED_Control.board;

    public static void runIntro(){
        LED_Control.currentGame = Game.INTRO;

        //BoardTransformerWS2812B.printIntArray(BoardTransformerWS2812B.transformToIntArray(board.board));
        CommandSenderThread commando = new CommandSenderThread(board, commandSenderArduino);
        Thread thread = new Thread(commando);
        thread.start();
        boolean animationIsOver = false;
        int count = 0;

        Board frame1 = PixelArtFileParser.load();
        //board.board = frame1.board.clone();

        BoardTransformerWS2812B.printIntArray(BoardTransformerWS2812B.transformToIntArray(frame1.board));

        float factor = 0f;

            while(!animationIsOver) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(count < Constants.INTRO_LENGHT/4){
                    factor = (float) (count/(Constants.INTRO_LENGHT/4.0));
                }

                if(count > (int) (Constants.INTRO_LENGHT*(3.0/4))){
                    factor = (float) (1-((count-Constants.INTRO_LENGHT*(3.0/4))/(Constants.INTRO_LENGHT/4)));
                }

                if(count > Constants.INTRO_LENGHT/4 && count < (int) (Constants.INTRO_LENGHT*(3.0/4))){
                    factor = 1f;
                }


                board.scaleBrightness(frame1, factor);

                //if (count == 100){
                //   BoardTransformerWS2812B.printIntArray(BoardTransformerWS2812B.transformToIntArray(board.board));
                //}


                //board.updateBoard();
                LED_Control.updateDebugGUI();

                if(count > Constants.INTRO_LENGHT){
                    //count = 0;
                    animationIsOver = true;
                }
                count++;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    public static void setCommandSender(CommandSenderInterface cmdSender){
        commandSenderArduino = cmdSender;
    }
}
