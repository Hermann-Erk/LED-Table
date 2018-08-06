package ArduinoInterface;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Hermann on 03.08.2018.
 */
public class CommandSender implements  CommandSenderInterface{
    private OutputStream outputStream;
    private InputStream inputStream; //probably not used

    public CommandSender(OutputStream output, InputStream input){
        this.outputStream = output;
        this.inputStream = input;
    }

    public synchronized void sendCommand(String commandString){
        try {
            String commandStringPlusLineBreak = commandString + "\n";
            outputStream.write(commandStringPlusLineBreak.getBytes());
            System.out.println("Java to Arduino: " + commandString);
        }catch (Exception e){
            System.out.println("WARNING: Command " + commandString +
                    " has not been sent!");
        }
        try {
            //The arduino has problems reading the buffer in time if too many commands are sent
            Thread.sleep(1000); // TODO adjust timing
        }catch (Exception e){

        }
    }

    public synchronized void sendBoardArray(int[] boardArray){
        for(int i: boardArray){
            try {
                outputStream.write(i);
                //System.out.println("Java to Arduino: " + i);
            }catch (Exception e){
                //System.out.println("WARNING: Command " + commandString +
                //        " has not been sent!");
            }
        }
    }

}
