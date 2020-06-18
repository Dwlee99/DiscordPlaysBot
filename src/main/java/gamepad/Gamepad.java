package gamepad;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class Gamepad{

    private static final int DELAY = 0;

    /**
     * This variable is used to indicate the necessary time a key must be pressed to allow it to be recognized by
     * hardware.
     */
    private static final int PRESS_WAIT = 20;

    /**
     * A keycode that does not do anything when pressed.
     */
    private static final int DUMMY_CODE = 0;

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

        for(String curKey : keyPresses) {
            curKeyStroke = KeyStroke.getKeyStroke(curKey);

            if(curKeyStroke != null) {
                curKeyCode = curKeyStroke.getKeyCode();
            }
            else{
                curKeyCode = DUMMY_CODE;
            }
            keyCodes[curIndex] = curKeyCode;
            try {
                robot.keyPress(curKeyCode);
            } catch (IllegalArgumentException ignore) {
            }
            curIndex++;
        }
        try {
            Thread.sleep(PRESS_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int keyCode : keyCodes) {
            try {
                robot.keyRelease(keyCode);
            } catch (IllegalArgumentException ignore) {
            }
        }

        robot.delay(DELAY);
    }

}
