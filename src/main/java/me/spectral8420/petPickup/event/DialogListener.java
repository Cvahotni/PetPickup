package me.spectral8420.petPickup.event;

import io.papermc.paper.connection.PlayerGameConnection;
import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.event.player.PlayerCustomClickEvent;
import me.spectral8420.petPickup.PetPickup;
import me.spectral8420.petPickup.misc.Lang;
import me.spectral8420.petPickup.misc.Settings;
import me.spectral8420.petPickup.util.ConsoleHelper;
import me.spectral8420.petPickup.util.EggHelper;
import net.kyori.adventure.key.Key;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class DialogListener implements Listener {
    private final PetPickup plugin;

    public DialogListener(PetPickup plugin) {
        this.plugin = plugin;
    }

    private final Key cancelKey = Key.key("petpickup:cancel");
    private final Key submitKey = Key.key("petpickup:submit");

    @EventHandler
    public void onDialogSubmit(PlayerCustomClickEvent event) {
        if(!event.getIdentifier().equals(cancelKey) && !event.getIdentifier().equals(submitKey)) {
            return;
        }

        DialogResponseView responseView = event.getDialogResponseView();

        if(responseView == null) {
            return;
        }

        try {
            String newPetName = responseView.getText("pet_name");
            Player player = ((PlayerGameConnection) event.getCommonConnection()).getPlayer();

            if(newPetName == null) {
                player.sendMessage(Lang.getMessage("renamePromptError", true));
                return;
            }

            if(newPetName.isEmpty()) {
                player.sendMessage(Lang.getMessage("renamePromptError", true));
                return;
            }

            if(newPetName.length() >= Settings.getMaxRenameLength()) {
                player.sendMessage(Lang.getMessage("renameTooLong", true).replace("{0}", "" + Settings.getMaxRenameLength()));
                return;
            }

            ItemStack heldItem = player.getInventory().getItemInMainHand();

            if(!EggHelper.isEgg(heldItem)) {
                player.sendMessage(Lang.getMessage("renameNotEgg", true));
                return;
            }

            EggHelper.renameEgg(plugin, heldItem, player, newPetName);
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Excecption whilst submitting rename dialog prompt: " + e);
        }
    }
}
