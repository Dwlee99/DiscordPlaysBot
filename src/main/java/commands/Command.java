package commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * A command is an action that will be taken based in response to Discord input.
 */
public interface Command {
    /**
     * Runs an action based on what the command is meant to do.
     * @param event the event that will cause the command to run
     */
    void run(MessageReceivedEvent event);

    void run(String text);
}
