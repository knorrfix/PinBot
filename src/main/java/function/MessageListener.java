package main.java.function;

import main.java.support.Constants;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MessageListener extends ListenerAdapter {

    Map<String, CommandEventListener> commandListeners = new HashMap<>();
    Map<String, ReactionEventListener> reactionListeners = new HashMap<>();
    Set<String> channels = new HashSet<>();

    public MessageListener() {
        channels.addAll(Constants.getChannels(Constants.SECTION_GENERAL, Constants.BOT_CHANNELS_TOKEN));
    }

    public void addCommand(CommandEventListener listener) {
        commandListeners.put(listener.getPrefix(), listener);
    }

    public void addReaction(ReactionEventListener listener) {
        reactionListeners.put(listener.emote(), listener);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!channels.contains(event.getChannel().getId())) return;

        if (!event.getMessage().getContentRaw().startsWith(Constants.COMMAND_PREFIX)) return;

        String raw = event.getMessage().getContentRaw();
        String command = raw.substring(0, raw.indexOf(Constants.SPACE));

        if (!commandListeners.containsKey(command)) return;

        commandListeners.get(command).evaluate(event);
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        String emote = event.getReaction().getReactionEmote().getEmoji();
        if (!reactionListeners.containsKey(emote)) return;

        reactionListeners.get(emote).onAddReaction(event);
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        String emote = event.getReaction().getReactionEmote().getEmoji();
        if (!reactionListeners.containsKey(emote)) return;

        reactionListeners.get(emote).onRemoveReaction(event);
    }
}
