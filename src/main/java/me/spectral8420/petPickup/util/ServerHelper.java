package me.spectral8420.petPickup.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ServerHelper {
    public static int getServerVersion() {
        String version = Bukkit.getServer().getMinecraftVersion();
        String[] split = version.split("\\.");

        try {
            if(split.length == 3) {
                int major = Integer.parseInt(split[1]);
                int minor = Integer.parseInt(split[2]);

                return (major * 100) + minor;
            }

            else if(split.length == 2) {
                int major = Integer.parseInt(split[0]);
                int minor = Integer.parseInt(split[1]);

                return (major * 100) + minor;
            }
        }

        catch (NumberFormatException e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Unable to find correct server version due to an exception: " + e);
            return -1;
        }

        return -1;
    }

    public static boolean isPaperServer() {
        return Bukkit.getName().contains("Paper");
    }

    public static boolean canShowModernPrompt() {
        int requiredPromptVersion = 1206;
        return getServerVersion() >= requiredPromptVersion && isPaperServer();
    }
}
