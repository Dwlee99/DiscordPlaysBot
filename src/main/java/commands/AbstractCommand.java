package commands;

import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

public abstract class AbstractCommand implements Command {

    public void run(GenericMessageReactionEvent event){
        if(event instanceof MessageReactionAddEvent){
            run((MessageReactionAddEvent) event);
        }
        else if(event instanceof MessageReactionRemoveEvent){
            run((MessageReactionRemoveEvent) event);
        }
    }

    abstract void run(MessageReactionAddEvent event);

    abstract void run(MessageReactionRemoveEvent event);

}
