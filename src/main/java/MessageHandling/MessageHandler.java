package MessageHandling;

import commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static commands.CType.*;

public class MessageHandler extends ListenerAdapter {

    private JDA jda;

    public MessageHandler(JDA jda){
        this.jda = jda;
    }

    private static String gameChannelId;

    /**
     * Sets the channel id which will be used for group gaming on discord.
     * @param id the id of the channel as a String.
     */
    public static void setGameChannel(String id){
        gameChannelId = id;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getChannel().getId().equals(gameChannelId)){
            handleGameMessage(event);
            return;
        }

        User user = event.getAuthor();
        String text = event.getMessage().getContentDisplay();

        if(text.toLowerCase().startsWith(".startgame")){
            CommandFactory.getCommandByType(START_GAME).run(event);
        }
    }

    private void handleGameMessage(MessageReceivedEvent event) {

    }


}
