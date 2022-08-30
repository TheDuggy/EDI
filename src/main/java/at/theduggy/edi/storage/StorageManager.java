package at.theduggy.edi.storage;

import at.theduggy.edi.EDIManager;

import java.util.HashMap;
import java.util.UUID;

public class StorageManager {

    private static final HashMap<UUID, EDIManager> ediPlayerData = new HashMap<>();
    public static HashMap<UUID, EDIManager> getEDIData() {
        return ediPlayerData;
    }

}
