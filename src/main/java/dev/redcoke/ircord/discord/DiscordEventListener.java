package dev.redcoke.ircord.discord;

import dev.redcoke.ircord.enums.ServiceProvider;
import dev.redcoke.ircord.object.GenericMessage;
import dev.redcoke.ircord.utils.DiscordUtils;
import dev.redcoke.ircord.utils.IRCordUtil;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Log4j2
public class DiscordEventListener extends ListenerAdapter {
    @Getter
    private static final Queue<GenericMessage> discordMessageQueue = new ArrayBlockingQueue<>(128);

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        log.info("Successfully connected to Discord API");
        event.getJDA().getPresence().setPresence(Activity.listening("IRCord v" + IRCordUtil.getVersion()), false);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isFromType(ChannelType.PRIVATE)) return;
        if (event.getMessage().getContentRaw().equals("^status")) {
            event.getMessage().reply("IRCord is running with version v" + IRCordUtil.getVersion()).queue();
            return;
        }
        if (DiscordUtils.shouldCreateEvent(event.getGuild().getId(), event.getChannel().getId())) {
            StringBuilder content = new StringBuilder(DiscordUtils.convertToGenericMessageFormat(event.getMessage().getContentRaw(), event.getMessage().getMentionedMembers(),
                    event.getMessage().getMentionedChannels(), event.getMessage().getMentionedRoles()));

            for (var attachment : event.getMessage().getAttachments()) {
                content.append(String.format("%n<Attachment>[ %s ]", attachment.getUrl()));
            }
            discordMessageQueue.add(new GenericMessage(content.toString(), event.getAuthor().getAsTag(),
                    event.getChannel().getName(), event.getChannel().getId(), ServiceProvider.DISCORD));
        }
    }
}
