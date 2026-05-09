package me.spectral8420.petPickup.compatibility;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionType;
import me.spectral8420.petPickup.util.ConsoleHelper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

public class WorldGuardCompatibility {
    public static StateFlag PET_PICKUP;

    public static boolean checkWorldGuard(Location location) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        World world = location.getWorld();

        if(world == null) {
            return true;
        }

        RegionManager regionManager = container.get(BukkitAdapter.adapt(world));

        if(regionManager == null) {
            return true;
        }

        if(regionManager.getRegions() == null) {
            return true;
        }

        if(regionManager.getRegions().isEmpty()) {
            return true;
        }

        for(ProtectedRegion set : regionManager.getRegions().values()) {
            if(!set.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                continue;
            }

            if(set.getType() == RegionType.GLOBAL) {
                continue;
            }

            StateFlag.State state = set.getFlag(WorldGuardCompatibility.getPetPickup());

            if(state == StateFlag.State.DENY) {
                return false;
            }
        }

        return true;
    }

    public static void registerCustomWorldGuardFlag() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

        try {
            StateFlag flag = new StateFlag("pet-pickup", false);
            registry.register(flag);
            WorldGuardCompatibility.setPetPickup(flag);
        }

        catch(FlagConflictException e) {
            Flag<?> existing = registry.get("pet-pickup");

            if(existing instanceof StateFlag) {
                WorldGuardCompatibility.setPetPickup((StateFlag) existing);
            }

            else {
                ConsoleHelper.sendMessage(ChatColor.RED + "Could not register world guard flag: " + e.getMessage());
            }
        }
    }

    public static StateFlag getPetPickup() {
        return PET_PICKUP;
    }

    public static void setPetPickup(StateFlag petPickup) {
        PET_PICKUP = petPickup;
    }
}