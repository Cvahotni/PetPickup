package me.spectral8420.petPickup.config;

import me.spectral8420.petPickup.PetPickup;

import java.util.HashMap;

public class CustomConfigManager {
    private static final HashMap<String, CustomConfig> configs = new HashMap<>();

    public static void register() {
        configs.put("lang", new CustomConfig("lang", "lang.yml", false, true));
        configs.put("settings", new CustomConfig("settings", "settings.yml", false, true));
    }

    public static void load(PetPickup plugin) {
        for(String key : configs.keySet()) {
            configs.get(key).load(plugin);
        }
    }

    public static void save() {
        for(String key : configs.keySet()) {
            CustomConfig config = configs.get(key);

            if(config.isSaveOnDisable()) {
                config.save();
            }
        }
    }

    public static CustomConfig getConfig(String name) {
        return configs.get(name);
    }
}
