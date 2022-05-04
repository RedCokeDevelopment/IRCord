package dev.redcoke.ircord.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;


@Getter
public class Discord {

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("bridge")
    @Expose
    public List<String> bridge = null;

    @SerializedName("listen")
    @Expose
    public List<String> listen = null;

}