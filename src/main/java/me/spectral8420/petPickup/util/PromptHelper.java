package me.spectral8420.petPickup.util;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import me.spectral8420.petPickup.misc.Lang;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

import static net.kyori.adventure.text.Component.text;

public class PromptHelper {
    public static void showRenamePrompt(Player player) {
        if(!ServerHelper.canShowModernPrompt()) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Reminder for Spectral: This server doesn't support modern prompts, please look into this!");
            return;
        }

        String promptTitle = Lang.getMessage("renamePromptTitle", false);
        String promptDesc = Lang.getMessage("renamePrompt", false);

        if(promptTitle.isEmpty() || promptDesc.isEmpty()) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to open rename prompt, title and or desc was empty, please check the lang file");
            return;
        }

        Dialog dialog = Dialog.create(builder -> builder.empty()
                .base(DialogBase.builder(text(promptTitle)).
                        canCloseWithEscape(true).
                        body(List.of(
                                DialogBody.plainMessage(Component.text(promptDesc))
                        )).
                        inputs(List.of(
                        DialogInput.text("pet_name", 200, Component.text("Name"), true, "", 32, null)
                )).build())
                .type(DialogType.confirmation(
                        ActionButton.builder(Component.text("Submit", NamedTextColor.GREEN))
                                .action(DialogAction.customClick(Key.key("petpickup:submit"), null)).build(),

                        ActionButton.builder(Component.text("Cancel", NamedTextColor.RED))
                                .action(DialogAction.customClick(Key.key("petpickup:cancel"), null)).build()
                ))
        );

        player.showDialog(dialog);
    }
}
