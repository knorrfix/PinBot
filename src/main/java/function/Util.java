package main.java.function;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

public class Util {

    public static TextChannel getTextChannel(JDA jda, String channel_id) {
        return jda.getTextChannelById(channel_id);
    }
}
