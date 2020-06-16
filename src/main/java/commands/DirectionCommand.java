package commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * A command that will cause a direction press on the GamePad.
 */
public class DirectionCommand extends AbstractCommand {

    private static Command directionCommand = new DirectionCommand();

    /**
     * @return the singleton instance of the DirectionCommand
     */
    static Command getInstance() {
        return directionCommand;
    }

    @Override
    public void run(MessageReceivedEvent event) {
        String text = event.getMessage().getContentDisplay();
        if(text.toLowerCase().contains("upleft")){
            //upleft logic
        }
        else if(text.toLowerCase().contains("upright")){
            //upright logic
        }
        else if(text.toLowerCase().contains("downleft")){
            //downleft logic
        }
        else if(text.toLowerCase().contains("downright")){
            //downright logic
        }
        else if(text.toLowerCase().contains("up")){
            //up logic
        }
        else if(text.toLowerCase().contains("down")){
            //down logic
        }
        else if(text.toLowerCase().contains("left")){
            //left logic
        }
        else if(text.toLowerCase().contains("right")){
            //right logic
        }
    }
}
