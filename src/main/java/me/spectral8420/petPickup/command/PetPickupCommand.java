package me.spectral8420.petPickup.command;

import me.spectral8420.petPickup.PetPickup;
import me.spectral8420.petPickup.misc.Lang;
import me.spectral8420.petPickup.misc.ToggleTracker;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PetPickupCommand extends CustomCommand{
    @Override
    public String getName() {
        return "pp";
    }

    @Override
    public List<CustomCommandArgumentType> arguments() {
        return List.of();
    }

    @Override
    public void onCommand(PetPickup plugin, CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(Lang.getMessage("notPlayer", true));
            return;
        }

        ToggleTracker.setStatus(player.getUniqueId(), !ToggleTracker.getStatus(player.getUniqueId()));

        if(ToggleTracker.getStatus(player.getUniqueId())) {
            player.sendMessage(Lang.getMessage("toggleOn", true));
        }

        else {
            player.sendMessage(Lang.getMessage("toggleOff", true));
        }
    }
}
