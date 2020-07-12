import message_handling.MessageHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import reaction_handling.ReactionHandler;
import commands.QueuePressCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.xml.ws.Holder;
import java.util.List;


public class Main extends ListenerAdapter {

    private static final String PLAYER = "Discord Player";
    private static final String HOST = "Game Host";

    public static void main(String[] args) throws javax.security.auth.login.LoginException, InterruptedException {
        JDABuilder builder = JDABuilder.createDefault(args[0]);
        JDA jda = builder.build().awaitReady();

        checkRoles(jda.getGuilds());
        jda.addEventListener(new ListenerAdapter() {
            @Override
            public void onGuildJoin(@Nonnull GuildJoinEvent event) { checkRoles(event.getJDA().getGuilds());
            }
        });
        jda.addEventListener(new MessageHandler());
        jda.addEventListener(new ReactionHandler());

        startParallelThreads();
    }

    /**
     * Starts threads in the bot that need to be running constantly
     */
    private static void startParallelThreads(){
        QueuePressCommand.startCheckingQueue();
    }

    private static void checkRoles(List<Guild> guildList) {
        for (Guild guild : guildList){
            if(guild.getRolesByName(PLAYER, false).size() == 0) {
                guild.createRole().setName(PLAYER).queue();
            }
            if(guild.getRolesByName(HOST, false).size() == 0) {
                guild.createRole().setName(HOST).queue();
            }
        }
    }
}
