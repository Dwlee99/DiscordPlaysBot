package handlers;

import commands.CommandFactory;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import static commands.CType.*;
import static handlers.MessageHandler.*;

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
        User user = event.getUser();

        Guild guild = event.getGuild();
        Member member = guild.getMember(user);

        if(event.getMessageIdLong() == controllerMessageID && hasRole(member, PLAYER)) {
            CommandFactory.getCommandByType(QUEUE_PRESS).run(event);
        }
        if(event.getMessageIdLong() == governmentMessageID && hasRole(member, HOST)) {
            CommandFactory.getCommandByType(SET_GOVERNMENT).run(event);
        }
    }

    public static void setControllerMessageID(long messageID) {
        controllerMessageID = messageID;
    }

    public static long getControllerMessageID() {
        return controllerMessageID;
    }

    public static void setGovernmentMessageID(long messageID) {
        governmentMessageID = messageID;
    }

    public static long getGovernmentMessageID(){
        return governmentMessageID;
    }
    


}
