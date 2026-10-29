package me.spectral8420.petPickup.command;

import me.spectral8420.petPickup.PetPickup;
import me.spectral8420.petPickup.misc.Lang;
import me.spectral8420.petPickup.misc.Settings;
import me.spectral8420.petPickup.util.EggHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LegacyRenameCommand extends CustomCommand{
    @Override
    public String getName() {
        return "pprenamelegacy";
    }

    @Override
    public List<CustomCommandArgumentType> arguments() {
        return List.of(CustomCommandArgumentType.ANY_STRING);
    }

    @Override
    public void onCommand(PetPickup plugin, CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(Lang.getMessage("notPlayer", true));
            return;
        }

        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if(!EggHelper.isEgg(heldItem)) {
            sender.sendMessage(Lang.getMessage("renameNotEgg", true));
            return;
        }

        if(args.length < 1) {
            sender.sendMessage(Lang.getMessage("renamePromptError", true));
            return;
        }

        List<String> nextArgs = new ArrayList<>();
        nextArgs.addAll(Arrays.asList(args).subList(0, args.length));

        if(nextArgs.isEmpty()) {
            sender.sendMessage(Lang.getMessage("renamePromptError", true));
            return;
        }

        String newPetName = String.join(" ", nextArgs);

        if(newPetName.length() >= Settings.getMaxRenameLength()) {
            sender.sendMessage(Lang.getMessage("renameTooLong", true).replace("{0}", "" + Settings.getMaxRenameLength()));
            return;
        }

        EggHelper.renameEgg(plugin, heldItem, player, newPetName);
    }
}
