package commands;

import message_handling.MessageHandler;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import org.w3c.dom.Text;
import reaction_handling.ReactionHandler;
import utility.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Optional;

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

                long id = Utility.sendEmbed(constructInstructions(), gameChannel);
                ReactionHandler.setReactionMessage(id);
                addReactions(gameChannel, id);

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

    private MessageEmbed constructInstructions(){
        EmbedBuilder eb = new CustomEmbedBuilder();

        return eb.build();

    }

    private void addReactions(TextChannel channel, long reactionID){
        channel.retrieveMessageById(reactionID).queue(message -> {
            for(String key : QueuePressCommand.getReactionMap().keySet()){
                message.addReaction(key).queue();
            }
        });
    }
}
