package com.rinpr.machineprocessed.Utilities;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class SmartGive {
    private final Inventory inv;
    private final Location loc;

    public SmartGive(Player player) {
        inv = player.getInventory();
        loc = player.getLocation();
    }

    /*
     * either give directly the item to the player or drops it on the ground if
     * there is not enough space in the player inventory.
     */
    public void give(ItemStack... item) {
        for (ItemStack drop : inv.addItem(item).values())
            Objects.requireNonNull(loc.getWorld()).dropItem(loc, drop);
    }

    public void give(List<ItemStack> item) {
        for (ItemStack drop : inv.addItem(item.toArray(new ItemStack[0])).values())
            Objects.requireNonNull(loc.getWorld()).dropItem(loc, drop);
    }

}
