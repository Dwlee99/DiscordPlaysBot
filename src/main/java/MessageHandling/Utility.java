package MessageHandling;

import net.dv8tion.jda.api.entities.TextChannel;

public class Utility {

    public static void send(String text, TextChannel channel){
        channel.sendMessage(text).queue();
    }
}
