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
}
