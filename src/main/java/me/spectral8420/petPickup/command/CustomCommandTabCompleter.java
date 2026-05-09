package me.spectral8420.petPickup.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        int index = args.length - 1;

        if(index < 0) {
            index = 0;
        }

        CustomCommand customCommand = CustomCommandManager.getCommand(command.getName());

        if(customCommand == null) {
            return completions;
        }

        if(index >= customCommand.arguments().size()) {
            return completions;
        }

        CustomCommandArgumentType argument = customCommand.arguments().get(index);

        switch(argument) {

        }

        return completions;
    }
}
