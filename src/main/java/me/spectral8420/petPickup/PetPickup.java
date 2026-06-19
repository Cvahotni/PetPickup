package me.spectral8420.petPickup;

import me.spectral8420.petPickup.command.CustomCommandManager;
import me.spectral8420.petPickup.compatibility.CompatibilityChecks;
import me.spectral8420.petPickup.compatibility.WorldGuardCompatibility;
import me.spectral8420.petPickup.config.CustomConfigManager;
import me.spectral8420.petPickup.database.DatabaseManager;
import me.spectral8420.petPickup.event.MainListener;
import me.spectral8420.petPickup.misc.Lang;
import me.spectral8420.petPickup.misc.Settings;
import me.spectral8420.petPickup.util.ConsoleHelper;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class PetPickup extends JavaPlugin {
    @Override
    public void onEnable() {
        CustomCommandManager.registerAllCommands(this);

        CustomConfigManager.register();
        CustomConfigManager.load(this);

        Lang.getData();
        Settings.getData();

        if(CompatibilityChecks.isWorldGuardPluginInstalled()) {
            WorldGuardCompatibility.registerCustomWorldGuardFlag();
        }

        if(!DatabaseManager.initTargetDatabase()) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Failed to initialize database, try and check previous console errors!");
        }

        else {
            ConsoleHelper.sendMessage(ChatColor.GREEN + "Database successfully initialized!");
        }

        getServer().getPluginManager().registerEvents(new MainListener(this), this);
        ConsoleHelper.sendMessage(ChatColor.GREEN + "PetPickup has been enabled!");
    }

    @Override
    public void onDisable() {
        CustomConfigManager.save();
        DatabaseManager.closeTargetDatabase();

        ConsoleHelper.sendMessage(ChatColor.RED + "PetPickup has been disabled!");
    }
}
