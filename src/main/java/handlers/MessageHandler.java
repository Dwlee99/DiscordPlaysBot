package handlers;

import commands.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static commands.CType.*;

public class MessageHandler extends ListenerAdapter {

    private static String gameChannelId;

    public static final String PLAYER = "Discord Player";
    public static final String HOST = "Game Host";

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

        Guild guild = event.getGuild();
        Member member = guild.getMember(user);

        if(text.toLowerCase().startsWith(".startgame") && hasRole(member, HOST)){
            CommandFactory.getCommandByType(START_GAME).run(event);
        }
        else if(text.toLowerCase().startsWith(".setbinds") && hasRole(member, HOST)){
            CommandFactory.getCommandByType(SET_BINDS).run(event);
        }
        else if(event.getChannel().getId().equals(gameChannelId) && hasRole(member, PLAYER)) {
            handleGameMessage(event);
        }
    }

    protected static boolean hasRole(Member member, String role){
        return member.getRoles().stream().anyMatch(r -> r.getName().equals(role));
    }

    private void handleGameMessage(MessageReceivedEvent event) {
        CommandFactory.getCommandByType(QUEUE_PRESS).run(event);
    }


}
