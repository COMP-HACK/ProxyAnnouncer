package me.comphack.bungeeannounce.velocity.api;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;

public class CommandApi {
    public static void registerCommand(Object plugin, CommandManager commandManager, Command command, String name, String... args) {
        CommandMeta commandMeta;
        if (args.length == 0) {
            commandMeta = commandManager.metaBuilder(name)
                    .plugin(plugin)
                    .build();

        } else {
            commandMeta = commandManager.metaBuilder(name)
                    .aliases(args)
                    .plugin(plugin)
                    .build();

        }
        commandManager.register(commandMeta, command);
    }
}
