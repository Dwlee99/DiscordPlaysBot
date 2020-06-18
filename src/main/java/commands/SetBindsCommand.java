package commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SetBindsCommand extends AbstractCommand {

    private SetBindsCommand(){}

    private static Command setBindsCommand = new SetBindsCommand();

    /**
     * @return the singleton instance of the QueuePressCommand
     */
    static Command getInstance(){
        return setBindsCommand;
    }
    @Override
    public void run(MessageReceivedEvent event) {

    }
}
