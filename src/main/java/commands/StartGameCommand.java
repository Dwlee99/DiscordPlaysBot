package commands;

import MessageHandling.MessageHandler;
import MessageHandling.Utility;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Optional;

/**
 * A command that will initiate a game by setting the channel it is to be played in.
 */
public class StartGameCommand extends AbstractCommand {

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
                MessageHandler.setGameChannel(channel.get().getId());
                Utility.send( "Game channel successfully set!", curChannel);
            }
            else{
                Utility.send("Invalid channel name, please try again.", curChannel);
            }

        }
        catch(IndexOutOfBoundsException e){
            Utility.send("No game channel provided. (Ex: ``.startgame example-channel``)", curChannel);
        }
    }
}
