package commands;

public class CommandFactory {

    public static Command getDirectionCommand(){
        return DirectionCommand.getInstance();
    }
    public static Command getStartGameCommand(){
        return StartGameCommand.getInstance();
    }

    public static Command getCommandByType(CType cType) {
        switch(cType) {
            case DIRECTION:
                return getDirectionCommand();
            case STARTGAME:
                return getStartGameCommand();
        }
        return null;
    }

}
