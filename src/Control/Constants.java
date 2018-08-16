package Control;

/**
 * Created by Hermann on 03.08.2018.
 */
public class Constants {
    public static final String portArduino = "COM14";
    public static final String portController1 = "COM11";
    public static int[] header = { 0xDE, 0xAD, 0xBE, 0xEF };

    public static int foodSpawn = 10; //chance in %
    public static float pongDT = 200f;
    public static float pongPlayerVelocity = 0.0006f;

    public static float PONG_BALL_VELOCITY_INCREMENT = 0.00005f;
    public static final int INTRO_LENGHT = 400;
}
