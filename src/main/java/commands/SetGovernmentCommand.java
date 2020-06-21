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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetGovernmentCommand extends AbstractCommand {

    private static boolean isDemocracy = false;

    private static double anarchyVotes = 0;
    private static double democracyVotes = 0;

    protected final static String ANARCHY_EMOJI = "\uD83D\uDD25";
    protected final static String DEMOCRACY_EMOJI = "\uD83D\uDCDD";

    private final static String NO_VOTE = "";
    private static String mostRecentVote = NO_VOTE;

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

    private static void updateGovernment(TextChannel channel) {
        isDemocracy = democracyVotes > anarchyVotes;
        mostRecentVote = isDemocracy ? mostRecentVote : NO_VOTE;
        channel.retrieveMessageById(ReactionHandler.getGovernmentMessageID()).queue(message -> {
            double democracyPercent = democracyVotes / (democracyVotes + anarchyVotes);
            message.editMessage(constructGovernment(democracyPercent)).queue();
        });
        if (isDemocracy) {
            QueuePressCommand.startDemocracy(channel);
        }
    }

    private static final int LINE_LENGTH = 30;

    private static final char BLACK_RECTANGLE = '\u25AC';
    private static final String RADIO_BUTTON = "\uD83D\uDD18";
    private static final String RED_TRIANGLE = "\uD83D\uDEC6";
    private static String initialLine;
    private static String leftOffsetSpaces;
    private static String rightOffsetSpaces;

    static {
        char[] lineCreation = new char[LINE_LENGTH];
        Arrays.fill(lineCreation, BLACK_RECTANGLE);
        initialLine = new String(lineCreation);

        char[] offsetSpaces = new char[LINE_LENGTH / 2 + 1];
        Arrays.fill(offsetSpaces, ' ');
        leftOffsetSpaces = new String(offsetSpaces);

        char[] offsetSpaces2 = new char[LINE_LENGTH / 2 - 1];
        Arrays.fill(offsetSpaces2, ' ');
        rightOffsetSpaces = new String(offsetSpaces2);
    }

    protected static MessageEmbed constructGovernment(double democracyPercent) {
        EmbedBuilder eb = new CustomEmbedBuilder();

        int dotLocation = (int) Math.ceil(democracyPercent * LINE_LENGTH);
        String line = initialLine;

        if (dotLocation != LINE_LENGTH) {
            line = line.substring(0, dotLocation) + RADIO_BUTTON + line.substring(dotLocation + 1);
        }
        else{
            line = line.substring(0, dotLocation) + RADIO_BUTTON;
        }

        String boxedLine = "```" + line;
        String lineWithMarker = boxedLine + "\n" + leftOffsetSpaces + RED_TRIANGLE + rightOffsetSpaces + "```";

        eb.addField("Democracy-meter", lineWithMarker, false);
        eb.addField("Most Recent Vote", mostRecentVote.equals(NO_VOTE) ? "N/A" : mostRecentVote, false);

        return eb.build();
    }

    private static final Pattern CUSTOM_EMOJI = Pattern.compile(":\\w+:\\d+");

    protected static void setMostRecentVote(String unicode, TextChannel textChannel){
        Matcher matcher = CUSTOM_EMOJI.matcher(unicode);
        String formatted;
        if(matcher.find()) {
            formatted = "<" + unicode + ">";
        }
        else{
            formatted = unicode;
        }
        mostRecentVote = formatted;
        updateGovernment(textChannel);
    }

    public static boolean isDemocracy() {
        return isDemocracy;
    }
}
