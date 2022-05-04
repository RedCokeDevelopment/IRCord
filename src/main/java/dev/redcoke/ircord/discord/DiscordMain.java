package dev.redcoke.ircord.discord;

import dev.redcoke.ircord.managers.ConfigManagers;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

@Log4j2
public class DiscordMain {
    @Getter
    private static JDA jda;

    public void start() {
        try {
            jda = JDABuilder.createDefault(ConfigManagers.getConfig().discord.token,
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_INVITES,
                    GatewayIntent.GUILD_PRESENCES,
                    GatewayIntent.GUILD_MESSAGES
            ).build();
            jda.addEventListener(new DiscordEventListener());
            jda.setAutoReconnect(true);
        } catch (LoginException e) {
            log.fatal("Failed to login to Discord! incorrect token? ", e);
            throw new RuntimeException(e);
        }
    }
}
