import MessageHandling.MessageHandler;
import gamepad.Gamepad;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Main extends ListenerAdapter {

    public static void main(String[] args) throws javax.security.auth.login.LoginException {
        JDABuilder builder = JDABuilder.createDefault(args[0]);
        JDA jda = builder.build();
        jda.addEventListener(new MessageHandler(jda));
    }
}
