package reaction_handling;

import gamepad.Gamepad;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class ReactionHandler extends ListenerAdapter {

    private static long reactionMessage;
    HashMap<String, String> reactionMap = new HashMap<>();

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
        if(event.getMessageIdLong() == reactionMessage) {
            handleMessageReaction(event);
        }
    }

    public void handleMessageReaction(MessageReactionAddEvent event) {
        for(String curEmoji : reactionMap.keySet()) {
            if(event.getReactionEmote().getAsReactionCode().equals(curEmoji)) {
                Gamepad.pressKey(reactionMap.get(curEmoji));
                event.retrieveMessage().queue((message -> {
                    message.removeReaction(curEmoji, event.getUser()).queue();
                }));
            }
        }
    }

    public void setReactionMessage(long reactionID) {
        reactionMessage = reactionID;
    }
    public void setReactionMap(HashMap<String, String> map) {
        reactionMap = map;
    }

}
