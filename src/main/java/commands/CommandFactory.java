package commands;

/**
 * CommandFactory is a class meant to be used to access command objects.
 */
public class CommandFactory {

    /**
     * @return the Direction Command
     */
    public static Command getDirectionCommand(){
        return DirectionCommand.getInstance();
    }

    /**
     * @return the Start Game Command
     */
    public static Command getStartGameCommand(){
        return StartGameCommand.getInstance();
    }

    /**
     * @param cType the type of command to be found
     * @return the command associated with cType
     */
    public static Command getCommandByType(CType cType) {
        switch(cType) {
            case DIRECTION:
                return getDirectionCommand();
            case START_GAME:
                return getStartGameCommand();
        }
        return null;
    }

}
