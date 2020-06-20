package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import reaction_handling.ReactionHandler;
import utility.CustomEmbedBuilder;

import java.util.Arrays;

public class SetGovernmentCommand extends AbstractCommand {

    private static boolean isDemocracy = false;

    private static double anarchyVotes = 0;
    private static double democracyVotes = 0;

    protected final static String ANARCHY_EMOJI = "\uD83D\uDD25";
    protected final static String DEMOCRACY_EMOJI = "\uD83D\uDCDD";

    private SetGovernmentCommand(){}

    private static Command setGovernmentCommand = new SetGovernmentCommand();

    public static Command getInstance(){
        return setGovernmentCommand;
    }

    @Override
    void run(MessageReactionAddEvent event) {
        if(event.getReactionEmote().getAsReactionCode().equals(ANARCHY_EMOJI)) {
            anarchyVotes++;
        }
        else if(event.getReactionEmote().getAsReactionCode().equals(DEMOCRACY_EMOJI)) {
            democracyVotes++;
        }
        TextChannel textChannel = event.getTextChannel();
        updateGovernment(textChannel);
    }

    @Override
    void run(MessageReactionRemoveEvent event) {
        if(event.getReactionEmote().getAsReactionCode().equals(ANARCHY_EMOJI)) {
            anarchyVotes--;
        }
        else if(event.getReactionEmote().getAsReactionCode().equals(DEMOCRACY_EMOJI)) {
            democracyVotes--;
        }
        TextChannel textChannel = event.getTextChannel();
        updateGovernment(textChannel);
    }

    @Override
    public void run(MessageReceivedEvent event) {

    }

    private void updateGovernment(TextChannel channel){
        isDemocracy = democracyVotes > anarchyVotes;
        channel.retrieveMessageById(ReactionHandler.getGovernmentMessageID()).queue(message -> {
            double democracyPercent = democracyVotes / (democracyVotes + anarchyVotes);
            message.editMessage(constructGovernment(democracyPercent)).queue();
        });
    }

    private static final int LINE_LENGTH = 30;

    protected static MessageEmbed constructGovernment(double democracyPercent) {
        EmbedBuilder eb = new CustomEmbedBuilder();

        char[] lineCreation = new char[LINE_LENGTH];
        Arrays.fill(lineCreation, '-');
        String line = new String(lineCreation);
        int dotLocation = (int) (democracyPercent * LINE_LENGTH);

        if (dotLocation != LINE_LENGTH) {
            line = line.substring(0, dotLocation) + "\uD83D\uDD18" + line.substring(dotLocation + 1);
        }
        else{
            line = line.substring(0, dotLocation) + "\uD83D\uDD18";
        }


        String boxedLine = "`" + line + "`";

        eb.addField("Democracy-meter", boxedLine, false);

        return eb.build();
    }

    public static boolean isDemocracy() {
        return isDemocracy;
    }
}
