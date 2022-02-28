package main.java.support;

import main.java.main.BotMain;

import java.io.File;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Constants {

    public static final String COMMAND_PREFIX = "/";
    public static final String SPACE = " ";
    public static final String SEPERATOR = ";";

    //emojis
    public static final String PUSH_PIN = "\ud83d\udccc";

    //settings.ini
    public static final String SECTION_GENERAL = "general";
    public static final String KEY_TOKEN = "token";
    public static final String BOT_CHANNELS_TOKEN = "bot-channels";
    public static final String STAPLE_CHANNELS = "staple-channels";

    //commands
    public static final String COMMAND_ADD = "add-pin-channel";
    public static final String COMMAND_REMOVE = "remove-pin-channel";

    public static String getProjectFolder(Class aclass) throws Exception {
        CodeSource codeSource = aclass.getProtectionDomain().getCodeSource();

        File jarFile;

        if (codeSource.getLocation() != null) {
            jarFile = new File(codeSource.getLocation().toURI());
        }
        else {
            String path = aclass.getResource(aclass.getSimpleName() + ".class").getPath();
            String jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"));
            jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
            jarFile = new File(jarFilePath);
        }
        if (runningFromIntelliJ()) {
            System.out.println("Running from IntelliJ");
            return jarFile.getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath();
        } else {
            System.out.println("Running from commandline");
            return jarFile.getParentFile().getAbsolutePath();
        }
    }

    public static String getFileInJarFoulder(String filename) {
        return BotMain.JAR_FOULDER + "\\" + filename;
    }

    public static boolean runningFromIntelliJ()
    {
        String classPath = System.getProperty("java.class.path");
        return classPath.contains(".gradle");
    }

    public static List<String> getChannels(String section, String token) {
        String content = BotMain.getSetting(section, token);

        if (content.isEmpty()) return new ArrayList<>();

        return Arrays.asList(content.split(SEPERATOR));
    }

    public static String channelsToList(List<String> channels) {
        StringJoiner sj = new StringJoiner(SEPERATOR);
        for (String s : channels) {
            sj.add(s);
        }
        return sj.toString();
    }
}

