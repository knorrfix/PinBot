package main.java.function;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class CommandEventListener {

    public abstract String getPrefix();
    public abstract void evaluate(MessageReceivedEvent event);

}
