package com.rinpr.machineprocessed.Utilities;

import com.rinpr.machineprocessed.DataManager.SQLiteManager;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class MachineInChunk {
    private final Chunk chunk;
    private final SQLiteManager sqLiteManager = new SQLiteManager();

    /**
     * This is for putting chunk in to get some data.
     * @param chunk to read data.
     */
    public MachineInChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * This method is used to get all machine location directly from sqlite database.
     * @return All location of the machine in the chunk specified.
     */
    public Set<Location> getMachine() {
        Set<Location> result = new HashSet<>();
        for (Location location : sqLiteManager.getAllMachineLocation()) {
            if (location.getBlockX() >> 4 == chunk.getX() && location.getBlockZ() >> 4 == chunk.getZ()) { result.add(location); }
        }
        return result;
    }
}
