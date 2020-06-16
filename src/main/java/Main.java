import MessageHandling.MessageHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Main extends ListenerAdapter {

    private static JDA jda;

    public static void main(String[] args) throws javax.security.auth.login.LoginException {
        JDABuilder builder = JDABuilder.createDefault(args[0]);
        jda = builder.build();
        jda.addEventListener(new MessageHandler(jda));

    }

    public static JDA getJda() {
        return jda;
    }
}
