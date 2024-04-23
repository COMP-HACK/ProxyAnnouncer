/**
 *
 * Velocity Support added by FrancyPro
 *
 * **/
package me.comphack.bungeeannounce.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import me.comphack.bungeeannounce.velocity.api.CommandApi;
import me.comphack.bungeeannounce.velocity.api.ConfigApi;
import me.comphack.bungeeannounce.velocity.commands.GlobalBroadcastCommand;
import me.comphack.bungeeannounce.velocity.commands.ServerBroadcastCommand;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "proxyannounce",
        name = "ProxyAnnounce",
        version = "1.0.1",
        authors = {"COMPHACK"}
)
public class VelocityAnnounce {

    @Getter
    private final ProxyServer server;
    @Getter
    private final Logger logger;
    @Getter
    private final Path dataDirectory;
    @Getter
    private static VelocityAnnounce instance;

    @Inject
    public VelocityAnnounce(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        instance = this;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        ConfigApi.initialise(dataDirectory.toFile());

        CommandManager commandManager = server.getCommandManager();
        CommandApi.registerCommand(this, commandManager, new GlobalBroadcastCommand(), "announce");
        CommandApi.registerCommand(this, commandManager, new ServerBroadcastCommand(), "announceserver");
    }

    @Subscribe
    public void onProxyShuttingdown(ProxyShutdownEvent event) {
        logger.info("Disabled plugin Bungee Announcer [v1.0.0]");
        logger.info("Developed by COMPHACK | A product of Neptune Development");
    }
}
