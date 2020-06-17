package commands;

/**
 * CommandFactory is a class meant to be used to access command objects.
 */
public class CommandFactory {

    /**
     * @return the Start Game Command
     */
    public static Command getStartGameCommand(){
        return StartGameCommand.getInstance();
    }

    /**
     * @return the Queue Press Command
     */
    public static Command getQueuePressCommand() { return QueuePressCommand.getInstance(); }

    /**
     * @param cType the type of command to be found
     * @return the command associated with cType
     */
    public static Command getCommandByType(CType cType) {
        switch(cType) {
            case QUEUE_PRESS:
                return getQueuePressCommand();
            case START_GAME:
                return getStartGameCommand();
        }
        return null;
    }

}
