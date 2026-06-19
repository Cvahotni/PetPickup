package me.spectral8420.petPickup.util;

import me.spectral8420.petPickup.PetPickup;
import me.spectral8420.petPickup.database.RedisDatabase;
import me.spectral8420.petPickup.misc.Settings;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class EggHelper {
    public static ItemStack captureEntityToEgg(Entity entity, PetPickup plugin) {
        if(entity == null) {
            return null;
        }

        Material eggMaterial = getSpawnEggMaterial(entity.getType());

        if(eggMaterial == null) {
            return null;
        }

        ItemStack egg = new ItemStack(eggMaterial);
        SpawnEggMeta meta = (SpawnEggMeta) egg.getItemMeta();

        if(meta != null) {
            EntitySnapshot snapshot = entity.createSnapshot();

            if(snapshot == null) {
                return null;
            }

            boolean didCaptureCorrectly = false;

            switch(Settings.getDatabaseType()) {
                case NBT -> {
                    meta.setSpawnedEntity(snapshot);
                    didCaptureCorrectly = true;
                }

                case REDIS -> {
                    UUID uuid = UUID.randomUUID();

                    NamespacedKey uuidKey = new NamespacedKey(plugin, "databaseUUID");
                    PersistentDataContainer container = meta.getPersistentDataContainer();

                    container.set(uuidKey, PersistentDataType.STRING, uuid.toString());

                    if(!RedisDatabase.exists(uuid.toString())) {
                        didCaptureCorrectly = RedisDatabase.saveString(uuid.toString(), snapshot.getAsString());
                    }
                }
            }

            if(!didCaptureCorrectly) {
                return new ItemStack(Material.AIR, 1);
            }

            else {
                String name = entity.getCustomName() != null ? entity.getCustomName() : entity.getName();
                meta.setDisplayName("§aPortable " + name);

                egg.setItemMeta(meta);
            }
        }

        return egg;
    }

    private static Material getSpawnEggMaterial(@NotNull EntityType type) {
        try {
            return Material.valueOf(type.name() + "_SPAWN_EGG");
        }

        catch (IllegalArgumentException e) {
            return null;
        }
    }
}
