package ArduinoInterface;

/**
 * Created by Hermann on 03.08.2018.
 */
public interface CommandSenderInterface {
    public void sendCommand(String commandString);
    public void sendBoardArray(int[] boardArray);
}
