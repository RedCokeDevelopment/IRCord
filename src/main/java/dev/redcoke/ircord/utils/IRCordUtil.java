package dev.redcoke.ircord.utils;

import dev.redcoke.ircord.IRCordMain;
import dev.redcoke.ircord.discord.DiscordMain;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IRCordUtil {
    public static String getVersion() {
        return (IRCordMain.class.getPackage().getImplementationVersion() != null) ? IRCordMain.class.getPackage().getImplementationVersion() : "(development build)";
    }


}
