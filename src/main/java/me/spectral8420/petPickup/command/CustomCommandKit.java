package me.spectral8420.petPickup.command;

import me.spectral8420.petPickup.PetPickup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CustomCommandKit implements CommandExecutor {
    private final PetPickup plugin;

    public CustomCommandKit(PetPickup plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        CustomCommand customCommand = CustomCommandManager.getCommand(command.getName());

        if(customCommand == null) {
            return false;
        }

        customCommand.onCommand(plugin, commandSender, args);
        return false;
    }
}
