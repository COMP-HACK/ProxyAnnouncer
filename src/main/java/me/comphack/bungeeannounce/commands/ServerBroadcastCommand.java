package me.comphack.bungeeannounce.commands;

import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.util.Arrays;

public class ServerBroadcastCommand extends Command {

    private Configuration config;

    public ServerBroadcastCommand(Configuration config) {
        super("announceserver", "broadcast.command.server");
        this.config = config;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length <= 1) {
            sender.sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "Usage: /announceserver <server> <message...>"));
            return;
        }

        String serverName = args[0];
        ServerInfo server = getServerByName(serverName);

        if (server == null) {
            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cServer was not found.")));
            return;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        TextComponent formattedMessage = new TextComponent(ChatColor.translateAlternateColorCodes('&', config.getString("announce.server-message")
                .replace("{prefix}", config.getString("announce.prefix"))
                .replace("{message}", message)
                .replace("{server}", server.getName())
        ));

// Broadcast the formatted message to the specified server
        for (ProxiedPlayer player : server.getPlayers()) {
            player.sendMessage(formattedMessage);
        }

// Send success message to the command sender
        sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&aAnnouncement sent to " + server.getName() + ": " + message)));


    }

    private ServerInfo getServerByName(String serverName) {
        for (ServerInfo server : ProxyServer.getInstance().getServers().values()) {
            if (server.getName().equalsIgnoreCase(serverName)) {
                return server;
            }
        }
        return null;
    }

}
