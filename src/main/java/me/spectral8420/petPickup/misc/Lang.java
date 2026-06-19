package me.spectral8420.petPickup.misc;

import me.spectral8420.petPickup.config.CustomConfig;
import me.spectral8420.petPickup.config.CustomConfigManager;
import me.spectral8420.petPickup.util.ConsoleHelper;
import org.bukkit.ChatColor;

import java.util.HashMap;

public class Lang {
    private static final HashMap<String, String> messages = new HashMap<>();

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
    }

    public static void addMessage(String key, CustomConfig config) {
        try {
            messages.put(key, (String) config.get(key));
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to load message in language file due to an exception: " + e);
        }
    }

    public static String getMessage(String key, boolean includePrefix) {
        if(!messages.containsKey(key)) return key;
        return includePrefix ? messages.get("prefix") + messages.get(key) : messages.get(key);
    }
}
