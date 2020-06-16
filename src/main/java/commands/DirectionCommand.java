package commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DirectionCommand extends AbstractCommand {

    private static Command directionCommand = new DirectionCommand();

    public static Command getInstance() {
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
