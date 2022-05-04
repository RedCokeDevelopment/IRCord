package dev.redcoke.ircord;

import dev.redcoke.ircord.config.Config;
import dev.redcoke.ircord.discord.DiscordMain;
import dev.redcoke.ircord.irc.IRCMain;
import dev.redcoke.ircord.managers.ConfigManagers;
import dev.redcoke.ircord.runnable.HandleMessageRunnable;
import dev.redcoke.ircord.utils.IRCordUtil;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class IRCordMain {
    @Getter
    private static final IRCMain ircMain = new IRCMain();
    @Getter
    private static final DiscordMain discordMain = new DiscordMain();
    @Getter
    private static Thread ircThread;
    @Getter
    private static Thread discordThread;

    public static void main(String[] args) {
        log.info("""
                     
                     ___________  _____               _\s
                     |_   _| ___ \\/  __ \\             | |
                       | | | |_/ /| /  \\/ ___  _ __ __| |
                       | | |    / | |    / _ \\| '__/ _` |
                      _| |_| |\\ \\ | \\__/\\ (_) | | | (_| |
                      \\___/\\_| \\_| \\____/\\___/|_|  \\__,_|
                          v{}
                """, IRCordUtil.getVersion());
        log.info("Loading config...");
        ConfigManagers.loadConfig();

        ircThread = new Thread(ircMain::start);
        discordThread = new Thread(discordMain::start);
        HandleMessageRunnable.start();
        log.info("Starting IRCord...");
        discordThread.start();
        ircThread.start();
    }
}
