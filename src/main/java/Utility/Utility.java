package Utility;

import net.dv8tion.jda.api.entities.TextChannel;

public class Utility {

    /**
     * Sends a message in a discord channel.
     * @param text the text content of the message
     * @param channel the channel to send the message in
     */
    public static void send(String text, TextChannel channel){
        channel.sendMessage(text).queue();
    }
}
