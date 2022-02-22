package main.java.function;

import main.java.support.Constants;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RemoveChannelListenerCommand  extends CommandEventListener{

    private final StapleReactionListener srl;

    public RemoveChannelListenerCommand(StapleReactionListener listener) {
        srl = listener;
    }

    @Override
    public String getPrefix() {
        return Constants.COMMAND_REMOVE;
    }

    @Override
    public void evaluate(MessageReceivedEvent event) {
        String raw = event.getMessage().getContentRaw();

        String[] split = raw.split(Constants.SPACE);

        if (split.length != 2) {
            event.getTextChannel().sendMessage("unallowed usage!").queue();
            return;
        }

        String id = split[1];

        TextChannel channel = Util.getTextChannel(event.getJDA(), id);
        if (channel == null) {
            event.getTextChannel().sendMessage("unavaliable TextChannel: " + id + "!").queue();
            return;
        }

        srl.removeChannel(id);
    }
}
