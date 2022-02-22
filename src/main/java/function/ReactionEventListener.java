package main.java.function;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import org.jetbrains.annotations.NotNull;

public abstract class ReactionEventListener {

    public abstract String emote();

    public void onAddReaction(@NotNull MessageReactionAddEvent event) {
        return;
    }

    public void onRemoveReaction(@NotNull MessageReactionRemoveEvent event) {
        return;
    }
}
