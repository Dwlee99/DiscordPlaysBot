package reaction_handling;

import commands.CommandFactory;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static commands.CType.*;

public class ReactionHandler extends ListenerAdapter {

    private static long controllerMessageID;
    private static long governmentMessageID;

    @Override
    public void onGenericMessageReaction(GenericMessageReactionEvent event){
        User user = event.getUser();
        if(user == null || !user.isBot()) {
            new Thread(() -> handleMessageReaction(event)).start();
        }
    }

    public void handleMessageReaction(GenericMessageReactionEvent event) {
        if(event.getMessageIdLong() == controllerMessageID) {
            CommandFactory.getCommandByType(QUEUE_PRESS).run(event);
        }
        if(event.getMessageIdLong() == governmentMessageID) {
//            CommandFactory.getCommandByType(QUEUE_PRESS).run(event);
        }
    }

    public static void setControllerMessageID(long messageID) {
        controllerMessageID = messageID;
    }

    public static void setGovernmentMessageID(long messageID) {
        governmentMessageID = messageID;
    }
    


}
