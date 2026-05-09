package me.spectral8420.petPickup.misc;

import me.spectral8420.petPickup.config.CustomConfig;
import me.spectral8420.petPickup.config.CustomConfigManager;
import me.spectral8420.petPickup.util.ConsoleHelper;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    private static List<String> whitelistedTypes = new ArrayList<>();

    public static void getData() {
        CustomConfig settingsConfig = CustomConfigManager.getConfig("settings");

        try {
            if(settingsConfig.has("whitelistedTypes")) {
                whitelistedTypes = (List<String>) settingsConfig.get("whitelistedTypes");
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to load settings due to an exception: " + e);
        }
    }

    public static boolean isEntityTypeWhitelisted(EntityType type) {
        return whitelistedTypes.contains(type.toString());
    }
}
