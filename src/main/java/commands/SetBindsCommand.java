package commands;

import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import reaction_handling.ReactionHandler;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetBindsCommand implements Command {

    private SetBindsCommand(){}

    private static Command setBindsCommand = new SetBindsCommand();

    /**
     * Unicode -> Key press code
     */
    private LinkedHashMap<String, String> reactionMap = new LinkedHashMap<>();

    private static final Pattern CUSTOM_EMOJI = Pattern.compile(":\\w+:\\d+");

    /**
     * @return the singleton instance of the QueuePressCommand
     */
    static Command getInstance(){
        return setBindsCommand;
    }
    @Override
    public void run(MessageReceivedEvent event) {
        reactionMap.clear();
        String text = event.getMessage().getContentRaw();
        String[] lines = text.split("\n");
        for(String line: lines){
            Optional<String> optEmoji = EmojiParser.extractEmojis(line).stream().findFirst();

            if(optEmoji.isPresent()){
                String emoji = optEmoji.get();
                int index = line.indexOf(emoji);
                String keyPress = line.substring(index + emoji.length() + 1);
                keyPress = keyPress.trim();
                reactionMap.put(emoji, keyPress);
            }
            else{
                Matcher matcher = CUSTOM_EMOJI.matcher(line);

                if(matcher.find()) {
                    String emoji = matcher.group();
                    int index = line.indexOf(emoji);
                    String keyPress = line.substring(index + emoji.length() + 1);
                    keyPress = keyPress.trim();
                    reactionMap.put(emoji, keyPress);
                }
            }
        }
        QueuePressCommand.setReactionMap(reactionMap);
    }

    @Override
    public void run(GenericMessageReactionEvent event) {

    }
}
