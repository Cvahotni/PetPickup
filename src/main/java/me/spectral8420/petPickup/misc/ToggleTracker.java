package me.spectral8420.petPickup.misc;

import java.util.HashMap;
import java.util.UUID;

public class ToggleTracker {
    private static final HashMap<UUID, Boolean> status = new HashMap<>();

    public static boolean getStatus(UUID uuid) {
        if(!status.containsKey(uuid)) {
            return false;
        }

        return status.get(uuid);
    }

    public static void setStatus(UUID uuid, boolean value) {
        if(status.containsKey(uuid)) {
            status.replace(uuid, value);
        }

        else {
            status.put(uuid, value);
        }
    }
}
