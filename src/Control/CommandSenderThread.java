package Control;

import ArduinoInterface.BoardTransformerWS2812B;
import ArduinoInterface.CommandSenderInterface;
import model.Board;

/**
 * Created by Hermann on 06.08.2018.
 */
public class CommandSenderThread implements Runnable {
    private Board board;
    private CommandSenderInterface commandSender;

    public CommandSenderThread(Board board, CommandSenderInterface commandSender){
        this.board = board;
        this.commandSender = commandSender;
    }

    public void run(){
        while(true) {
            commandSender.sendBoardArray(BoardTransformerWS2812B.transformToIntArray(board.board));
            //System.out.println("test");
            try {
                //The arduino has problems reading the buffer in time if too many commands are sent
                Thread.sleep(10); // TODO adjust timing
            } catch (Exception e) {

            }
        }
    }

}
