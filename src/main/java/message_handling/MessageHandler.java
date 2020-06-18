package message_handling;

import commands.*;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static commands.CType.*;

public class MessageHandler extends ListenerAdapter {

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
        if(!event.getAuthor().isBot())
            new Thread(() -> handleMessage(event)).start();
    }

    private void handleMessage(MessageReceivedEvent event){
        User user = event.getAuthor();
        String text = event.getMessage().getContentDisplay();

        if(text.toLowerCase().startsWith(".startgame")){
            CommandFactory.getCommandByType(START_GAME).run(event);
        }
        else if(text.toLowerCase().startsWith(".setbinds")){

        }
        else if(event.getChannel().getId().equals(gameChannelId)){
            handleGameMessage(event);
        }


    }

    private void handleGameMessage(MessageReceivedEvent event) {
        CommandFactory.getCommandByType(QUEUE_PRESS).run(event);
    }


}
