package commands;

import gamepad.Gamepad;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import reaction_handling.ReactionHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueuePressCommand extends AbstractCommand {

    private QueuePressCommand(){}

    private static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    private static final Object lock = new Object();

    private static final int VOTE_DELAY = 5000;

    private static boolean running = false;

    private static Random random = new Random();

    private static boolean democracyModeRunning = false;

    /**
     * Starts checking the queue for new key presses. Can only be run once.
     */
    public static void startCheckingQueue(){
        if(!running) {
            new Thread(QueuePressCommand::runQueue).start();
            running = true;
        }
    }

    /**
     * Acquires a lock to check if the queue is empty, then waits until it is not. After the queue is no longer empty
     * and the thread is woken up, it will attempt to run the key press and repeat.
     */
    private static void runQueue(){
        String top;
        while (true) {
            synchronized (lock) {
                while (queue.isEmpty()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
            }
            top = queue.poll();
            Gamepad.pressKey(top);
        }
    }

    private static Command queuePressCommand = new QueuePressCommand();

    /**
     * @return the singleton instance of the QueuePressCommand
     */
    static Command getInstance(){
        return queuePressCommand;
    }

    @Override
    public void run(MessageReceivedEvent event) {
        if(SetGovernmentCommand.isDemocracy()) { return; }
        addAndNotify(event.getMessage().getContentDisplay());
    }

    private static LinkedHashMap<String, String> reactionMap = new LinkedHashMap<>();

    @Override
    void run(MessageReactionAddEvent event) {
        if(SetGovernmentCommand.isDemocracy()) { return; }
        String curEmoji = event.getReactionEmote().getAsReactionCode(); //discord doesn't provide the first colon
        if(reactionMap.containsKey(curEmoji)) {
            addAndNotify(reactionMap.get(curEmoji));
        } else if(reactionMap.containsKey(":" + curEmoji)) {
            addAndNotify(reactionMap.get(":" + curEmoji));
        }
        event.retrieveMessage().queue((message -> {
            message.removeReaction(event.getReactionEmote().getAsReactionCode(), event.getUser()).queue();
        }));
    }

    private static void addAndNotify(String keyPress) {
        queue.add(keyPress);
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    static void setReactionMap(LinkedHashMap<String, String> map) {
        reactionMap = map;
    }

    static void startDemocracy(TextChannel channel){
        if(!democracyModeRunning) {
            democracyModeRunning = true;
            queue.clear();
            new Thread(() -> {
                while (SetGovernmentCommand.isDemocracy()) {
                    try {
                        Thread.sleep(VOTE_DELAY);
                    } catch (InterruptedException ignore) {}
                    if(SetGovernmentCommand.isDemocracy())
                        addAndNotify(getKeyPress(channel));
                }
                democracyModeRunning = false;
            }).start();
        }
    }

    private static String getKeyPress(TextChannel channel) {
        int topCount = 0;
        int curCount;
        ArrayList<String> topReactions = new ArrayList<>();

        Message message = channel.retrieveMessageById(ReactionHandler.getControllerMessageID()).complete();
        for(MessageReaction react : message.getReactions()) {
            curCount = react.getCount();
            MessageReaction.ReactionEmote reactEmote =  react.getReactionEmote();

            if(curCount > topCount) {
                topReactions.clear();
                topCount = curCount;

                if(reactEmote.isEmote()) {
                    topReactions.add(":" + reactEmote.getAsReactionCode());
                }
                else {
                    topReactions.add(reactEmote.getAsReactionCode());
                }
            }
            else if(curCount == topCount) {
                if(reactEmote.isEmote()) {
                    topReactions.add(":" + reactEmote.getAsReactionCode());
                }
                else {
                    topReactions.add(reactEmote.getAsReactionCode());
                }            }
        }

        if(topReactions.isEmpty()) {
            return "";
        }

        int index = random.nextInt(topReactions.size());
        String reaction = topReactions.get(index);

        SetGovernmentCommand.setMostRecentVote(reaction, channel);
        return reactionMap.get(reaction);
    }

    static LinkedHashMap<String, String> getReactionMap(){
        return reactionMap;
    }

    @Override
    void run(MessageReactionRemoveEvent event) {}
}
