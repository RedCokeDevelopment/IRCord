package dev.redcoke.ircord.object;

import dev.redcoke.ircord.enums.ServiceProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * A generic message that should compatible with providers.
 * @param message The message
 * @param sender The sender from the provider
 * @param channel The channel the message was sent at (if applicable, null otherwise)
 * @param provider The provider that sent the message {@link ServiceProvider}
 * @see ServiceProvider
 */
public record GenericMessage(@NotNull String message, @NotNull String sender, @Nullable String channel, @Nullable String channelId,
                             @NotNull ServiceProvider provider) {
}
