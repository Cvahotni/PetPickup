package me.spectral8420.petPickup.event;

import me.spectral8420.petPickup.PetPickup;
import me.spectral8420.petPickup.compatibility.CompatibilityChecks;
import me.spectral8420.petPickup.compatibility.WorldGuardCompatibility;
import me.spectral8420.petPickup.database.RedisDatabase;
import me.spectral8420.petPickup.misc.Lang;
import me.spectral8420.petPickup.misc.Settings;
import me.spectral8420.petPickup.misc.ToggleTracker;
import me.spectral8420.petPickup.util.ConsoleHelper;
import me.spectral8420.petPickup.util.EggHelper;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityMountEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class MainListener implements Listener {
    private final PetPickup plugin;

    public MainListener(PetPickup plugin) {
        this.plugin = plugin;
    }

    private static final String nameKey = "pet_pickup_name";
    private static final String nameVisibleKey = "pet_pickup_name_visible";

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();

        if(event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if(!ToggleTracker.getStatus(player.getUniqueId())) {
            return;
        }

        Entity entity = event.getRightClicked();

        if(CompatibilityChecks.isWorldGuardPluginInstalled()) {
            if(!WorldGuardCompatibility.checkWorldGuard(entity.getLocation()) && !player.hasPermission("pp.bypass.worldguard")) {
                player.sendMessage(Lang.getMessage("noPermissionPickup", true));
                return;
            }
        }

        if(!Settings.isEntityTypeWhitelisted(entity.getType()) && !player.hasPermission("pp.bypass.whitelist")) {
            player.sendMessage(Lang.getMessage("entityNotWhitelisted", true));
            return;
        }

        if((entity instanceof Tameable tameable)) {
            AnimalTamer tamer = tameable.getOwner();

            if(tamer != null) {
                UUID owner = tamer.getUniqueId();

                if(!player.getUniqueId().equals(owner) && !player.hasPermission("pp.bypass.pickup")) {
                    player.sendMessage(Lang.getMessage("notOwnerPickup", true));
                    event.setCancelled(true);

                    return;
                }
            }
        }

        if(entity.getType() == EntityType.PLAYER) {
            return;
        }

        ItemStack egg = EggHelper.captureEntityToEgg(entity, plugin);

        if(egg.getType() == Material.AIR) {
            player.sendMessage(Lang.getMessage("pickupFail", true));
            event.setCancelled(true);

            return;
        }

        ItemMeta meta = egg.getItemMeta();

        if(meta == null) {
            return;
        }

        NamespacedKey currentNameKey = new NamespacedKey(plugin, nameKey);
        NamespacedKey currentNameVisibleKey = new NamespacedKey(plugin, nameVisibleKey);

        String customName = entity.getCustomName();

        meta.getPersistentDataContainer().set(currentNameKey, PersistentDataType.STRING, customName == null ? "" : customName);
        meta.getPersistentDataContainer().set(currentNameVisibleKey, PersistentDataType.BOOLEAN, customName != null);

        entity.remove();

        egg.setItemMeta(meta);
        inventory.addItem(egg);
    }

    @EventHandler
    public void onMount(EntityMountEvent event) {
        if(!(event.getEntity() instanceof Player player)) {
            return;
        }

        Entity mount = event.getMount();

        if((mount instanceof Tameable tameable)) {
            AnimalTamer tamer = tameable.getOwner();

            if(tamer != null) {
                UUID owner = tamer.getUniqueId();

                if(!player.getUniqueId().equals(owner) && !player.hasPermission("petpickup.bypass.ride")) {
                    player.sendMessage(Lang.getMessage("notOwnerRide", true));
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onSpawnEggPlace(PlayerInteractEvent event) {
        if(event.getAction() != org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if(event.getItem() == null || !event.getItem().getType().toString().contains("SPAWN_EGG")) {
            return;
        }

        Player player = event.getPlayer();

        Block clickedBlock = event.getClickedBlock();

        if(clickedBlock == null) {
            return;
        }

        if(CompatibilityChecks.isWorldGuardPluginInstalled()) {
            if(!WorldGuardCompatibility.checkWorldGuard(clickedBlock.getLocation()) && !player.hasPermission("petpickup.bypass.worldguard")) {
                player.sendMessage(Lang.getMessage("noPermissionPlace", true));
                event.setCancelled(true);

                return;
            }
        }

        ItemStack egg = event.getItem();
        ItemMeta meta = egg.getItemMeta();

        if(meta == null) {
            return;
        }

        if(!(meta instanceof SpawnEggMeta spawnEggMeta)) {
            return;
        }

        World world = clickedBlock.getWorld();
        EntitySnapshot snapshot = null;

        String redisKey = "";

        switch (Settings.getDatabaseType()) {
            case NBT -> snapshot = spawnEggMeta.getSpawnedEntity();

            case REDIS -> {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                NamespacedKey uuidKey = new NamespacedKey(plugin, "databaseUUID");

                if(container.has(uuidKey, PersistentDataType.STRING)) {
                    redisKey = container.get(uuidKey, PersistentDataType.STRING);
                }

                String redisString = RedisDatabase.getString(redisKey);

                if(redisString != null) {
                    if(!redisString.isEmpty()) {
                        try {
                            EntityFactory factory = Bukkit.getEntityFactory();
                            snapshot = factory.createEntitySnapshot(redisString);
                        }

                        catch (Exception e) {
                            ConsoleHelper.sendMessage(ChatColor.RED + "Failed to convert entity database string to entity snapshot: " + e);
                        }
                    }
                }
            }
        }

        if(snapshot == null) {
            player.sendMessage(Lang.getMessage("placeFail", true));
            event.setCancelled(true);

            return;
        }

        NamespacedKey currentNameKey = new NamespacedKey(plugin, nameKey);
        NamespacedKey currentNameVisibleKey = new NamespacedKey(plugin, nameVisibleKey);

        if(!meta.getPersistentDataContainer().has(currentNameKey, PersistentDataType.STRING) ||
                !meta.getPersistentDataContainer().has(currentNameVisibleKey, PersistentDataType.BOOLEAN)) {

            return;
        }

        String entityName = meta.getPersistentDataContainer().get(currentNameKey, PersistentDataType.STRING);
        boolean entityNameVisible = Boolean.TRUE.equals(meta.getPersistentDataContainer().get(currentNameVisibleKey, PersistentDataType.BOOLEAN));

        Entity entity = snapshot.createEntity(world);

        entity.setCustomName(entityName);
        entity.setCustomNameVisible(entityNameVisible);

        Location spawnLocation = clickedBlock.getLocation();

        spawnLocation.add(0.5, 0.5, 0.5);
        spawnLocation.add(event.getBlockFace().getDirection());

        switch(Settings.getDatabaseType()) {
            case REDIS -> {
                if(!RedisDatabase.deleteKey(redisKey)) {
                    ConsoleHelper.sendMessage(Lang.getMessage("placeFail", true));
                    event.setCancelled(true);

                    return;
                }
            }
        }

        event.getItem().subtract(1);
        entity.spawnAt(spawnLocation);

        event.setCancelled(true);
    }
}
