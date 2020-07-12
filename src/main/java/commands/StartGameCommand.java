package commands;

import handlers.MessageHandler;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import handlers.ReactionHandler;
import utility.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A command that will initiate a game by setting the channel it is to be played in.
 */
public class StartGameCommand implements Command {

    private StartGameCommand(){}

    private static Command startGameCommand = new StartGameCommand();

    /**
     * @return the singleton instance of the StartGameCommand
     */
    static Command getInstance(){
        return startGameCommand;
    }

    @Override
    public void run(MessageReceivedEvent event) {
        String text = event.getMessage().getContentDisplay();
        String[] args = text.split("\\s");
        JDA jda = event.getJDA();
        TextChannel curChannel = event.getTextChannel();
        try{
            String gameChannelName = args[1];
            Optional<TextChannel> channel =
                    jda.getTextChannelsByName(gameChannelName, true).stream().findFirst();

            if(channel.isPresent()){
                TextChannel gameChannel = channel.get();

                MessageHandler.setGameChannel(gameChannel.getId());
                Utility.send( "Game channel successfully set!", curChannel);

                long governmentID = Utility.sendEmbed(SetGovernmentCommand.constructGovernment(0), gameChannel);
                ReactionHandler.setGovernmentMessageID(governmentID);
                addGovernmentReactions(gameChannel, governmentID);

                long controllerID = Utility.sendEmbed(constructInstructions(), gameChannel);
                gameChannel.pinMessageById(controllerID).queue();

                ReactionHandler.setControllerMessageID(controllerID);
                addControllerReactions(gameChannel, controllerID);

            }
            else{
                Utility.send("Invalid channel name, please try again.", curChannel);
            }

        }
        catch(IndexOutOfBoundsException e){
            Utility.send("No game channel provided. (Ex: ``.startgame example-channel``)", curChannel);
        }
    }





    @Override
    public void run(GenericMessageReactionEvent event) {

    }

    private static final Pattern CUSTOM_EMOJI = Pattern.compile(":\\w+:\\d+");

    private MessageEmbed constructInstructions(){
        EmbedBuilder eb = new CustomEmbedBuilder();
        LinkedHashMap<String, String> reactions = QueuePressCommand.getReactionMap();

        StringBuilder sb = new StringBuilder();

        reactions.forEach((k,v) -> {
            Matcher matcher = CUSTOM_EMOJI.matcher(k);
            if(matcher.find()) {
                sb.append("<").append(k).append(">");
            }
            else{
                sb.append(k);
            }
            sb.append(" - ").append("`").append(v).append("`").append("\n");
        });

        eb.addField("List of Reactions", sb.toString(), true);

        return eb.build();

    }


    private void addGovernmentReactions(TextChannel channel, long reactionID) {
        channel.retrieveMessageById(reactionID).queue(message -> {
            message.addReaction(SetGovernmentCommand.DEMOCRACY_EMOJI).queue();
            message.addReaction(SetGovernmentCommand.ANARCHY_EMOJI).queue();
        });
    }

    private void addControllerReactions(TextChannel channel, long reactionID){
        channel.retrieveMessageById(reactionID).queue(message -> {
            for(String key : QueuePressCommand.getReactionMap().keySet()){
                message.addReaction(key).queue();
            }
        });
    }
}
