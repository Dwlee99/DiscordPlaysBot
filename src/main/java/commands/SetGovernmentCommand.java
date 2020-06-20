package commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

public class SetGovernmentCommand extends AbstractCommand {

    private static boolean isDemocracy = false;

    private static int anarchyVotes = 0;
    private static int democracyVotes = 0;

    protected final static String ANARCHY_EMOJI = "\uD83D\uDD25";
    protected final static String DEMOCRACY_EMOJI = "\uD83D\uDCDD";

    @Override
    void run(MessageReactionAddEvent event) {
        if(event.getReactionEmote().getAsReactionCode().equals(ANARCHY_EMOJI)) {
            anarchyVotes++;
        }
        else if(event.getReactionEmote().getAsReactionCode().equals(DEMOCRACY_EMOJI)) {
            democracyVotes++;
        }
        determineGameMode();
    }

    @Override
    void run(MessageReactionRemoveEvent event) {
        if(event.getReactionEmote().getAsReactionCode().equals(ANARCHY_EMOJI)) {
            anarchyVotes--;
        }
        else if(event.getReactionEmote().getAsReactionCode().equals(DEMOCRACY_EMOJI)) {
            democracyVotes--;
        }
        determineGameMode();
    }

    @Override
    public void run(MessageReceivedEvent event) {

    }

    public void determineGameMode(){
       isDemocracy = democracyVotes > anarchyVotes;
    }

    public static boolean isIsDemocracy() {
        return isDemocracy;
    }
}
