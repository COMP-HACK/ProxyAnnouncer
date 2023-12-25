package me.comphack.bungeeannounce.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import me.comphack.bungeeannounce.velocity.VelocityAnnounce;
import me.comphack.bungeeannounce.velocity.api.ConfigApi;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.Arrays;
import java.util.Optional;

public class ServerBroadcastCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length <= 1) {
            source.sendMessage(LegacyComponentSerializer.legacy('&').deserialize("&cUsage: /announceserver <server> <message...>"));
            return;
        }

        String serverName = args[0];
        Optional<RegisteredServer> server = VelocityAnnounce.getInstance().getServer().getServer(serverName);

        if (!server.isPresent()) {
            source.sendMessage(LegacyComponentSerializer.legacy('&').deserialize("&cServer was not found."));
            return;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        Component formattedMessage = LegacyComponentSerializer.legacy('&').deserialize(ConfigApi.get().getString("announce.server-message")
                .replace("{prefix}", ConfigApi.get().getString("announce.prefix"))
                .replace("{message}", message)
                .replace("{server}", server.get().getServerInfo().getName()));

// Broadcast the formatted message to the specified server
        for (Player player : server.get().getPlayersConnected()) {
            player.sendMessage(formattedMessage);
        }

// Send success message to the command sender
        source.sendMessage(LegacyComponentSerializer.legacy('&').deserialize("&aAnnouncement sent to " + server.get().getServerInfo().getName() + ": " + message));

    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("broadcast.command.server");
    }
}
