package me.spectral8420.petPickup.command;

import me.spectral8420.petPickup.PetPickup;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class CustomCommand {
    public abstract String getName();
    public abstract List<CustomCommandArgumentType> arguments();
    public abstract void onCommand(PetPickup plugin, CommandSender sender, String[] args);
}
