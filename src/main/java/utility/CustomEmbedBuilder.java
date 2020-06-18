package utility;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.Instant;

public class CustomEmbedBuilder extends EmbedBuilder {

    public CustomEmbedBuilder(){
        super();
        setDefaults(this);
    }

    public CustomEmbedBuilder(EmbedBuilder eb){
        super(eb);
        setDefaults(this);
    }

    private static final String LOGO_URL = "https://i.imgur.com/YW3b3JD.png";

    private static final String AUTHOR_URL = null;

    private static void setDefaults(EmbedBuilder eb){
        eb.setAuthor("DiscordPlays", AUTHOR_URL, LOGO_URL);
        eb.setColor(new Color(111, 122, 137));
    }
}
