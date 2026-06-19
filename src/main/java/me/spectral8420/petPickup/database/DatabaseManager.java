package me.spectral8420.petPickup.database;

import me.spectral8420.petPickup.misc.Settings;
import me.spectral8420.petPickup.util.ConsoleHelper;
import org.bukkit.ChatColor;

public class DatabaseManager {
    public static boolean initTargetDatabase() {
        DatabaseType type = Settings.getDatabaseType();

        switch(type) {
            case NBT -> {
                ConsoleHelper.sendMessage("Using item NBT as a database, no need for connection.");
                return true;
            }

            case REDIS -> {
                return RedisDatabase.connect(
                        Settings.getDatabaseIP(),
                        Settings.getDataBasePort(),
                        Settings.getDatabasePassword()
                );
            }
        }

        return false;
    }

    public static void closeTargetDatabase() {
        DatabaseType type = Settings.getDatabaseType();

        switch(type) {
            case REDIS -> RedisDatabase.close();
        }

        ConsoleHelper.sendMessage(ChatColor.WHITE + "Attempted to close connections to the existing target database.");
    }
}
