package me.comphack.bungeeannounce.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.util.Collection;

public class GlobalBroadcastCommand extends Command {

    private Configuration config;

    public GlobalBroadcastCommand(Configuration config) {
        super("announce", "broadcast.command.global");
        this.config = config;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(args.length <= 0) {
            sender.sendMessage(new TextComponent("Please provide a message to announce"));
            return;
        }
        String rawMsg = String.join(" ", args);

        TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', config.getString("announce.global-message")
                .replace("{prefix}", config.getString("announce.prefix"))
                .replace("{message}", rawMsg)
        ));

        Collection<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers();

        for(ProxiedPlayer player : players) {
            player.sendMessage(message);
        }
    }
}
