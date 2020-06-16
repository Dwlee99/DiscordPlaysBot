package MessageHandling;

import commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import static commands.CType.STARTGAME;

public class MessageHandler extends ListenerAdapter {


    private JDA jda;

    public MessageHandler(JDA jda){
        this.jda = jda;
    }

    private static String gameChannelId;

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
            CommandFactory.getCommandByType(STARTGAME).run(event);
        }
    }

    private void handleGameMessage(MessageReceivedEvent event) {

    }


}
