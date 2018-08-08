package ArduinoInterface;

/**
 * Created by Hermann on 03.08.2018.
 */
public class CommandSenderDummy implements CommandSenderInterface{

    @Override
    public void sendCommand(String commandString) {
        System.out.println("WARNING: Command " + commandString + " has not been sent by the dummy.");
    }

    public void sendBoardArray(int[] boardArray){
        //System.out.println("WARNING: The boardArray has not been sent by the dummy.");
    }

}
