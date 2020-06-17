package gamepad;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class Gamepad{

    private static final int DELAY = 0;


    private static Robot robot;
    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }



    public static void pressKey(char key) {

        KeyStroke ks = KeyStroke.getKeyStroke(Character.toUpperCase(key), 0);
        int keyCode = ks.getKeyCode();
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    public static void pressKey(String key) {

        KeyStroke ks = KeyStroke.getKeyStroke(key);
        int keyCode = ks.getKeyCode();
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

}
