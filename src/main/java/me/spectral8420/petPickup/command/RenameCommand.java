package me.spectral8420.petPickup.command;

import me.spectral8420.petPickup.PetPickup;
import me.spectral8420.petPickup.misc.Lang;
import me.spectral8420.petPickup.misc.Settings;
import me.spectral8420.petPickup.util.EggHelper;
import me.spectral8420.petPickup.util.PromptHelper;
import me.spectral8420.petPickup.util.ServerHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RenameCommand extends CustomCommand {
    @Override
    public String getName() {
        return "pprename";
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

        if(!ServerHelper.canShowModernPrompt() && !Settings.isSwapToLegacyWhenNeeded()) {
            sender.sendMessage(Lang.getMessage("renameNotModern", true));
            return;
        }

        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if(!EggHelper.isEgg(heldItem)) {
            sender.sendMessage(Lang.getMessage("renameNotEgg", true));
            return;
        }

        if(Settings.isSwapToLegacyWhenNeeded() && !ServerHelper.canShowModernPrompt()) {
            List<String> nextArgs = new ArrayList<>();
            nextArgs.addAll(Arrays.asList(args).subList(0, args.length));

            String newPetName = String.join(" ", nextArgs);
            player.performCommand("pprenamelegacy " + newPetName);
        }

        else {
            PromptHelper.showRenamePrompt(player);
        }
    }
}
