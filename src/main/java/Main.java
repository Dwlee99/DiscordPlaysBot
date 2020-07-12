import handlers.MessageHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import handlers.ReactionHandler;
import commands.QueuePressCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;

import static handlers.MessageHandler.HOST;
import static handlers.MessageHandler.PLAYER;


public class Main extends ListenerAdapter {

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
