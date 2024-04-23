package me.comphack.bungeeannounce.velocity.api;

import com.github.smuddgge.squishyconfiguration.implementation.YamlConfiguration;

import java.io.File;

public class ConfigApi extends YamlConfiguration {
    private static ConfigApi config;

    public ConfigApi(File folder) {
        super(folder, "config.yml");
        this.setDefaultPath("config.yml");
        this.load();
    }

    public static void initialise(File folder) {
        ConfigApi.config = new ConfigApi(folder);
    }

    public static ConfigApi get() {
        return ConfigApi.config;
    }
}