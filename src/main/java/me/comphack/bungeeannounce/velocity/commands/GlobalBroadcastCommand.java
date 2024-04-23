package me.comphack.bungeeannounce.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.comphack.bungeeannounce.velocity.VelocityAnnounce;
import me.comphack.bungeeannounce.velocity.api.ConfigApi;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.Collection;

public class GlobalBroadcastCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if(args.length <= 0) {
            source.sendMessage(Component.text("Please provide a message to announce"));
            return;
        }
        String rawMsg = String.join(" ", args);

        Component message = LegacyComponentSerializer.legacy('&').deserialize(ConfigApi.get().getString("announce.global-message")
                .replace("{prefix}", ConfigApi.get().getString("announce.prefix"))
                .replace("{message}", rawMsg));

        Collection<Player> players = VelocityAnnounce.getInstance().getServer().getAllPlayers();

        for(Player player : players) {
            player.sendMessage(message);
        }
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("broadcast.command.global");
    }
}
