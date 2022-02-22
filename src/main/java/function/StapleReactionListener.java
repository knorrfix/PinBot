package main.java.function;

import main.java.main.BotMain;
import main.java.support.Constants;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class StapleReactionListener extends ReactionEventListener{

    private final Set<String> channel_ids = new HashSet<>();

    public StapleReactionListener() {
        channel_ids.addAll(Constants.getChannels(Constants.SECTION_GENERAL, Constants.STAPLE_CHANNELS));
    }

    @Override
    public String emote() {
        return Constants.PUSH_PIN;
    }

    public void addChannel(String channel_id) {
        channel_ids.add(channel_id);
        String config = Constants.channelsToList(new ArrayList<>(channel_ids));
        BotMain.removeSetting(Constants.SECTION_GENERAL, Constants.STAPLE_CHANNELS);
        BotMain.addSetting(Constants.SECTION_GENERAL, Constants.STAPLE_CHANNELS, config);
    }

    public void removeChannel(String channel_id) {
        channel_ids.remove(channel_id);
        String config = Constants.channelsToList(new ArrayList<>(channel_ids));
        BotMain.removeSetting(Constants.SECTION_GENERAL, Constants.STAPLE_CHANNELS);
        BotMain.addSetting(Constants.SECTION_GENERAL, Constants.STAPLE_CHANNELS, config);
    }

    @Override
    public void onAddReaction(@NotNull MessageReactionAddEvent event) {
        String id = event.getMessageId();
        TextChannel channel = Util.getTextChannel(event.getJDA(), id);
        channel.pinMessageById(id).queue();
    }

    @Override
    public void onRemoveReaction(@NotNull MessageReactionRemoveEvent event) {
        String id = event.getMessageId();

        TextChannel channel = Util.getTextChannel(event.getJDA(), id);
        channel.retrieveMessageById(id).queue((Message m) -> {
            if (m.getReactionByUnicode(emote()) == null) {
                channel.unpinMessageById(id).queue();
            }
        });
    }
}
