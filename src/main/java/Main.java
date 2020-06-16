import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Main extends ListenerAdapter {

    private static JDA jda;

    public static void main(String[] args) throws javax.security.auth.login.LoginException {
        JDABuilder builder = JDABuilder.createDefault(args[0]);
        jda = builder.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User author = event.getAuthor();
        Message message = event.getMessage();

    }
}
