package me.spectral8420.petPickup.misc;

import me.spectral8420.petPickup.config.CustomConfig;
import me.spectral8420.petPickup.config.CustomConfigManager;
import me.spectral8420.petPickup.database.DatabaseType;
import me.spectral8420.petPickup.util.ConsoleHelper;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    private static List<String> whitelistedTypes = new ArrayList<>();
    private static DatabaseType databaseType;

    private static String databaseIP = "";
    private static int dataBasePort = 0;

    private static String databaseName = "";
    private static String databasePassword = "";

    private static int maxRenameLength = 32;

    private static boolean swapToLegacyWhenNeeded = true;
    private static boolean mythicMobsPatch = true;

    private static boolean shouldNotifyOnSaveNow = false;

    public static void getData() {
        CustomConfig settingsConfig = CustomConfigManager.getConfig("settings");

        try {
            if(settingsConfig.has("whitelistedTypes")) {
                whitelistedTypes = (List<String>) settingsConfig.get("whitelistedTypes");
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to load whitelistedTypes from settings due to an exception: " + e);
        }

        try {
            if(settingsConfig.has("databaseType")) {
                databaseType = DatabaseType.valueOf((String) settingsConfig.get("databaseType"));
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to load database type from settings due to an exception: " + e);
        }

        try {
            if(settingsConfig.has("databaseIP")) {
                databaseIP = (String) settingsConfig.get("databaseIP");
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to load databaseIP from settings due to an exception: " + e);
        }

        try {
            if(settingsConfig.has("databasePort")) {
                dataBasePort = Integer.parseInt((String) settingsConfig.get("databasePort"));
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to load databasePort from settings due to an exception: " + e);
        }

        try {
            if(settingsConfig.has("databaseName")) {
                databaseName = (String) settingsConfig.get("databaseName");
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to load databaseName from settings due to an exception: " + e);
        }

        try {
            if(settingsConfig.has("databasePassword")) {
                databasePassword = (String) settingsConfig.get("databasePassword");
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to load databasePassword from settings due to an exception: " + e);
        }

        try {
            if(settingsConfig.has("maxRenameLength")) {
                maxRenameLength = settingsConfig.getInt("maxRenameLength");
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to load maxRenameLength from settings due to an exception: " + e);
        }

        try {
            if(settingsConfig.has("swapToLegacyWhenNeeded")) {
                swapToLegacyWhenNeeded = (Boolean) settingsConfig.get("swapToLegacyWhenNeeded");
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to load swapToLegacyWhenNeeded from settings due to an exception: " + e);
        }

        try {
            if(settingsConfig.has("mythicMobsPatch")) {
                mythicMobsPatch = (Boolean) settingsConfig.get("mythicMobsPatch");
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to load mythicMobsPatch from settings due to an exception: " + e);
        }
    }

    public static void setData() {
        CustomConfig settingsConfig = CustomConfigManager.getConfig("settings");

        try {
            if(!settingsConfig.has("whitelistedTypes")) {
                shouldNotifyOnSaveNow = true;
                settingsConfig.set("whitelistedTypes", whitelistedTypes);
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to save whitelistedTypes to settings due to an exception: " + e);
        }

        try {
            if(!settingsConfig.has("databaseType")) {
                shouldNotifyOnSaveNow = true;
                settingsConfig.set("databaseType", databaseType);
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to save database type to settings due to an exception: " + e);
        }

        try {
            if(!settingsConfig.has("databaseIP")) {
                shouldNotifyOnSaveNow = true;
                settingsConfig.set("databaseIP", databaseIP);
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to save databaseIP to settings due to an exception: " + e);
        }

        try {
            if(!settingsConfig.has("databasePort")) {
                shouldNotifyOnSaveNow = true;
                settingsConfig.set("databasePort", dataBasePort);
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to save databasePort to settings due to an exception: " + e);
        }

        try {
            if(!settingsConfig.has("databaseName")) {
                shouldNotifyOnSaveNow = true;
                settingsConfig.set("databaseName", databaseName);
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to save databaseName to settings due to an exception: " + e);
        }

        try {
            if(!settingsConfig.has("databasePassword")) {
                shouldNotifyOnSaveNow = true;
                settingsConfig.set("databasePassword", databasePassword);
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to save databasePassword to settings due to an exception: " + e);
        }

        try {
            if(!settingsConfig.has("maxRenameLength")) {
                shouldNotifyOnSaveNow = true;
                settingsConfig.setInt("maxRenameLength", maxRenameLength);
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to save maxRenameLength to settings due to an exception: " + e);
        }

        try {
            if(!settingsConfig.has("swapToLegacyWhenNeeded")) {
                shouldNotifyOnSaveNow = true;
                settingsConfig.set("swapToLegacyWhenNeeded", swapToLegacyWhenNeeded);
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to save swapToLegacyWhenNeeded to settings due to an exception: " + e);
        }

        try {
            if(!settingsConfig.has("mythicMobsPatch")) {
                shouldNotifyOnSaveNow = true;
                settingsConfig.set("mythicMobsPatch", mythicMobsPatch);
            }
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to save mythicMobsPatch to settings due to an exception: " + e);
        }

        settingsConfig.save();

        if(shouldNotifyOnSaveNow) {
            ConsoleHelper.sendMessage(
                    ChatColor.RED +
                            "Some values in the settings.yml file were reset, as they were not found, or they were not loaded correctly, " +
                            "if problems continue, you may need to delete the settings.yml manually, and try again"
            );
        }
    }

    public static boolean isEntityTypeWhitelisted(EntityType type) {
        return whitelistedTypes.contains(type.toString());
    }

    public static DatabaseType getDatabaseType() {
        return databaseType;
    }

    public static String getDatabaseIP() {
        return databaseIP;
    }

    public static int getDataBasePort() {
        return dataBasePort;
    }

    public static String getDatabaseName() {
        return databaseName;
    }

    public static String getDatabasePassword() {
        return databasePassword;
    }

    public static int getMaxRenameLength() {
        return maxRenameLength;
    }

    public static boolean isSwapToLegacyWhenNeeded() {
        return swapToLegacyWhenNeeded;
    }

    public static boolean isMythicMobsPatch() {
        return mythicMobsPatch;
    }
}
