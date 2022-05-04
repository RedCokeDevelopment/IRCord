package dev.redcoke.ircord.utils;

import dev.redcoke.ircord.discord.DiscordMain;
import dev.redcoke.ircord.managers.ConfigManagers;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class DiscordUtils {
    public static String getSelfAvatarURL() {
        var self = DiscordMain.getJda().getSelfUser();
        return self.getAvatarUrl() != null ?  self.getAvatarUrl() : self.getDefaultAvatarUrl();
    }

    public static String convertToGenericMessageFormat(String message, List<Member> members, List<TextChannel> channels, List<Role> roles) {
        for (var mention : members) {
            message = message.replace("<@" + mention.getId() + ">", "@" + mention.getUser().getAsTag());
        }
        for (var channel : channels) {
            message = message.replace("<#" + channel.getId() + ">", "#" + channel.getName());
        }
        for (var role : roles) {
            message = message.replace("<@&" + role.getId() + ">", "@" + role.getName());
        }
        return message;
    }

    public static boolean shouldCreateEvent(String guild, String channel) {
        for (var listener : ConfigManagers.getConfig().discord.listen) {
            var listen = listener.split(":");
            // <Guild>:<Channel>
            if (listener.equals("*:*")) {
                return true; // listening to all guilds and channels
            } else {
                if (checkGuild(listen[0], guild) && checkChannel(listen[1], channel)) return true;
            }
        }
        return false;
    }

    private static boolean checkGuild(String configGuild, String actualGuild) {
        if (configGuild.equals("*")) {
            return true;
        } else {
            return configGuild.equals(actualGuild);
        }
    }

    private static boolean checkChannel(String configChannel, String actualChannel) {
        if (configChannel.equals("*")) {
            return true;
        } else {
            return configChannel.equals(actualChannel);
        }
    }
}
