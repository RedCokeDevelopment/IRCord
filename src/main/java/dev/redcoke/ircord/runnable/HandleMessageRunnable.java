package dev.redcoke.ircord.runnable;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import dev.redcoke.ircord.IRCordMain;
import dev.redcoke.ircord.discord.DiscordEventListener;
import dev.redcoke.ircord.discord.DiscordMain;
import dev.redcoke.ircord.irc.IRCEventListener;
import dev.redcoke.ircord.irc.IRCMain;
import dev.redcoke.ircord.managers.ConfigManagers;
import dev.redcoke.ircord.utils.DiscordUtils;
import dev.redcoke.ircord.utils.IRCordUtil;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Webhook;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Log4j2
public class HandleMessageRunnable {
    @Getter
    private static ScheduledExecutorService executorService;

    public static void start() {
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            try {
                if (!IRCEventListener.getIrcMessageQueue().isEmpty()) {
                    // send to Discord <- IRC
                    var event = IRCEventListener.getIrcMessageQueue().poll();
                    if (event == null) return;
                    for (var bridge : ConfigManagers.getConfig().discord.bridge) {
                        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder()
                                .setContent(event.message())
                                .setAvatarUrl(DiscordUtils.getSelfAvatarURL())
                                .setUsername(event.sender() + " - " + event.channel());
                        try (WebhookClient client = new WebhookClientBuilder(bridge).build()) {
                            client.send(messageBuilder.build());
                        }
                    }
                }
                if (!DiscordEventListener.getDiscordMessageQueue().isEmpty()) {
                    // send to IRC
                    var event = DiscordEventListener.getDiscordMessageQueue().poll();
                    if (event == null) return;
                    var message = String.format("%s [#%s]> %s", event.sender(), event.channel(), event.message());
                    for (var channel : ConfigManagers.getConfig().irc.channels) {
                        IRCMain.getBot().sendIRC().message(channel, message);
                    }
                }
            } catch (Exception ex) {
                log.error(ex);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
