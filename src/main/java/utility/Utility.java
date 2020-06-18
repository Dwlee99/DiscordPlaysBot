package utility;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.RestAction;

public class Utility {

    /**
     * Sends a message in a discord channel.
     * @param text the text content of the message
     * @param channel the channel to send the message in
     * @return the id of the message sent
     */
    public static long send(String text, TextChannel channel){
        RestAction<Message> ra = channel.sendMessage(text);
        Message message = ra.complete();
        return message.getIdLong();

    }

    public static long sendEmbed(MessageEmbed embed, TextChannel channel){
        RestAction<Message> ra = channel.sendMessage(embed);
        Message message = ra.complete();
        return message.getIdLong();
    }

}
