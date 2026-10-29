package me.spectral8420.petPickup.misc;

import me.spectral8420.petPickup.PetPickup;
import me.spectral8420.petPickup.config.CustomConfig;
import me.spectral8420.petPickup.config.CustomConfigManager;
import me.spectral8420.petPickup.util.ConsoleHelper;
import org.bukkit.ChatColor;

import java.util.HashMap;

public class Lang {
    private static final HashMap<String, String> messages = new HashMap<>();
    private static final HashMap<String, String> presetMessages = new HashMap<>();

    private static boolean shouldRepairLangFile = false;

    public static void getData() {
        CustomConfig langConfig = CustomConfigManager.getConfig("lang");

        addMessage("prefix", langConfig);
        addMessage("toggleOff", langConfig);
        addMessage("toggleOn", langConfig);
        addMessage("noPermissionPickup", langConfig);
        addMessage("noPermissionPlace", langConfig);
        addMessage("notOwnerPickup", langConfig);
        addMessage("notOwnerRide", langConfig);
        addMessage("entityNotWhitelisted", langConfig);
        addMessage("notPlayer", langConfig);
        addMessage("pickupFail", langConfig);
        addMessage("placeFail", langConfig);
        addMessage("renameNotEgg", langConfig);
        addMessage("renamePromptTitle", langConfig);
        addMessage("renamePrompt", langConfig);
        addMessage("renamePromptError", langConfig);
        addMessage("renameNotLiving", langConfig);
        addMessage("renameNotModern", langConfig);
        addMessage("renameTooLong", langConfig);
        addMessage("renameDone", langConfig);
    }

    public static void setData(PetPickup plugin) {
        CustomConfig langConfig = CustomConfigManager.getConfig("lang");

        for(String key : presetMessages.keySet()) {
            setMessageIfNotFound(key, langConfig);
        }

        langConfig.save();

        if(shouldRepairLangFile) {
            langConfig.delete();

            langConfig.load(plugin);
            langConfig.save();
        }
    }

    public static void addMessage(String key, CustomConfig config) {
        presetMessages.put(key, "");

        if(!config.has(key)) {
            ConsoleHelper.sendMessage(ChatColor.RED + "The current language file will be repaired, as the language key was not found: " + key);

            shouldRepairLangFile = true;
            return;
        }

        try {
            messages.put(key, (String) config.get(key));
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to load message in language file due to an exception: " + e);

            if(!shouldRepairLangFile) {
                ConsoleHelper.sendMessage(ChatColor.RED + "The current language file will be repaired due to this");
                shouldRepairLangFile = true;
            }
        }
    }

    public static void setMessageIfNotFound(String key, CustomConfig config) {
        try {
            if(!config.has(key)) {
                config.set(key, messages.get(key));
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to set message in language file due to an exception: " + e);
        }
    }

    public static String getMessage(String key, boolean includePrefix) {
        if(!messages.containsKey(key)) {
            return key;
        }

        String message = includePrefix ? messages.get("prefix") + messages.get(key) : messages.get(key);

        if(message == null) {
            return key;
        }

        return message.isEmpty() ? key : message;
    }
}
