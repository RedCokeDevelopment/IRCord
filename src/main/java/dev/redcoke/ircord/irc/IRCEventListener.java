package dev.redcoke.ircord.irc;

import dev.redcoke.ircord.IRCordMain;
import dev.redcoke.ircord.enums.ServiceProvider;
import dev.redcoke.ircord.object.GenericMessage;
import dev.redcoke.ircord.utils.IRCordUtil;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Log4j2
public class IRCEventListener extends ListenerAdapter {
    @Getter
    private static final Queue<GenericMessage> ircMessageQueue = new ArrayBlockingQueue<>(128);
    private int reconnectionAttempts = 3;

    @Override
    public void onMessage(MessageEvent event) {
        if (event.getMessage().equals("^status")) {
            event.respond("IRCord is running with version v" + IRCordUtil.getVersion());
            return;
        }

        var message = new GenericMessage(event.getMessage(), event.getUser().getNick() + "[" + event.getUser().getIdent() + "]", event.getChannel().getName()
                , event.getUser().getChannels().first().getChannelKey(), ServiceProvider.IRC);
        ircMessageQueue.add(message);
    }

    /**
     * We got disconnected from the server, reconnect
     * @param event {@link DisconnectEvent}
     */
    @Override
    public void onDisconnect(DisconnectEvent event) {
        if (reconnectionAttempts > 0) {
            reconnectionAttempts--;
            log.warn("IRCord Disconnected from server: {}" + event.getDisconnectException().getMessage());
            log.info("Reconnecting to IRC Server...");
            IRCordMain.getIrcMain().restart();
        } else {
            log.error("Failed to reconnect to IRC server after 3 tries! Not trying anymore.");
        }
    }
}
