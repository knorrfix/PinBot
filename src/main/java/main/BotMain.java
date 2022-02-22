package main.java.main;

import main.java.function.AddChannelListenerCommand;
import main.java.function.MessageListener;
import main.java.function.RemoveChannelListenerCommand;
import main.java.function.StapleReactionListener;
import main.java.support.Constants;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.ini4j.Wini;


import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class BotMain {

    public static String JAR_FOULDER;
    private static Wini settings;


    public static void main(String[] args) throws LoginException {

        try {
            JAR_FOULDER = Constants.getProjectFolder(BotMain.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something gone wrong.");
            System.exit(-1);
        }
        System.out.println("JAR Location: " + JAR_FOULDER);

        try {
            settings = new Wini(new File(Constants.getFileInJarFoulder("settings.ini")));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't read settings.ini");
            System.exit(-1);
        }

        String token = getSetting(Constants.SECTION_GENERAL, Constants.KEY_TOKEN);
        if (token.isEmpty()) {
            System.out.println("No token stored, exiting...");
            System.exit(-1);
        }

        //check all DBs

        JDABuilder builder = JDABuilder.createDefault(token);

        // Disable parts of the cache
        builder.setMemberCachePolicy(MemberCachePolicy.VOICE);
        // Disable compression (not recommended)
        builder.setCompression(Compression.NONE);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.playing("use me"));

        MessageListener ml = new MessageListener();
        StapleReactionListener srl = new StapleReactionListener();
        ml.addCommand(new AddChannelListenerCommand(srl));
        ml.addCommand(new RemoveChannelListenerCommand(srl));

        ml.addReaction(srl);

        builder.addEventListeners(ml);
        builder.build();
    }

    public static String getSetting(String section, String key) {
        String s = settings.get(section, key);
        return (s == null) ? "" : s;
    }

    public static void addSetting(String section, String key, String value) {
        settings.put(section, key, value);
        try {
            settings.store();
        } catch (IOException e) {
            System.out.println("Can't save settings.ini");
        }
    }

    public static void removeSetting(String section, String key) {
        settings.remove(section, key);
        try {
            settings.store();
        } catch (IOException e) {
            System.out.println("Can't save settings.ini");
        }
    }
}
