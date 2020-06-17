package gamepad;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class Gamepad{

    private static final int DELAY = 1000;


    private static Robot robot;
    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Presses the keys in the string separated by white space. For example, "shift b c" will press, shift, b, then c
     * and the release shift, b, then c. After pressing and releasing the keys, the robot will wait DELAY before
     * accepting new key presses.
     * @param key a string, delimited by spaces, of the keys to press
     */
    public synchronized static void pressKey(String key) {
        String[] keyPresses = key.toUpperCase().split("\\s");
        int[] keyCodes = new int[keyPresses.length];


        int curIndex = 0;
        KeyStroke curKeyStroke;
        int curKeyCode;

        for(String curKey : keyPresses)
        {
            curKeyStroke = KeyStroke.getKeyStroke(curKey);
            curKeyCode = curKeyStroke.getKeyCode();
            keyCodes[curIndex] = curKeyCode;
            robot.keyPress(curKeyCode);
            curIndex++;
        }

        for (int keyCode : keyCodes)
        {
            robot.keyRelease(keyCode);
        }

        robot.delay(DELAY);
    }

}
