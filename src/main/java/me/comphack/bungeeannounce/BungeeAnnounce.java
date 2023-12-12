package me.comphack.bungeeannounce;

import me.comphack.bungeeannounce.commands.GlobalBroadcastCommand;
import me.comphack.bungeeannounce.commands.ServerBroadcastCommand;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


public final class BungeeAnnounce extends Plugin {

    public static Configuration configuration;

    @Override
    public void onEnable() {
        loadConfig();

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        getLogger().info("Enabled plugin Bungee Announcer [v" + getDescription().getVersion() + "]");
        getLogger().info("Developed by COMPHACK | A product of Neptune Development");

        //Register the commands
        getProxy().getPluginManager().registerCommand(this, new GlobalBroadcastCommand(configuration));
        getProxy().getPluginManager().registerCommand(this, new ServerBroadcastCommand(configuration));
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled plugin Bungee Announcer [v" + getDescription().getVersion() + "]");
        getLogger().info("Developed by COMPHACK | A product of Neptune Development");
    }


    public void loadConfig() {
        String resourceFilePath = "config.yml";

        // Access the resource file
        InputStream inputStream = getClass().getResourceAsStream("/" + resourceFilePath);

        // Check if the resource file exists
        if (inputStream == null) {
            getLogger().severe("Resource file '" + resourceFilePath + "' not found!");
            return;
        }

        // Create the data folder if it doesn't exist
        Path dataFolderPath = getDataFolder().toPath();
        try {
            Files.createDirectories(dataFolderPath);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Create the config file path in the data folder
        Path configFile = dataFolderPath.resolve("config.yml");

        // Copy the resource file to the data folder if the config file doesn't exist
        if (!Files.exists(configFile)) {
            try (OutputStream outputStream = Files.newOutputStream(configFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                getLogger().info("Config file copied successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                getLogger().severe("Error copying config file!");
            } finally {
                // Close the InputStream
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            getLogger().info("Config file already exists, no need to copy.");
        }

    }

}
