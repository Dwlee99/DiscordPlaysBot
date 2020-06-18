package reaction_handling;

import commands.CType;
import commands.CommandFactory;
import gamepad.Gamepad;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

import static commands.CType.*;

public class ReactionHandler extends ListenerAdapter {

    private static long reactionMessage;
    private static HashMap<String, String> reactionMap = new HashMap<>();

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
        User user = event.getUser();
        if(user != null && !user.isBot() && event.getMessageIdLong() == reactionMessage) {
            new Thread(() -> handleMessageReaction(event)).start();
        }
    }

    private static final int WAIT_BETWEEN_ACTIONS = 3000;

    public void handleMessageReaction(MessageReactionAddEvent event) {
        for(String curEmoji : reactionMap.keySet()) {
            if(event.getReactionEmote().getAsReactionCode().equals(curEmoji)) {
                CommandFactory.getCommandByType(QUEUE_PRESS).run(reactionMap.get(curEmoji));
                try {
                    Thread.sleep(WAIT_BETWEEN_ACTIONS);
                } catch (InterruptedException ignored) {
                }
                event.retrieveMessage().queue((message -> {
                    message.removeReaction(curEmoji, event.getUser()).queue();
                }));
            }
        }
    }

    public static void setReactionMessage(TextChannel channel, long reactionID) {
        channel.retrieveMessageById(reactionID).queue(message -> {
            for(String key : reactionMap.keySet()){
                message.addReaction(key).queue();
            }
        });
        reactionMessage = reactionID;
    }
    public static void setReactionMap(HashMap<String, String> map) {
        reactionMap = map;
    }

}
