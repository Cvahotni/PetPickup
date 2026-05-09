package me.spectral8420.petPickup.util;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.jetbrains.annotations.NotNull;

public class EggHelper {
    public static ItemStack captureEntityToEgg(Entity entity) {
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

            meta.setSpawnedEntity(snapshot);

            String name = entity.getCustomName() != null ? entity.getCustomName() : entity.getName();
            meta.setDisplayName("§aPortable " + name);

            egg.setItemMeta(meta);
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
