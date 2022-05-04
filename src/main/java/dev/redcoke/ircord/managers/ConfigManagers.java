package dev.redcoke.ircord.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import dev.redcoke.ircord.config.Config;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Getter
public class ConfigManagers {
    @Getter
    private static Config config;

    @Getter
    public static JsonObject unsafeConfig;

    public static void loadConfig() {
        try {
            final var reader = Files.newBufferedReader(Paths.get("config.json"));
            unsafeConfig = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();
        } catch (final NoSuchFileException ex) {
            log.error("Configuration file not found! Exiting...");
            System.exit(1);
        } catch (final IOException ex) {
            ex.printStackTrace();
            log.error("An error occurred while loading config file. Exiting...");
            System.exit(1);
        }

        config = new Gson().fromJson(unsafeConfig, Config.class);

        try {
            Files.writeString(Paths.get("config.json"),
                    new GsonBuilder().setPrettyPrinting().create().toJson(unsafeConfig));
        } catch (final IOException ex) {
            log.warn("Unable to save config.json!");
        }
    }
}
