package me.spectral8420.petPickup.config;

import me.spectral8420.petPickup.PetPickup;
import me.spectral8420.petPickup.util.ConsoleHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class CustomConfig {
    private File customConfigFile;
    private YamlConfiguration customConfig;
    private final String name;
    private final String path;
    private final boolean saveOnDisable;
    private final boolean loadFromInternalResources;

    public CustomConfig(String name, String path, boolean saveOnDisable, boolean loadFromInternalResources) {
        this.name = name;
        this.path = path;
        this.saveOnDisable = saveOnDisable;
        this.loadFromInternalResources = loadFromInternalResources;
    }

    public boolean has(String path) {
        return customConfig.isSet(path);
    }

    public Object get(String path) {
        return customConfig.get(path);
    }

    public ItemStack getItem(String path) {
        if(!customConfig.isSet(path)) {
            return new ItemStack(Material.AIR);
        }

        if(!customConfig.isItemStack(path)) {
            return new ItemStack(Material.AIR);
        }

        return customConfig.getItemStack(path);
    }

    public void set(String path, Object data) {
        customConfig.set(path, data);
    }

    public void save() {
        try {
            customConfig.save(customConfigFile);
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Exception whilst saving config " + name + ": " + e);
        }
    }

    public void load(PetPickup plugin) {
        try {
            createCustomConfig(plugin);
            customConfig.load(customConfigFile);
        }

        catch (IOException | InvalidConfigurationException e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Exception whilst loading config " + name + ": " + e);
        }
    }

    public void createCustomConfig(PetPickup plugin) {
        customConfigFile = new File(plugin.getDataFolder(), path);

        if(!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            if(loadFromInternalResources) {
                copyFromPluginResources();
            }
        }

        customConfig = new YamlConfiguration();
    }

    private void copyFromPluginResources() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);

            if(inputStream == null) {
                ConsoleHelper.sendMessage(ChatColor.RED + "Failed to load config file from internal resources: " + path);
                return;
            }

            Files.copy(inputStream, customConfigFile.toPath());
        }

        catch (Exception e) {
            ConsoleHelper.sendMessage(ChatColor.RED + "Exception whilst copying config " + name + "from plugin resources: " + e);
        }
    }

    public boolean delete() {
        if(customConfigFile == null) {
            return false;
        }

        if(!customConfigFile.exists()) {
            return false;
        }

        return customConfigFile.delete();
    }

    public boolean isSaveOnDisable() {
        return saveOnDisable;
    }
}