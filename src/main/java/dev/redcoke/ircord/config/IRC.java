package dev.redcoke.ircord.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class IRC {

    @SerializedName("server")
    @Expose
    public String server;
    @SerializedName("port")
    @Expose
    public Integer port;
    @SerializedName("real_name")
    @Expose
    public String realName;
    @SerializedName("ident")
    @Expose
    public String ident;
    @SerializedName("nick")
    @Expose
    public String nick = null;
    @SerializedName("channels")
    @Expose
    public List<String> channels;

}