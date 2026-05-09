package me.spectral8420.petPickup.compatibility;

import org.bukkit.Bukkit;

public class CompatibilityChecks {
    public static boolean isWorldGuardPluginInstalled() {
        return Bukkit.getPluginManager().getPlugin("WorldGuard") != null;
    }
}