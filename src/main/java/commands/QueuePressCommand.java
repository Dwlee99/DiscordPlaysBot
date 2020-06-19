package commands;

import gamepad.Gamepad;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueuePressCommand extends AbstractCommand {

    private QueuePressCommand(){}

    private static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    private static final Object lock = new Object();

    private static boolean running = false;

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
        queue.add(event.getMessage().getContentDisplay());
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    private static LinkedHashMap<String, String> reactionMap = new LinkedHashMap<>();

    @Override
    void run(MessageReactionAddEvent event) {
        String curEmoji = ":" + event.getReactionEmote().getAsReactionCode(); //discord doesn't provide the first colon
        if(reactionMap.containsKey(curEmoji)) {
            queue.add(reactionMap.get(curEmoji));
            synchronized (lock) {
                lock.notifyAll();
            }
        }
        event.retrieveMessage().queue((message -> {
            message.removeReaction(event.getReactionEmote().getAsReactionCode(), event.getUser()).queue();
        }));
    }

    static void setReactionMap(LinkedHashMap<String, String> map) {
        reactionMap = map;
    }

    static LinkedHashMap<String, String> getReactionMap(){
        return reactionMap;
    }

    @Override
    void run(MessageReactionRemoveEvent event) {}
}
