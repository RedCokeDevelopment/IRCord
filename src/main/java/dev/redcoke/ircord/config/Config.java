package dev.redcoke.ircord.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Config {
    @SerializedName("discord")
    @Expose
    public Discord discord;
    @SerializedName("irc")
    @Expose
    public IRC irc;
}