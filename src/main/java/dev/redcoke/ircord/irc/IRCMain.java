package dev.redcoke.ircord.irc;

import dev.redcoke.ircord.config.Config;
import dev.redcoke.ircord.managers.ConfigManagers;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

@Log4j2
public class IRCMain {
    @Getter
    private static PircBotX bot;

    public void start() {
        Configuration config = new Configuration.Builder()
                .setName(ConfigManagers.getConfig().irc.nick)
                .addServer(ConfigManagers.getConfig().irc.server)
                .addAutoJoinChannels(ConfigManagers.getConfig().irc.channels)
                .addListener(new IRCEventListener())
                .buildConfiguration();
        bot = new PircBotX(config);
        try {
            bot.startBot();
        } catch (IOException | IrcException ex) {
            log.fatal("There were issues starting the IRC bridge!", ex);
            throw new RuntimeException(ex);
        }
    }

    public void restart() {
        if (bot != null && bot.isConnected()) {
            bot.close();
            start();
        }
    }
}
